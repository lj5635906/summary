package com.summary.front.web.rest.search;

import com.summary.client.goods.dto.GoodsSkuDTO;
import com.summary.client.remote.GoodsSkuSearchRemoteService;
import com.summary.client.search.param.GoodsSkuSearchParam;
import com.summary.common.core.dto.R;
import com.summary.common.core.page.PageResult;
import jakarta.validation.Valid;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 搜索相关接口
 *
 * @author jie.luo
 * @since 2024/6/24
 */
@RestController
@RequestMapping("/web/search")
public class SearchController {

    @DubboReference
    private GoodsSkuSearchRemoteService goodsSkuSearchRemoteService;

    /**
     * 商品sku搜索
     *
     * @param param 添加商品参数
     * @return 商品id
     */
    @PostMapping("/searchGoodsSkus")
    public R<PageResult<GoodsSkuDTO>> searchGoodsSkus(@RequestParam @Valid GoodsSkuSearchParam param) throws IOException {
        return R.success(goodsSkuSearchRemoteService.searchGoodsSkus(param));
    }
}
