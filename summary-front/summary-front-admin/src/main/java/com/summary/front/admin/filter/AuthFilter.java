package com.summary.front.admin.filter;

import com.alibaba.fastjson2.JSONObject;
import com.summary.common.core.dto.R;
import com.summary.common.core.jwt.JwtUtils;
import com.summary.common.core.utils.UserContextHolder;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.summary.common.core.constant.GlobalConstant.SessionConstant.*;
import static com.summary.common.core.constant.GlobalConstant.StrConstant.DEFAULT_CHARSET;
import static com.summary.common.core.constant.GlobalConstant.StrConstant.HEADER_ACCESS_TOKEN_NAME;

/**
 * 验证当前地址 该用户是否有权限访问
 *
 * @author jie.luo
 * @since 2024/5/30
 */
@Slf4j
@Component
public class AuthFilter extends OncePerRequestFilter {
    /**
     * 不需要拦截的地址
     */
    private static final Set<String> ALLOWED_PATHS = Set.of(
            "/admin/login/username",
            "/admin/smart-doc/AllInOne.css",
            "/admin/smart-doc/debug.js",
            "/admin/smart-doc/font.css",
            "/admin/smart-doc/highlight.min.js",
            "/admin/smart-doc/index.html",
            "/admin/smart-doc/jquery.min.js",
            "/admin/smart-doc/search.js",
            "/admin/smart-doc/xt256.min.css");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // 当前请求地址
        String currentUri = request.getServletPath();

        boolean allowedPath = ALLOWED_PATHS.contains(currentUri);
        if (allowedPath) {
            filterChain.doFilter(request, response);
            return;
        }

        // 从请求头中取出token
        String accessToken = request.getHeader(HEADER_ACCESS_TOKEN_NAME);

        if (StringUtils.isBlank(accessToken)) {
            writeForbidden(response);
            return;
        }

        // 校验access_token
        boolean verify = JwtUtils.verify(accessToken);
        if (!verify) {
            writeForbidden(response);
            return;
        }

        Object adminIdObj = JwtUtils.getPayloadClaim(accessToken, X_ADMIN_ID);
        if (null == adminIdObj) {
            // 请求没有token
            writeForbidden(response);
            return;
        }

        long adminId = 0;
        try {
            adminId = (long) adminIdObj;
        } catch (Exception e) {
            logger.error("JwtToken 中 获取 [X_ADMIN_ID] 属性转换类型出现异常");
            logger.error(e.getMessage(), e);
        }

        String requestIp = request.getHeader(X_REQUEST_IP);

        log.debug("url:{}, get adminId:{}, requestIp:{} from header", currentUri, adminId, requestIp);

        Map<String, String> params = new HashMap<>(4);
        params.put(X_ADMIN_ID, String.valueOf(adminId));
        params.put(X_REQUEST_IP, requestIp);
        UserContextHolder.getInstance().setContext(params);

        filterChain.doFilter(request, response);
    }


    protected void writeForbidden(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();

        String forbidden = JSONObject.toJSONString(R.forbidden());

        out.write(forbidden);
        out.flush();
    }

}
