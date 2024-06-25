package com.summary.biz.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.summary.biz.seckill.service.SeckillGoodsService;
import com.summary.biz.seckill.service.SeckillService;
import com.summary.client.seckill.dto.SeckillStateDTO;
import com.summary.client.seckill.enums.SeckillStateEnums;
import com.summary.client.seckill.msg.CreateSeckillOrderMsg;
import com.summary.common.core.constant.topic.SeckillOrderTopic;
import com.summary.common.core.dto.R;
import com.summary.common.core.mq.MqService;
import com.summary.common.core.utils.DateTimeUtils;
import com.summary.common.core.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import static com.summary.client.seckill.code.SeckillExceptionCode.seckill_customer_only;
import static com.summary.client.seckill.code.SeckillExceptionCode.seckill_end;
import static com.summary.common.core.constant.GlobalConstant.RedisCacheConstant.*;

/**
 * 秒杀相关业务
 *
 * @author jie.luo
 * @since 2024/6/4
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private SeckillGoodsService seckillGoodsService;
    @Autowired
    private MqService mqService;

    @Override
    public R<SeckillStateDTO> seckillAction(Long customerId, Long seckillId, Integer num) {

        // 解决重复参与秒杀
        Long increment = redisTemplate.boundHashOps(SECKILL_USER + seckillId).increment(customerId, 1);
        if (increment != null && increment > 1) {
            return R.custom(seckill_customer_only.getCode(), seckill_customer_only.getMessage());
        }

        // 获取库存数量
        Long stockNum = redisTemplate.boundListOps(SECKILL_GOODS_STOCK + seckillId).size();
        // 无库存 该秒杀已经结束
        if (null == stockNum || stockNum <= 0) {
            return R.custom(seckill_end.getCode(), seckill_end.getMessage());
        }

        // 初始化抢单状态为 正在排队
        SeckillStateDTO seckillState = SeckillStateDTO.builder()
                .seckillId(seckillId)
                .customerId(customerId)
                .state(SeckillStateEnums.queuing.getCode())
                .createTime(DateTimeUtils.getNow())
                .build();

        String string = JSONObject.toJSONString(seckillState);

        // 用户描述状态-->用于用户查询当前秒杀状态
        redisTemplate.boundHashOps(SECKILL_STATE + seckillId).put(customerId, string);

        // 发送到队列创建秒杀订单
        mqService.asyncSend(SeckillOrderTopic.SeckillOrderCreate.DESTINATION, CreateSeckillOrderMsg.builder()
                .messageId(UUIDUtils.generateUuid())
                .customerId(customerId)
                .seckillId(seckillId)
                .num(num)
                .build());

        return R.success(seckillState);
    }

    @Override
    public SeckillStateDTO querySeckillState(Long customerId, Long seckillId) {
        Object obj = redisTemplate.boundHashOps(SECKILL_STATE + seckillId).get(customerId);
        if (obj == null) {
            return null;
        }
        return JSONObject.parseObject(obj.toString(), SeckillStateDTO.class);
    }
}
