package com.summary.biz.seckill.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.summary.biz.seckill.service.SeckillGoodsService;
import com.summary.biz.seckill.service.SeckillOrderService;
import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.dto.SeckillOrderDTO;
import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.enums.SeckillStateEnums;
import com.summary.client.seckill.msg.CreateSeckillOrderMsg;
import com.summary.client.seckill.msg.SeckillOrderTimeoutCancelMsg;
import com.summary.common.core.constant.topic.SeckillOrderTopic;
import com.summary.common.core.mq.MqService;
import com.summary.common.core.mq.rocket.MessageDelayLevel;
import com.summary.common.core.utils.UUIDUtils;
import com.summary.component.generator.id.snowflake.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.summary.common.core.constant.GlobalConstant.RedisCacheConstant.*;

/**
 * 秒杀订单相关
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Slf4j
@Service
public class SeckillOrderServiceImpl implements SeckillOrderService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private MqService mqService;

    @Override
    public void createSeckillOrder(CreateSeckillOrderMsg msg) {

        long seckillId = msg.getSeckillId();
        long customerId = msg.getCustomerId();

        // 获取num(秒杀数量)个库存
        List list = redisTemplate.boundListOps(SECKILL_GOODS_STOCK + seckillId).rightPop(msg.getNum());
        if (CollUtil.isEmpty(list)) {
            // 秒杀商品已经售罄
            return;
        }
        if (msg.getNum() != list.size()) {
            // 秒杀数量>库存数量,不能进行秒杀
            // 恢复库存数量
            redisTemplate.boundListOps(SECKILL_GOODS_STOCK + seckillId).leftPushAll(list);
            return;
        }
        // 获取到库存，则可进行秒杀

        // 获取用户的秒杀状态
        Object obj = redisTemplate.boundHashOps(SECKILL_STATE + seckillId).get(customerId);
        ;
        if (obj == null) {
            return;
        }
        SeckillStateDTO seckillState = JSONObject.parseObject(obj.toString(), SeckillStateDTO.class);

        // 获取当前秒杀商品
        SeckillGoodsDTO seckillGoods = seckillGoodsService.getSeckillGoods(seckillId);

        if (null == seckillGoods || seckillGoods.getStockNum() <= 0) {
            log.debug("该秒杀商品卖完了");
            seckillState.setState(SeckillStateEnums.fail.getCode());
            // 用户描述状态-->用于用户查询当前秒杀状态
            redisTemplate.boundHashOps(SECKILL_STATE + seckillId).put(customerId, JSONObject.toJSONString(seckillState));
            return;
        }

        // 创建秒杀订单
        SeckillOrderDTO order = new SeckillOrderDTO();
        order.setOrderId(IdWorker.nextId());
        order.setSeckillId(seckillId);
        order.setGoodsId(seckillGoods.getGoodsId());
        order.setSkuId(seckillGoods.getSkuId());
        order.setSkuPrice(seckillGoods.getSkuPrice());
        order.setSeckillPrice(seckillGoods.getSeckillPrice());
        order.setMoney(msg.getNum() * seckillGoods.getSeckillPrice());
        order.setNum(msg.getNum());
        order.setOrderState(0);
        // 用户秒杀订单写入redis
        redisTemplate.boundHashOps(SECKILL_ORDER + seckillId).put(customerId, JSONObject.toJSONString(order));

        // 修改用户秒杀状态
        seckillState.setState(SeckillStateEnums.wait_pay.getCode());
        seckillState.setMoney(order.getMoney());
        seckillState.setOrderId(order.getOrderId());
        redisTemplate.boundHashOps(SECKILL_STATE + seckillId).put(customerId, JSONObject.toJSONString(seckillState));

        // 秒杀订单超时未支付消息
        mqService.sendDelayed(SeckillOrderTopic.SeckillOrderTimeoutCancel.DESTINATION, SeckillOrderTimeoutCancelMsg.builder()
                .messageId(UUIDUtils.generateUuid())
                .customerId(customerId)
                .seckillId(seckillId)
                .build(), MessageDelayLevel.LEVEL_5);
    }

    @Override
    public void seckillOrderTimeoutCancel(Long customerId, Long seckillId) {
        Object obj = redisTemplate.boundHashOps(SECKILL_ORDER + seckillId).get(customerId);
        if (obj == null) {
            return;
        }

        SeckillOrderDTO order = JSONObject.parseObject(obj.toString(), SeckillOrderDTO.class);
        order.setOrderState(-2);
        redisTemplate.boundHashOps(SECKILL_ORDER + seckillId).put(customerId, JSONObject.toJSONString(order));

        // 恢复库存
        redisTemplate.boundListOps(SECKILL_GOODS_STOCK + seckillId).leftPushAll(seckillId);
    }

}
