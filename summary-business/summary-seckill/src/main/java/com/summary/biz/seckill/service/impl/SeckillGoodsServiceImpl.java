package com.summary.biz.seckill.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.biz.seckill.entity.SeckillGoodsDO;
import com.summary.biz.seckill.mapper.SeckillGoodsMapper;
import com.summary.biz.seckill.service.SeckillGoodsService;
import com.summary.client.seckill.dto.SeckillGoodsDTO;
import com.summary.client.seckill.param.CreateSeckillGoodsParam;
import com.summary.common.core.utils.ConvertUtils;
import com.summary.component.generator.id.snowflake.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

import static com.summary.common.core.constant.GlobalConstant.RedisCacheConstant.SECKILL_GOODS;
import static com.summary.common.core.constant.GlobalConstant.RedisCacheConstant.SECKILL_GOODS_STOCK;

/**
 * <p>
 * 秒杀商品 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class SeckillGoodsServiceImpl extends ServiceImpl<SeckillGoodsMapper, SeckillGoodsDO> implements SeckillGoodsService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public Long createSeckillGoods(CreateSeckillGoodsParam param) {

        Long seckillId = IdWorker.nextId();
        SeckillGoodsDO seckillGoods = ConvertUtils.convert(param, SeckillGoodsDO.class);
        seckillGoods.setSeckillId(seckillId);
        this.save(seckillGoods);

        // 秒杀商品
        redisTemplate.boundHashOps(SECKILL_GOODS)
                .put(seckillId, JSONObject.toJSONString(seckillGoods));

        // 库存不精确解决方案
        Long[] stockArr = new Long[seckillGoods.getStockNum()];
        for (int i = 0; i < seckillGoods.getStockNum(); i++) {
            stockArr[i] = seckillId;
        }

        // 秒杀商品库存列表
        // 每一个元素，表示一个库存
        redisTemplate.boundListOps(SECKILL_GOODS_STOCK + seckillId)
                .leftPushAll(stockArr);

        return seckillGoods.getSeckillId();
    }

    @Override
    public SeckillGoodsDTO getSeckillGoods(Long seckillId) {
        Object o = redisTemplate.boundHashOps(SECKILL_GOODS).get(seckillId);
        if (o == null) {
            return null;
        }
        return JSONObject.parseObject(o.toString(), SeckillGoodsDTO.class);
    }

    @Override
    public List<SeckillGoodsDTO> pageSeckillGoods() {
        List values = redisTemplate.boundHashOps(SECKILL_GOODS).values();
        if (CollectionUtils.isEmpty(values)) {
            return null;
        }
        List<SeckillGoodsDTO> goods = new ArrayList<>();
        for (Object value : values) {
            goods.add(JSONObject.parseObject(value.toString(), SeckillGoodsDTO.class));
        }
        return goods;
    }
}
