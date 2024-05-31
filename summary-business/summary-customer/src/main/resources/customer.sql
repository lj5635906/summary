
drop table if exists customer;
CREATE TABLE customer(
    customer_id	            bigint(20)	 	NOT NULL PRIMARY KEY 	AUTO_INCREMENT	COMMENT '用户id',

    customer_mobile			varchar(20)	    DEFAULT NULL  				COMMENT '电话号码',
    customer_name           varchar(50) 	DEFAULT NULL				COMMENT '姓名',
    sex                     int(1)          NOT NULL                    COMMENT '性别: -1 未知、0-女性、1-男性',
    union_id                varchar(50)     DEFAULT NULL                COMMENT 'unionId',
    open_id                 varchar(50)     DEFAULT NULL                COMMENT '小程序-openid',

    version 				int(11) 		NOT NULL 					COMMENT '版本号',
    create_time 			datetime 		DEFAULT CURRENT_TIMESTAMP 	COMMENT '创建时间',
    update_time 			datetime 		DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    delete_flag 			boolean 		NOT NULL 					COMMENT '删除标志(0/false-未删除,1/true-已删除)',

  UNIQUE KEY unique_index_mobile (customer_mobile) USING BTREE

)ENGINE=InnoDB CHARSET=utf8mb4 COMMENT='用户表';