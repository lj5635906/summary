package com.summary.common.core.utils;

import java.math.BigDecimal;

import static java.util.regex.Pattern.compile;

/**
 * @author jie.luo
 * @since 2024/5/30
 */
public class RegexUtil {

    private RegexUtil() {
        // Not allowed use this constructor to get a instance of this class, so
        // declare it to private
    }

    /**
     * 验证“密码”的规则。
     * 密码校验规则. 至少8个字符并至少包含一个大写字母、一个小写字母和一个数字
     *
     * @param str 密码
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validatePassword(String str) {
        return compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,}$").matcher(str).matches();
    }

    /**
     * 验证“手机号”的规则。
     *
     * @param str 手机号
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validateMobile(String str) {
        return compile("1\\d{10}").matcher(str).matches();
    }

    /**
     * 验证“邮箱”的规则。
     * 以任意字母和数字开头，并且在特殊字符“@”前、后必须是数字和字母，最后在字符“.”之后只能以最少2个字母结束。
     *
     * @param str 邮箱
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validateEmail(String str) {
        return compile("^([a-z0-9A-Z]+[-|.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$").matcher(str).matches();
    }

    /**
     * 验证“昵称”的规则。
     * 长度为8-16位的字母和数字的组合。
     *
     * @param str 昵称
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validateNickname(String str) {
        return compile("^[0-9a-zA-Z]{8,16}$").matcher(str).matches();
    }

    /**
     * 验证“正整数”的规则。
     * 任意长度的正整数
     *
     * @param number 正整数
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validateNumber(BigDecimal number) {
        return compile("^[1-9]+\\d*$").matcher(number.toString()).matches();
    }

    /**
     * 验证“正整数”的规则。
     * 任意长度的正整数或者最多2位小数的数
     *
     * @param number 正整数
     * @return 匹配规则返回true，不匹配返回false。
     */
    public static boolean validateNumberMaxTwoPoint(BigDecimal number) {
        return compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$").matcher(number.toString()).matches();
    }

    public static void main(String[] args) {

//        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(9999999.25)));
//        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(1)));


        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(1)));
        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(0)));
        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(-1)));
        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(0.5)));
        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(11.235)));
        System.out.println(validateNumberMaxTwoPoint(new BigDecimal(11.23)));

    }
}
