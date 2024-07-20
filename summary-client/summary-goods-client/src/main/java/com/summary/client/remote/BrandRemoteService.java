package com.summary.client.remote;

import org.springframework.cloud.openfeign.FeignClient;

/**
 * 品牌相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@FeignClient(value = "summary-goods", path = "/goods/brand")
public interface BrandRemoteService {
}
