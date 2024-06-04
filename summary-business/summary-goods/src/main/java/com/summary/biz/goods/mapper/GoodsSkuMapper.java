package com.summary.biz.goods.mapper;

import com.summary.biz.goods.entity.GoodsSkuDO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.summary.client.goods.dto.CreateOrderCheckGoodsSkuDTO;
import com.summary.client.goods.param.CreateOrderCheckParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

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

    /**
     * 商品sku 扣 库存、添加销量
     *
     * @param skuId 商品skuId
     * @param num   扣除数量
     * @return .
     */
    @Update("update goods_sku set sale_num = sale_num+#{num} , stock_num = stock_num-#{num} where sku_id = #{skuId} and stock_num >= #{num}")
    int changeStockAndSale(@Param("skuId") Long skuId, @Param("num") Integer num);

}
