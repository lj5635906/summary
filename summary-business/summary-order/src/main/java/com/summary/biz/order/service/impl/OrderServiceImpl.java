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
import com.summary.client.goods.param.CreateOrderCheckParam;
import com.summary.client.order.code.OrderExceptionCode;
import com.summary.client.order.enums.OrderStateEnum;
import com.summary.client.order.enums.OrderTypeEnum;
import com.summary.client.order.param.CreateOrderGoodsParam;
import com.summary.client.order.param.CreateOrderParam;
import com.summary.client.remote.CustomerRemoteService;
import com.summary.client.remote.GoodsRemoteService;
import com.summary.common.core.dto.R;
import com.summary.common.core.utils.Assert;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.common.core.utils.VerificationUtil;
import com.summary.component.generator.id.snowflake.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.summary.common.core.constant.GlobalConstant.DefaultConstant.ZERO_LONG;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-05-31
 */
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

    @Override
    public Long createOrder(CreateOrderParam param) {

        // 检查并获取用户信息
        CustomerDTO customer = VerificationUtil.checkGetResponse(customerService.getCustomerByCustomerId(param.getCustomerId()), CustomerExceptionCode.customer_non_existent);
        // 购买的商品
        List<CreateOrderGoodsParam> buyGoods = param.getGoods();

        // 验证购买的商品信息
        R<List<CreateOrderCheckGoodsSkuDTO>> createOrderGoods = goodsRemoteService.getCreateOrderGoods(ConvertUtils.convertList(buyGoods, CreateOrderCheckParam.class));
        List<CreateOrderCheckGoodsSkuDTO> goodsSkus = VerificationUtil.checkGetResponse(createOrderGoods);
        // 验证传入的商品集合与查询的商品集合是否一致
        Assert.isTrue((CollectionUtils.isEmpty(goodsSkus) || param.getGoods().size() != goodsSkus.size()), OrderExceptionCode.by_goods_check_error);

        // 构建订单信息
        OrderDO order = buildOrderDO(param, customer);

        // 构建商品类目并计算价格
        List<OrderItemDO> orderItems = buildOrderItemDO(order, buyGoods, goodsSkus);

        // 保存订单相关数据
        this.save(order);
        orderItemService.saveBatch(orderItems);
        orderWaterService.saveCreateOrderWater(order.getOrderId());

        return order.getOrderId();
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
