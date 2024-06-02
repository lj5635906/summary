
drop table if exists o_order;
CREATE TABLE o_order(
    order_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '订单id',

    customer_id 			bigint(20)	 	        NOT NULL                    COMMENT '客户id',
    customer_name			varchar(100)	        DEFAULT NULL 			    COMMENT '姓名',
    customer_mobile			varchar(20)	            DEFAULT NULL  				COMMENT '电话号码',

    order_type              int(3)                  NOT   NULL                  COMMENT '订单类型(1-普通订单、2-积分订单、3-秒杀订单、4-赠品)',
    order_type_desc         varchar(20)             NOT  NULL                   COMMENT '订单类型描述',

    order_state             int(3)                  NOT  NULL                   COMMENT '订单状态(-3-主动取消订单,-2-超时未支付,-1-已关闭,0-待付款,1-待发货,2-待收货,3-已完成,4-已评价,5-退款中,6-已退款)',
    order_state_desc        varchar(20)             NOT  NULL                   COMMENT '订单状态描述',

    money                   bigint(20)              NOT  NULL                   COMMENT '订单总金额(分)',
    discount_money          bigint(20)              NOT  NULL                   COMMENT '订单优惠总金额(分)',
    pay_money               bigint(20)              NOT  NULL                   COMMENT '订单实际支付金额(分)',
    pay_time                datetime                DEFAULT  NULL               COMMENT '付款时间',

    customer_message        varchar(512)            DEFAULT  NULL              COMMENT '订单备注',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
  KEY index_customer_id (customer_id)

) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单表';

/** 订单-类目表 */
DROP TABLE IF EXISTS order_item;
CREATE  TABLE  order_item(
    item_id                             bigint(20)              NOT NULL    PRIMARY KEY   AUTO_INCREMENT    COMMENT '主键id',

    order_id                            bigint(20)              NOT NULL                    COMMENT '订单id',

    goods_id                            bigint(20)              NOT NULL                    COMMENT '商品id',
    goods_name 		                    varchar(125) 		    NOT NULL				    COMMENT '商品名称',
    sku_id 	                            bigint(20)	 	        NOT NULL                    COMMENT '商品skuId',
    sku_name                            varchar(50) 	        NOT NULL				    COMMENT 'SKU名称',

    sku_price                           bigint(20)              NOT NULL                    COMMENT '商品sku单价(单位/分)',
    money                               bigint(20)              NOT  NULL                   COMMENT '该商品总金额(分)',
    discount_money                      bigint(20)              NOT  NULL                   COMMENT '该商品优惠总金额(分)',
    pay_money                           bigint(20)              NOT  NULL                   COMMENT '该商品实际支付金额(分)',
    buy_number                          int(11)                 NOT NULL                    COMMENT '商品的数量/购买数量',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_order_id (order_id)
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '订单-类目表';

/* 订单流水 */
drop table if exists order_water;
CREATE TABLE order_water(
    water_id 		            bigint(20)	 	NOT NULL PRIMARY KEY AUTO_INCREMENT COMMENT '订单流水id',

    order_id 				    bigint(20)	 	NOT NULL                    COMMENT '订单id',

    from_order_state            int(2) 			DEFAULT NULL				COMMENT '操作前订单状态',
    from_order_state_desc       varchar(20) 	DEFAULT NULL				COMMENT '操作前订单状态描述',

    to_order_state              int(2) 			NOT NULL					COMMENT '操作后订单状态',
    to_order_state_desc         varchar(20) 	NOT NULL					COMMENT '操作后订单状态描述',

    remark                      varchar(256) 	DEFAULT NULL				COMMENT '备注',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',

    KEY index_order_id (order_id)
)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='订单流水';