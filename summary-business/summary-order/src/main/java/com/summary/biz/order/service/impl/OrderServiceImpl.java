package com.summary.biz.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.order.entity.OrderDO;
import com.summary.biz.order.entity.OrderItemDO;
import com.summary.biz.order.mapper.OrderMapper;
import com.summary.biz.order.service.OrderItemService;
import com.summary.biz.order.service.OrderService;
import com.summary.biz.order.service.OrderWaterService;
import com.summary.client.customer.code.CustomerExceptionCode;
import com.summary.client.customer.dto.CustomerDTO;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.param.ChangeStockAndSaleParam;
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.client.order.code.OrderExceptionCode;
import com.summary.client.order.enums.OrderStateEnum;
import com.summary.client.order.enums.OrderTypeEnum;
import com.summary.client.order.msg.OrderTimeoutCancelMsg;
import com.summary.client.order.param.CreateOrderGoodsParam;
import com.summary.client.order.param.CreateOrderParam;
import com.summary.client.remote.CustomerRemoteService;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.constant.topic.OrderTopic;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.mq.MqService;
import com.summary.common.core.mq.rocket.MessageDelayLevel;
import com.summary.common.core.utils.Assert;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.common.core.utils.UUIDUtils;
import com.summary.common.core.utils.VerificationUtil;
import com.summary.component.generator.id.snowflake.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.summary.client.goods.code.GoodsExceptionCode.goods_stock_lack;
import static com.summary.common.core.constant.GlobalConstant.DefaultConstant.ZERO_LONG;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
@Slf4j
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDO> implements OrderService {

    @Autowired
    private CustomerRemoteService customerService;
    @Autowired
    private GoodsRemoteService goodsRemoteService;
    @Autowired
    private OrderItemService orderItemService;
    @Autowired
    private OrderWaterService orderWaterService;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private MqService mqService;

    @Override
    public Long createOrder(CreateOrderParam param) {

        // 检查并获取用户信息
        CustomerDTO customer = customerService.getCustomerByCustomerId(param.getCustomerId());
        Assert.isNull(customer, CustomerExceptionCode.customer_non_existent);
        // 购买的商品
        List<CreateOrderGoodsParam> buyGoods = param.getGoods();

        // 验证购买的商品信息
        List<CreateOrderCheckGoodsSkuDTO> goodsSkus = goodsRemoteService.getCreateOrderGoods(ConvertUtils.convertList(buyGoods, CreateOrderCheckParam.class));
        // 验证传入的商品集合与查询的商品集合是否一致
        Assert.isTrue((CollectionUtils.isEmpty(goodsSkus) || param.getGoods().size() != goodsSkus.size()), OrderExceptionCode.by_goods_check_error);

        // 构建订单信息
        OrderDO order = buildOrderDO(param, customer);

        // 构建商品类目并计算价格
        List<OrderItemDO> orderItems = buildOrderItemDO(order, buyGoods, goodsSkus);

        // 商品下单扣库存与增加销量
        Boolean stockAndSaleResponse = goodsRemoteService.changeStockAndSale(order.getOrderId(), buildChangeStockAndSaleParam(orderItems));
        // false 代表扣库存失败
        boolean success = null != stockAndSaleResponse && stockAndSaleResponse;
        Assert.isFalse(success, new CustomException(goods_stock_lack));

        // 保存订单相关数据
        this.save(order);
        orderItemService.saveBatch(orderItems);
        orderWaterService.saveCreateOrderWater(order.getOrderId());

        // 发送订单超时未支付 取消订单消息
        sendOrderTimeoutCancelMsg(order.getOrderId());

        return order.getOrderId();
    }

    /**
     * 发送订单超时未支付取消订单消息
     *
     * @param orderId orderId
     */
    private void sendOrderTimeoutCancelMsg(Long orderId) {
        OrderTimeoutCancelMsg orderTimeoutCancelMsg = OrderTimeoutCancelMsg.builder()
                .messageId(UUIDUtils.generateUuid())
                .orderId(orderId)
                .build();
        mqService.sendDelayed(OrderTopic.OrderTimeoutCancel.DESTINATION, orderTimeoutCancelMsg, MessageDelayLevel.LEVEL_5);
    }

    @Override
    public void orderTimeoutCancel(Long orderId) {
        OrderDO orderDO = this.getById(orderId);
        if (null == orderDO) {
            log.error("订单不存在，orderId: {}", orderId);
            return;
        }

        // 订单状态
        OrderStateEnum orderState = OrderStateEnum.getByCode(orderDO.getOrderState());

        if (!OrderStateEnum.WAIT_PAY.equals(orderState)) {
            log.error("订单【orderId : {}】状态为: {}，不能进行取消订单", orderId, orderState.getMessage());
            return;
        }

        // 修改订单状态
        OrderDO modify = OrderDO.builder()
                .orderId(orderDO.getOrderId())
                .orderState(OrderStateEnum.TIME_OUT_CANCEL.getCode())
                .orderStateDesc(OrderStateEnum.TIME_OUT_CANCEL.getMessage()).build();
        orderMapper.updateById(modify);

        // 添加订单流水
        orderWaterService.saveOrderTimeoutCancelWater(orderId);

        // 商品恢复库存&销量
        List<OrderItemDO> orderItems = orderItemService.getOrderItems(orderId);
        if (CollectionUtils.isEmpty(orderItems)) {
            return;
        }

        for (OrderItemDO orderItem : orderItems) {
            ChangeStockAndSaleParam param = new ChangeStockAndSaleParam();
            param.setGoodsId(orderItem.getGoodsId());
            param.setSkuId(orderItem.getSkuId());
            param.setNum(orderItem.getBuyNumber());
            // 订单取消 恢复 库存与销量
            goodsRemoteService.recoveryStockAndSale(orderId, param);
        }
    }

    /**
     * 构建 商品下单扣库存与增加销量
     *
     * @param orderItems 订单明细
     * @return .
     */
    private List<ChangeStockAndSaleParam> buildChangeStockAndSaleParam(List<OrderItemDO> orderItems) {
        List<ChangeStockAndSaleParam> params = new ArrayList<>(orderItems.size());
        ChangeStockAndSaleParam param = null;
        for (OrderItemDO orderItem : orderItems) {
            param = new ChangeStockAndSaleParam();
            param.setNum(orderItem.getBuyNumber());
            param.setSkuId(orderItem.getSkuId());
            param.setGoodsId(orderItem.getGoodsId());
            params.add(param);
        }
        return params;
    }

    /**
     * 构建商品类目并计算价格
     *
     * @param order     订单
     * @param buyGoods  购买商品集合
     * @param goodsSkus 查询的购买商品集合
     * @return .
     */
    private List<OrderItemDO> buildOrderItemDO(OrderDO order, List<CreateOrderGoodsParam> buyGoods, List<CreateOrderCheckGoodsSkuDTO> goodsSkus) {

        // <skuId,CreateOrderCheckGoodsSkuDTO>
        Map<Long, CreateOrderCheckGoodsSkuDTO> goodsSkusMap = goodsSkus.stream().collect(Collectors.toMap(CreateOrderCheckGoodsSkuDTO::getSkuId, obj -> obj));

        List<OrderItemDO> orderItems = new ArrayList<>(goodsSkus.size());
        OrderItemDO item = null;

        // 订单总金额
        long totalMoney = ZERO_LONG;
        // 订单优惠总金额
        long totalDiscountMoney = ZERO_LONG;
        // 订单实际支付金额
        long totalPayMoney = ZERO_LONG;

        for (CreateOrderGoodsParam buyGood : buyGoods) {

            // 创建订单check商品
            CreateOrderCheckGoodsSkuDTO goodsSku = goodsSkusMap.get(buyGood.getSkuId());

            // 该商品总金额
            long money = buyGood.getBuyNumber() * goodsSku.getPrice();
            // 该商品优惠总金额
            long discountMoney = ZERO_LONG;
            // 该商品实际支付金额
            long payMoney = money - discountMoney;

            item = new OrderItemDO();
            item.setItemId(IdWorker.nextId());
            item.setOrderId(order.getOrderId());
            item.setGoodsId(goodsSku.getGoodsId());
            item.setGoodsName(goodsSku.getGoodsName());
            item.setSkuId(goodsSku.getSkuId());
            item.setSkuName(goodsSku.getSkuName());
            item.setSkuPrice(goodsSku.getPrice());
            item.setMoney(money);
            item.setDiscountMoney(ZERO_LONG);
            item.setPayMoney(payMoney);
            item.setBuyNumber(buyGood.getBuyNumber());

            // 总金额计算
            totalMoney = totalMoney + money;
            totalDiscountMoney = totalDiscountMoney + discountMoney;
            totalPayMoney = totalPayMoney + payMoney;

            orderItems.add(item);

        }

        order.setMoney(totalMoney);
        order.setDiscountMoney(totalDiscountMoney);
        order.setPayMoney(totalPayMoney);
        return orderItems;
    }

    /**
     * 构建订单信息
     *
     * @param param    创建订单参数
     * @param customer 客户信息
     * @return 订单
     */
    private OrderDO buildOrderDO(CreateOrderParam param, CustomerDTO customer) {

        OrderTypeEnum orderType = OrderTypeEnum.getByCode(param.getOrderType());

        OrderDO order = new OrderDO();
        order.setOrderId(IdWorker.nextId());
        order.setCustomerId(customer.getCustomerId());
        order.setCustomerName(customer.getCustomerName());
        order.setCustomerMobile(customer.getCustomerMobile());
        order.setOrderType(orderType.getCode());
        order.setOrderTypeDesc(orderType.getMessage());
        order.setOrderState(OrderStateEnum.WAIT_PAY.getCode());
        order.setOrderStateDesc(OrderStateEnum.WAIT_PAY.getMessage());
        order.setCustomerMessage(param.getCustomerMessage());

        return order;
    }

}
