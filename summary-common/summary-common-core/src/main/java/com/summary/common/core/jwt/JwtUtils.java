package com.summary.common.core.jwt;

import cn.hutool.core.convert.NumberWithFormat;
import cn.hutool.core.util.StrUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import cn.hutool.jwt.signers.AlgorithmUtil;
import cn.hutool.jwt.signers.HMacJWTSigner;
import cn.hutool.jwt.signers.JWTSigner;
import com.summary.common.core.dto.TokenDTO;
import com.summary.common.core.enums.AppEnum;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import static com.summary.common.core.constant.GlobalConstant.TokenConstant.*;

/**
 * jwt 工具类
 *
 * @author jie.luo
 * @since 2024/5/30
 */
public class JwtUtils {

    /**
     * 默认 2小时 - web - access_token
     */
    private static final long ACCESS_TOKEN_EXPIRE_TIME_WEB = 60 * 60 * 1000 * 2;

    /**
     * 默认 12小时 - web - refreshToken
     */
    private static final long REFRESH_TOKEN_EXPIRE_TIME_WEB = 60 * 60 * 1000 * 12;

    /**
     * 私钥
     */
    private static final String TOKEN_SECRET = "summary_2024_05_30";

    /**
     * JwtToken 载荷信息 中 有效期属性名称
     */
    private static final String JWT_TOKEN_EXP_NAME = "exp";

    private static JWTSigner signer = null;

    static {
        signer = new HMacJWTSigner(AlgorithmUtil.getAlgorithm("HS256"), getJwtTokenSecret());
    }

    /**
     * 构建JwtToken过期时间、当前时间+expireDate
     *
     * @param expireDate 过期时间
     * @return 过期时间
     */
    private static long generateExpireDate(long expireDate) {
        return System.currentTimeMillis() + expireDate;
    }

    private static Map<String, Object> generateJwtHeader() {
        // 设置头部信息
        Map<String, Object> header = new HashMap<>(2);
        header.put("alg", "HS256");
        return header;
    }

    /**
     * 构建密钥
     */
    public static byte[] getJwtTokenSecret() {
        return TOKEN_SECRET.getBytes(StandardCharsets.UTF_8);
    }

    /**
     * 验证JWT Token有效性
     * JwtToken 载荷中必须存在 exp 有效期属性
     *
     * @param token JwtToken
     * @return 是否有效
     */
    public static boolean verify(String token) {
        boolean verify = JWTUtil.verify(token, signer);
        if (!verify) {
            return false;
        }

        Object expObj = getPayloadClaim(token, JWT_TOKEN_EXP_NAME);
        if (expObj == null) {
            return false;
        }

        // 过期时间
        NumberWithFormat exp = (NumberWithFormat) expObj;
        return exp.longValue() >= System.currentTimeMillis();
    }


    /**
     * 获取 jwt 载荷信息 中 属性值
     *
     * @param claim 载荷信息属性名称
     * @return 属性值
     */
    public static Object getPayloadClaim(String token, String claim) {

        if (StrUtil.isBlank(token)) {
            return null;
        }
        JWT jwt = JWTUtil.parseToken(token);
        JWTPayload payload = jwt.getPayload();

        if (null == payload) {
            return null;
        }

        return payload.getClaim(claim);
    }

    public static TokenDTO generateJwtToken(Long adminId, AppEnum appEnum, String requestIp) {

        TokenDTO tokenDTO = new TokenDTO();

        Map<String, Object> payload = new HashMap<>(3);
        payload.put(TOKEN_TYPE, appEnum.getCode());
        payload.put(X_USER_ID, adminId);
        payload.put(X_REQUEST_IP, requestIp);
        payload.put(JWT_TOKEN_EXP_NAME, generateExpireDate(ACCESS_TOKEN_EXPIRE_TIME_WEB));

        String accessToken = JWTUtil.createToken(generateJwtHeader(), payload, signer);
        tokenDTO.setAccessToken(accessToken);

        payload.put(JWT_TOKEN_EXP_NAME, generateExpireDate(REFRESH_TOKEN_EXPIRE_TIME_WEB));
        String refreshToken = JWTUtil.createToken(generateJwtHeader(), payload, signer);
        tokenDTO.setRefreshToken(refreshToken);

        return tokenDTO;
    }

//    public static void main(String[] args) {
//        Map<String, Object> payload = new HashMap<>(3);
//        payload.put(X_USER_ID, "123456789");
//        payload.put(X_REQUEST_IP, "192.168.100.205");
//        payload.put(JWT_TOKEN_EXP_NAME, generateExpireDate(ACCESS_TOKEN_EXPIRE_TIME_WEB));
//
//        String token = JWTUtil.createToken(generateJwtHeader(), payload, signer);
//
//        Object adminId = JwtUtils.getPayloadClaim(token, X_USER_ID);
//        Object requestIp = JwtUtils.getPayloadClaim(token, X_REQUEST_IP);
//        Object exp = JwtUtils.getPayloadClaim(token, JWT_TOKEN_EXP_NAME);
//
//        System.out.println(adminId);
//        System.out.println(requestIp);
//        System.out.println(exp);
//
////        JWT jwt = JWTUtil.parseToken(token);
////        JWTPayload payload1 = jwt.getPayload();
////
////        System.out.println(payload1.getClaim(X_ADMIN_ID));
////        System.out.println(payload1.getClaim(X_REQUEST_IP));
////        System.out.println(payload1.getClaim("exp"));
////        System.out.println(token);
//
////        try {
////            Thread.sleep(5000);
////        } catch (InterruptedException e) {
////            throw new RuntimeException(e);
////        }
////        boolean verify = verify(token);
////        System.out.println(verify);
//
//
//    }

}
