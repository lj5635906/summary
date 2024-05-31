package com.summary.biz.goods.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.summary.biz.goods.entity.GoodsParamDO;
import com.summary.biz.goods.entity.GoodsSkuDO;
import com.summary.biz.goods.mapper.GoodsParamMapper;
import com.summary.biz.goods.service.GoodsParamService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.summary.client.goods.param.CreateGoodsCustomParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 商品参数 服务实现类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Service
public class GoodsParamServiceImpl extends ServiceImpl<GoodsParamMapper, GoodsParamDO> implements GoodsParamService {

    @Autowired
    private GoodsParamMapper goodsParamMapper;

    @Override
    public void saveGoodsParam(Long goodsId, List<CreateGoodsCustomParam> parameters) {
        if (CollectionUtils.isEmpty(parameters)) {
            return;
        }

        List<GoodsParamDO> params = new ArrayList<>();
        GoodsParamDO param = null;
        for (CreateGoodsCustomParam parameter : parameters) {
            param = GoodsParamDO.builder()
                    .goodsId(goodsId)
                    .paramName(parameter.getParamName())
                    .paramValue(parameter.getParamValue())
                    .build();
            params.add(param);
        }

        saveBatch(params);
    }

    @Override
    public List<GoodsParamDO> getGoodsParamByGoodsId(Long goodsId) {
        QueryWrapper<GoodsParamDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda()
                .eq(GoodsParamDO::getGoodsId, goodsId);
        return goodsParamMapper.selectList(queryWrapper);
    }
}
