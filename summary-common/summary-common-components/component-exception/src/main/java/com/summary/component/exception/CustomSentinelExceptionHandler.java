package com.summary.component.exception;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityException;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeException;
import com.alibaba.csp.sentinel.slots.block.flow.FlowException;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowException;
import com.alibaba.csp.sentinel.slots.system.SystemBlockException;
import com.summary.common.core.exception.CustomException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;

/**
 * Sentinel 相关异常处理
 *
 * @author jie.luo
 * @since 2024/6/14
 */
@Slf4j
@Component
public class CustomSentinelExceptionHandler implements BlockExceptionHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        if (e instanceof FlowException) {
            // 限流异常
            log.error("接口已被限流 ", e);
            throw new CustomException(FLOW_EXCEPTION);
        } else if (e instanceof DegradeException) {
            // 熔断异常
            log.error("接口已被熔断,请稍后再试", e);
            throw new CustomException(DEGRADE_EXCEPTION);
        } else if (e instanceof ParamFlowException) {
            // 热点参数限流
            log.error("热点参数限流", e);
            throw new CustomException(PARAM_FLOW_EXCEPTION);
        } else if (e instanceof SystemBlockException) {
            // 系统规则异常
            log.error("系统规则(负载/....不满足要求)", e);
            throw new CustomException(SYSTEM_BLOCK_EXCEPTION);
        } else if (e instanceof AuthorityException) {
            // 授权规则异常
            log.error("授权规则不通过", e);
            throw new CustomException(AUTHORITY_EXCEPTION);
        } else {
            throw new CustomException(SENTINEL);
        }
    }
}
