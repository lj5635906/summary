package com.summary.biz.goods.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.goods.entity.GoodsIntroductionDO;
import com.summary.biz.goods.entity.GoodsParamDO;
import com.summary.biz.goods.mapper.GoodsIntroductionMapper;
import com.summary.biz.goods.service.GoodsIntroductionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品介绍 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class GoodsIntroductionServiceImpl extends ServiceImpl<GoodsIntroductionMapper, GoodsIntroductionDO> implements GoodsIntroductionService {

    @Autowired
    private GoodsIntroductionMapper goodsIntroductionMapper;

    @Override
    public void saveGoodsIntroduction(Long goodsId, String introduction) {

        if (StrUtil.isBlank(introduction)) {
            return;
        }

        GoodsIntroductionDO introductionDO = GoodsIntroductionDO.builder()
                .goodsId(goodsId)
                .introduction(introduction)
                .build();

        this.save(introductionDO);
    }

    @Override
    public GoodsIntroductionDO getGoodsIntroductionByGoodsId(Long goodsId) {
        QueryWrapper<GoodsIntroductionDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GoodsIntroductionDO::getGoodsId, goodsId);
        return goodsIntroductionMapper.selectOne(queryWrapper);
    }
}
