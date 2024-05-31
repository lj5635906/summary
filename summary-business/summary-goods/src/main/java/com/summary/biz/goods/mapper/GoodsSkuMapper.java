package com.summary.biz.goods.mapper;

import com.summary.biz.goods.entity.GoodsSkuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.param.CreateOrderCheckParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * <p>
 * 商品-SKU Mapper 接口
 * </p>
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Mapper
public interface GoodsSkuMapper extends BaseMapper<GoodsSkuDO> {

    /**
     * 查询 创建订单时查询的sku数据
     *
     * @param params 下单check商品参数
     * @return 创建订单时查询的sku数据
     */
    List<CreateOrderCheckGoodsSkuDTO> selectCreateOrderCheckGoodsSku(List<CreateOrderCheckParam> params);
}
