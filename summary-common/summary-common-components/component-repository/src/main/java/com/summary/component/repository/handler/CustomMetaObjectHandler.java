package com.summary.component.repository.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.summary.common.core.utils.DateTimeUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * 元数据操作处理器
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Component
public class CustomMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "version", 0);
        this.fillStrategy(metaObject, "createTime", DateTimeUtils.getNow());
        this.fillStrategy(metaObject, "updateTime", DateTimeUtils.getNow());
        this.fillStrategy(metaObject, "deleteFlag", false);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        this.fillStrategy(metaObject, "updateTime", DateTimeUtils.getNow());
    }
}
