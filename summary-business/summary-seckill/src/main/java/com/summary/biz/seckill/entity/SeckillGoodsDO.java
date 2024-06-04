package com.summary.biz.seckill.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.summary.component.repository.base.BaseDO;
import java.io.Serializable;
import lombok.*;
import java.time.LocalDateTime;

/**
 * 秒杀商品
 *
 * @author myabtis-plus
 * @since 2024-06-01
 */
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@TableName("seckill_goods")
public class SeckillGoodsDO extends BaseDO<SeckillGoodsDO> {

    private static final long serialVersionUID = 1L;
    /*** 秒杀id */
    @TableId(value = "seckill_id", type = IdType.AUTO)
    private Long seckillId;
    /*** 商品id */
    private Long goodsId;
    /*** 商品名称 */
    private String goodsName;
    /*** 商品skuId */
    private Long skuId;
    /*** SKU名称 */
    private String skuName;
    /*** 商品sku单价(单位/分) */
    private Long skuPrice;
    /*** 库存数量 */
    private Integer stockNum;
    /*** 标题 */
    private String title;
    /*** 秒杀价格(单位/分) */
    private Long seckillPrice;

    @Builder
    public SeckillGoodsDO(Integer version, LocalDateTime createTime, LocalDateTime updateTime, Boolean deleteFlag, Long seckillId, Long goodsId, String goodsName, Long skuId, String skuName, Long skuPrice, Integer stockNum, String title, Long seckillPrice) {
        super(version, createTime, updateTime, deleteFlag);
        this.seckillId = seckillId;
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.skuId = skuId;
        this.skuName = skuName;
        this.skuPrice = skuPrice;
        this.stockNum = stockNum;
        this.title = title;
        this.seckillPrice = seckillPrice;
    }


    @Override
    public Serializable pkVal() {
        return this.seckillId;
    }

}
