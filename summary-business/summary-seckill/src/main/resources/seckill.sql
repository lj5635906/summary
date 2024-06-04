
/** 秒杀商品 */
DROP TABLE IF EXISTS seckill_goods;
CREATE  TABLE  seckill_goods(
    seckill_id                             bigint(20)              NOT NULL    PRIMARY KEY   AUTO_INCREMENT    COMMENT '秒杀id',

    goods_id                            bigint(20)              NOT NULL                    COMMENT '商品id',
    goods_name 		                    varchar(125) 		    NOT NULL				    COMMENT '商品名称',
    sku_id 	                            bigint(20)	 	        NOT NULL                    COMMENT '商品skuId',
    sku_name                            varchar(50) 	        NOT NULL				    COMMENT 'SKU名称',
    sku_price                           bigint(20)              NOT NULL                    COMMENT '商品sku单价(单位/分)',

    stock_num                           int(11)                 NOT NULL                    COMMENT '库存数量',

    title   		                    varchar(125) 		    NOT NULL				    COMMENT '标题',
    seckill_price                       bigint(20)              NOT NULL                    COMMENT '秒杀价格(单位/分)',

    version                               int(11)                  NOT NULL DEFAULT 0                      COMMENT '版本号',
    create_time                           datetime                 NOT NULL DEFAULT   CURRENT_TIMESTAMP    COMMENT '创建时间',
    update_time                           datetime                 NOT NULL ON  UPDATE CURRENT_TIMESTAMP    COMMENT '更新时间',
    delete_flag                           tinyint(1)               NOT NULL DEFAULT 0                      COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_goods_id_sku_id (goods_id,sku_id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '秒杀商品';