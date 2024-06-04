package com.summary.component.auth.filters;

import cn.hutool.json.JSONObject;
import com.summary.common.core.dto.R;
import com.summary.common.core.jwt.JwtUtils;
import com.summary.common.core.utils.UserContextHolder;
import com.summary.component.auth.config.AuthenticationAnonProperties;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static com.summary.common.core.constant.GlobalConstant.StrConstant.DEFAULT_CHARSET;
import static com.summary.common.core.constant.GlobalConstant.StrConstant.HEADER_ACCESS_TOKEN_NAME;
import static com.summary.common.core.constant.GlobalConstant.TokenConstant.*;

/**
 * 用户鉴权过滤器
 *
 * @author jie.luo
 * @since 2024/6/5
 */
@Component
public class AccessTokenCheckFilter extends OncePerRequestFilter {

    private AuthenticationAnonProperties properties;

    public AccessTokenCheckFilter(AuthenticationAnonProperties properties) {
        this.properties = properties;
    }

    protected AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        if (null == properties || CollectionUtils.isEmpty(properties.getPath())) {
            filterChain.doFilter(request, response);
            return;
        }

        // 当前请求
        String path = request.getServletPath();

        // 是否匹配
        boolean match = matcher(properties.getPath(), path);
        if (match) {
            // 当前请求不做任何拦截
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

        Object tokenTypeObj = JwtUtils.getPayloadClaim(accessToken, TOKEN_TYPE);
        if (null == tokenTypeObj) {
            // 请求没有token
            writeForbidden(response);
            return;
        }

        Object userIdObj = JwtUtils.getPayloadClaim(accessToken, X_USER_ID);
        if (null == userIdObj) {
            // 请求没有token
            writeForbidden(response);
            return;
        }

        Map<String, String> params = new HashMap<>(4);
        params.put(X_REQUEST_IP, request.getHeader(X_REQUEST_IP));
        params.put(TOKEN_TYPE, String.valueOf(tokenTypeObj));
        params.put(X_USER_ID, String.valueOf(userIdObj));

        // Token信息写入环境变量中
        UserContextHolder.getInstance().setContext(params);

        filterChain.doFilter(request, response);
    }

    protected boolean matcher(Set<String> uriList, String currentUri) {

        boolean match = false;

        for (String only : uriList) {
            match = antPathMatcher.match(only, currentUri);
            if (match) {
                break;
            }
        }

        return match;
    }

    protected void writeForbidden(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding(DEFAULT_CHARSET);
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject(R.forbidden());
        out.write(json.toString());
        out.flush();
    }

}
