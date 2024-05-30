package com.summary.common.core.utils.crypto;

import cn.hutool.core.util.StrUtil;
import com.summary.common.core.exception.CustomException;

import java.security.MessageDigest;

import static com.summary.common.core.exception.code.BaseExceptionCode.ENCRYPT_FAIL;

/**
 * MD5工具类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class Md5Util {

    private static final String[] HEX_DIG_ITS = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};
    private static final String MD5 = "MD5";
    private static final String CHARSET_NAME = "UTF-8";

    /**
     * MD5加密 , 默认 UTF-8
     *
     * @param origin 加密字符
     * @return 加密后的数据
     */
    public static String encrypt(String origin) {
        return encrypt(origin, CHARSET_NAME);
    }

    /**
     * MD5加密
     *
     * @param origin  加密字符
     * @param charset 编码
     * @return 加密后的数据
     */
    public static String encrypt(String origin, String charset) {
        try {
            MessageDigest md = MessageDigest.getInstance(MD5);
            if (StrUtil.isBlank(charset)) {
                return byteArrayToHexString(md.digest(origin.getBytes()));
            } else {
                return byteArrayToHexString(md.digest(origin.getBytes(charset)));
            }
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    public static String byteArrayToHexString(byte[] bytes) {
        StringBuilder buffer = new StringBuilder();
        for (byte aByte : bytes) {
            buffer.append(byteToHexString(aByte));
        }
        return buffer.toString();
    }

    public static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return HEX_DIG_ITS[d1] + HEX_DIG_ITS[d2];
    }

    public static void main(String[] args) {
        String utf8 = Md5Util.encrypt("123456", CHARSET_NAME);
        System.out.println(utf8);
    }
}
