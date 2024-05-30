package com.summary.common.core.utils;

import cn.hutool.core.date.DatePattern;
import com.summary.common.core.enums.SexEnum;

import java.time.LocalDate;

/**
 * 身份证号码相关util
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class IdentityUtil {

    /**
     * 身份证号码长度
     */
    private static final int ID_CARD_SIZE = 18;

    /**
     * 获取身份证生日
     *
     * @param idCardCode 身份证号码
     * @return 生日 yyyy-MM-dd
     */
    public static String getBirthdayStrByCard(String idCardCode) {
        LocalDate localDate = getBirthdayLocalDateByCard(idCardCode);
        return null == localDate ? null : DateTimeUtils.convertLocalDateToString(localDate);
    }

    /**
     * 获取身份证生日
     *
     * @param idCardCode 身份证号码
     * @return 生日
     */
    public static LocalDate getBirthdayLocalDateByCard(String idCardCode) {
        if (null == idCardCode || idCardCode.length() != ID_CARD_SIZE) {
            return null;
        }
        String substring = idCardCode.substring(6, 14);
        return DateTimeUtils.convertStringToLocalDate(substring, DatePattern.PURE_DATE_PATTERN);
    }

    /**
     * 获取身份证年龄
     *
     * @param idCardCode 身份证号码
     * @return 当前年龄
     */
    public static Integer getAgeByCard(String idCardCode) {
        LocalDate localDate = getBirthdayLocalDateByCard(idCardCode);
        return null == localDate ? null : DateTimeUtils.getAge(localDate);
    }

    /**
     * 获取身份证性别
     *
     * @param idCardCode 身份证号码
     * @return 性别
     */
    public static SexEnum getGenderByCard(String idCardCode) {
        if (null == idCardCode || idCardCode.length() != 18) {
            return null;
        }
        int parseInt = Integer.parseInt(idCardCode.substring(16, 17));
        if (parseInt % 2 == 0) {
            return SexEnum.WOMAN;
        } else {
            return SexEnum.MAN;
        }
    }

    /**
     * 获取身份证性别
     *
     * @param idCardCode 身份证号码
     * @return 性别
     */
    public static Integer getGenderIntByCard(String idCardCode) {
        SexEnum sexEnum = getGenderByCard(idCardCode);
        return null == sexEnum ? null : sexEnum.getCode();
    }
}
