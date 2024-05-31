package com.summary.biz.goods.service;

import com.summary.biz.goods.entity.GoodsParamDO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.summary.client.goods.param.CreateGoodsCustomParam;

import java.util.List;

/**
 * <p>
 * 商品参数 服务类
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
public interface GoodsParamService extends IService<GoodsParamDO> {

    /**
     * 保存商品自定义参数
     *
     * @param goodsId    商品id
     * @param parameters 商品自定义参数
     */
    void saveGoodsParam(Long goodsId, List<CreateGoodsCustomParam> parameters);

    /**
     * 获取商品的自定义参数
     * @param goodsId 商品id
     * @return 商品的自定义参数
     */
    List<GoodsParamDO> getGoodsParamByGoodsId(Long goodsId);

}
