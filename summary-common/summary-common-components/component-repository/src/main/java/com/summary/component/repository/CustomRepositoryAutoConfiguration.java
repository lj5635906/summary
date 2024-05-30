package com.summary.component.repository;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 数据访问层 自动配置
 *
 * @author jie.luo
 * @since 2024/5/29
 */
@Configuration
@ConditionalOnClass
@ComponentScan(basePackages = "com.summary.component.repository")
public class CustomRepositoryAutoConfiguration {

    /**
     * 分页插件
     *
     * @return {@link MybatisPlusInterceptor}
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor plusInterceptor = new MybatisPlusInterceptor();
        // 分页插件
        plusInterceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
        // 针对 update 和 delete 语句 作用: 阻止恶意的全表更新删除 插件
        plusInterceptor.addInnerInterceptor(new BlockAttackInnerInterceptor());
        // 乐观锁插件
        // 在实体类的字段上加上@Version注解
        plusInterceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        return plusInterceptor;
    }
}
