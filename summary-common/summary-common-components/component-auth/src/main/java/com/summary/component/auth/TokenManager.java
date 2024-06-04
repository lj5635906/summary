package com.summary.component.auth;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.summary.common.core.enums.AppEnum;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.exception.code.BaseExceptionCode;
import com.summary.common.core.utils.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

import static com.summary.common.core.constant.GlobalConstant.TokenConstant.*;

/**
 * @author jie.luo
 * @since 2024/6/5
 */
public class TokenManager {

    /**
     * 获取 用户id
     *
     * @return 用户id
     */
    public static Long getUserId() {
        String userId = getItem(X_USER_ID);
        if (StringUtils.isNotBlank(userId) && NumberUtils.isNumber(userId)) {
            return Long.parseLong(userId);
        }
        throw new CustomException(BaseExceptionCode.FORBIDDEN.getCode(), BaseExceptionCode.FORBIDDEN.getMessage());
    }

    /**
     * 获取 请求ip
     *
     * @return 请求ip
     */
    public static String getRequestIp() {
        return getItem(X_REQUEST_IP);
    }

    /**
     * 获取 Token 类型
     *
     * @return {@link AppEnum}
     */
    public static AppEnum getTokenType() {
        String item = getItem(TOKEN_TYPE);
        if (StrUtil.isBlank(item)) {
            return null;
        }
        return AppEnum.getByCode(Integer.parseInt(item));
    }

    private static String getItem(String itemName) {
        UserContextHolder instance = UserContextHolder.getInstance();
        if (null == instance) {
            return null;
        }
        Map<String, String> context = instance.getContext();
        if (CollectionUtil.isEmpty(context)) {
            return null;
        }
        return context.get(itemName);
    }

}
