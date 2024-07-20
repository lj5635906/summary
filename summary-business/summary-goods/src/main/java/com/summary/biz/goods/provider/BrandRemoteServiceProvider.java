package com.summary.biz.goods.provider;

import com.summary.client.remote.BrandRemoteService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 品牌相关接口
 *
 * @author jie.luo
 * @since 2024/6/23
 */
@RestController
@RequestMapping("/goods/brand")
public class BrandRemoteServiceProvider implements BrandRemoteService {
}
