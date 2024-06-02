package com.summary.client.remote;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 秒杀相关接口
 *
 * @author jie.luo
 * @since 2024/06/01
 */
@FeignClient(name = "summary-seckill", path = "/seckill")
public interface SeckillRemoteService {

}
