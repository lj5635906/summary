package com.summary.common.core.exception.code;

import java.util.HashMap;
import java.util.Map;

/**
 * 基础异常码
 * 0-9999
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public enum BaseExceptionCode implements CustomCodeService {

    /**
     * 请求成功。
     */
    SUCCESS(0, "请求成功。"),


    UNAUTHORIZED(401, "没有权限访问，请联系管理员！"),
    FORBIDDEN(403, "登录失效，重新登录！"),
    NOT_FOUND(404, "未找到该资源，请核对后再试！"),
    UNSUPPORTED_MEDIA_TYPE(415, "请求方法错误！"),

    SERVER_ERROR(500, "服务器发生错误！"),
    TIME_OUT(504, "连接超时，请稍后重试！"),
    MANY_REQUEST(506, "Too Many Requests"),

    REQUEST_ERROR(550, "请求出现异常！"),
    BLACKLISTED(551, "被加入黑名单！"),
    SERVER_MAINTAIN(552, "后台系统正在升级中，请稍候再试!"),

    BAD_REQUEST(600, "请求参数错误！"),
    BAD_REQUEST_FIELD(601, "参数不合法[{0}] - [{1}]"),

    /**
     * 加解密组件
     */
    RSA_INIT_FAIL(700, "数据加解组件初始化失败"),
    DECRYPT_FAIL(701, "数据解码失败"),
    ENCRYPT_FAIL(702, "数据编码失败"),

    /**
     * 验证码
     */
    CODE_EXPIRE(600, "验证码为空或验证码已过期！"),
    CODE_NOT_MATCH(601, "验证码不匹配！"),
    CODE_NOT_SUPPORT(602, "当前验证码生成器不支持！"),
    CODE_IS_EMPTY(603, "未获取到请求中的验证码"),

    RATIO_ERROR(2002, "折扣计算比例范围[0-100]"),


    GET_HOST_IP_ERROR(2003, "获取本机IP出现异常"),
    GREATER_THAN_MAX_WORK_ID(2004, "每个机房服务器最大work_id: 31"),
    GREATER_THAN_MAX_DATA_ID(2004, "每台设备最大data_id: 31"),

    /**
     * 服务降级相关
     */
    SENTINEL(505, "服务器出小差啦，请稍后重试！"),
    FLOW_EXCEPTION(506, "接口已被限流"),
    DEGRADE_EXCEPTION(507, "接口已被熔断,请稍后再试"),
    PARAM_FLOW_EXCEPTION(508, "热点参数限流"),
    SYSTEM_BLOCK_EXCEPTION(509, "系统规则异常"),
    AUTHORITY_EXCEPTION(510, "授权规则不通过"),

    /**
     * 支付模块
     */
    PAY_WAY_NOT_SUPPORT(1001, "暂不支持该交易方式"),
    PAY_FAIL(1009, "发起支付失败:{0}"),
    /**
     * 未知错误
     */
    UNKNOWN_EXCEPTION(-1, "未知错误");

    private static final Map<Integer, BaseExceptionCode> COMMON_CODE = new HashMap<>();

    static {
        for (BaseExceptionCode errorCode : BaseExceptionCode.values()) {
            COMMON_CODE.put(errorCode.getCode(), errorCode);
        }
    }

    /**
     * 根据响应码获取 BusinessCode
     *
     * @param code 响应码
     * @return BusinessCode
     */
    public static BaseExceptionCode getByCode(int code) {
        BaseExceptionCode result = COMMON_CODE.get(code);
        if (result == null) {
            return UNKNOWN_EXCEPTION;
        }
        return result;
    }

    /**
     * 错误代码
     */
    private final int code;
    /**
     * 错误信息中的补位用的对应的值
     */
    private final Object[] args;
    /**
     * 错误信息
     */
    private final String message;

    BaseExceptionCode(Integer code, String message) {
        this(code, message, null);
    }

    BaseExceptionCode(Integer code, String message, Object[] args) {
        this.code = code;
        this.args = args;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public Object[] getArgs() {
        return this.args;
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
