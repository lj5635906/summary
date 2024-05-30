package com.summary.front.admin.common;

import cn.hutool.core.collection.CollectionUtil;
import com.summary.common.core.exception.CustomException;
import com.summary.common.core.exception.code.BaseExceptionCode;
import com.summary.common.core.utils.UserContextHolder;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Map;

import static com.summary.common.core.constant.GlobalConstant.SessionConstant.*;

/**
 * Admin session 管理
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public class SessionManager {

    /**
     * 获取管理员id
     *
     * @return 管理员id
     */
    public static Long getAdminId() {
        String adminId = getItem(X_ADMIN_ID);
        if (StringUtils.isNotBlank(adminId) && NumberUtils.isNumber(adminId)) {
            return Long.parseLong(adminId);
        }
        throw new CustomException(BaseExceptionCode.FORBIDDEN.getCode(), BaseExceptionCode.FORBIDDEN.getMessage());
    }

    /**
     * 获取 管理员 请求ip
     *
     * @return 请求ip
     */
    public static String getAdminIp() {
        return getItem(X_REQUEST_IP);
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
