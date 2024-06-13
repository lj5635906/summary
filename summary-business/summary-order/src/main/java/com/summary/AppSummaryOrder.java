package com.summary;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * 启动主程序
 *
 * @author jie.luo
 * @since 2024/05/30
 */
@Slf4j
@SpringBootApplication
@EnableDubbo
@MapperScan("com.summary.biz.order.mapper")
public class AppSummaryOrder implements ApplicationRunner, DisposableBean {
    private static ConfigurableApplicationContext ctx;

    public static void main(String[] args) {
        // No Root logger was configured, creating default ERROR-level Root logger with Console appen
        System.setProperty("nacos.logging.default.config.enabled", "false");
        ctx = SpringApplication.run(AppSummaryOrder.class, args);
        for (String str : ctx.getEnvironment().getActiveProfiles()) {
            log.info(str);
        }
        log.info("App started!");
    }

    @Override
    public void destroy() throws Exception {
        if (null != ctx && ctx.isRunning()) {
            log.info("App closed!");
            ctx.close();
        }
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("App started run method");
    }

}
