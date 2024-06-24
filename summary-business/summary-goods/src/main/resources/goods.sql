/* 品牌 */
drop table if exists brand;
CREATE TABLE brand(
    brand_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '品牌id',

    brand_name              varchar(25)	 	NOT NULL                    COMMENT '品牌名称',
    brand_letter            varchar(5)	 	NOT NULL                    COMMENT '品牌的首字母',
    brand_logo              varchar(125)	NOT NULL                    COMMENT '品牌logo',

    sort                    int(11)         NOT NULL    DEFAULT 0       COMMENT '排序',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)'

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='品牌';

/* 分类 */
drop table if exists category;
CREATE TABLE category(
    category_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '分类id',

    parent_id 	            bigint(20)	 	NOT NULL                    COMMENT '上级分类id',

    category_name           varchar(25)	 	NOT NULL                    COMMENT '分类名称',
    show_flag 			    boolean 		NOT NULL DEFAULT 0			COMMENT '显示标志(0/false-不显示,1/true-显示)',
    sort                    int(11)         NOT NULL    DEFAULT 0       COMMENT '排序',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)'

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='分类';

/* 商品-SPU */
drop table if exists goods;
CREATE TABLE goods(
    goods_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '商品id',

    goods_name              varchar(125) 	NOT NULL				    COMMENT '商品名称',

    image                   varchar(255) 	NOT NULL				    COMMENT '商品图片-展示图',

    sale_num                int(11)         NOT NULL    DEFAULT 0       COMMENT '销售数量',
    comment_num             int(11)         NOT NULL    DEFAULT 0       COMMENT '评论数量',

    enable_marketable       int(1)          NOT NULL    DEFAULT 0       COMMENT '是否上架: 0/false-已下架,1/true-已上架',
    enable_spec             int(1)          NOT NULL    DEFAULT 0       COMMENT '是否启用规格: 0/false-未启用,1/true-启用',
    spec_item               json            DEFAULT NULL 	            COMMENT '规格列表',

    category1_id 	        bigint(20)	 	NOT NULL                    COMMENT '一级分类id',
    category2_id 	        bigint(20)	 	NOT NULL                    COMMENT '二级分类id',
    category3_id 	        bigint(20)	 	NOT NULL                    COMMENT '三级分类id',
    brand_id 	            bigint(20)	 	NOT NULL                    COMMENT '品牌id',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)'

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品-SPU';


/* 商品-SKU */
drop table if exists goods_sku;
CREATE TABLE goods_sku(
    sku_id 	                bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '商品skuId',

    goods_id 	            bigint(20)	 	NOT NULL                    COMMENT '商品id',
    goods_name              varchar(125) 	NOT NULL				    COMMENT '商品名称',

    sku_name                varchar(125) 	NOT NULL				    COMMENT 'SKU名称',
    price                   bigint(20)      NOT NULL                    COMMENT '商品sku单价(单位/分)',

    image                   varchar(255) 	NOT NULL				    COMMENT 'SKU图片-展示图',

    stock_num               int(11)         NOT NULL                    COMMENT '库存数量',
    alert_num               int(11)         NOT NULL                    COMMENT '库存预警数量',
    sale_num                int(11)         NOT NULL    DEFAULT 0       COMMENT '销售数量',
    comment_num             int(11)         NOT NULL    DEFAULT 0       COMMENT '评论数量',

    sale_state              int(1)          NOT NULL    DEFAULT 0       COMMENT '销售状态: 0-销售中,-1-已售罄',
    spec                    json            DEFAULT NULL 	            COMMENT '规格',

    sort                    int(11)         NOT NULL    DEFAULT 0       COMMENT '排序',

    category_id 	        bigint(20)	 	NOT NULL                    COMMENT '分类id',
    category_name           varchar(25)	 	NOT NULL                    COMMENT '分类名称',
    brand_id 	            bigint(20)	 	NOT NULL                    COMMENT '品牌id',
    brand_name              varchar(25)	 	NOT NULL                    COMMENT '品牌名称',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_goods_id (goods_id)

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品-SKU';


/* 商品图片 */
drop table if exists goods_image;
CREATE TABLE goods_image(
    image_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '商品图片id',

    goods_id 	            bigint(20)	 	DEFAULT NULL                COMMENT '商品id',
    sku_id 	                bigint(20)	 	DEFAULT NULL                COMMENT '商品-skuId',
    image_url               varchar(255) 	NOT NULL				    COMMENT '商品名称',
    image_type              varchar(10)     NOT NULL                    COMMENT '图片类型: 0-商品SPU、1-商品SKU',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_goods_id (goods_id)

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品图片';


/* 商品介绍 */
drop table if exists goods_introduction;
CREATE TABLE goods_introduction(
    introduction_id 	    bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '商品介绍id',

    goods_id 	            bigint(20)	 	NOT NULL                    COMMENT '商品id',
    introduction            text         	NOT NULL				    COMMENT '商品介绍',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_goods_id (goods_id)

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品介绍';

/* 商品自定义参数 */
drop table if exists goods_param;
CREATE TABLE goods_param(
    param_id 	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '商品参数id',

    goods_id 	            bigint(20)	 	NOT NULL                    COMMENT '商品id',

    param_name              varchar(20)     NOT NULL				    COMMENT '参数名',
    param_value             varchar(125)    NOT NULL				    COMMENT '参数值',

    version 				int(11) 		NOT NULL DEFAULT 0			COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL DEFAULT 0			COMMENT '删除标志(0/false-未删除,1/true-已删除)',
    KEY index_goods_id (goods_id)

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='商品自定义参数';