package com.summary.common.core.utils;

import java.util.UUID;

/**
 * “UUID”的工具类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public final class UUIDUtils {

    /**
     * 生成不包含横杠“-”的UUID字符串，长度32位。
     *
     * @return 32位的UUID字符串
     */
    public static String generateUuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }


}
