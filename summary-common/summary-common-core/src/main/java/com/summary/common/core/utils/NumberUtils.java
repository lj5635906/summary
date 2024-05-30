package com.summary.common.core.utils;

import com.summary.common.core.exception.CustomException;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static com.summary.common.core.exception.code.BaseExceptionCode.RATIO_ERROR;

/**
 * 数字工具类
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class NumberUtils {
    /**
     * 不让实例化
     */
    private NumberUtils() {
    }


    /**
     * 将小数字符串转换为百分比字符串
     *
     * @param decimal 需要转换的小数字符串
     * @return 百分比数字符串
     */
    public static String convertToPercentage(String decimal) {
        return new BigDecimal(decimal).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 将百分数字符串转为小数字符串
     *
     * @param percentage 需要转换的百分数字符串
     * @return 小数字符串
     */
    public static String convertToDecimal(String percentage) {
        return new BigDecimal(percentage).divide(new BigDecimal(100), 4, RoundingMode.DOWN).toString();
    }

    /**
     * number1 / number2 转换为百分比
     *
     * @param number1 1
     * @param number2 2
     * @return 百分比数字符串
     */
    public static String convertToPercentage(BigDecimal number1, BigDecimal number2) {
        String number = number1.divide(number2, 4, RoundingMode.HALF_DOWN).toString();
        return convertToPercentage(number);
    }

    /**
     * number1 / number2 转换为百分比 四舍五入
     *
     * @param number1 1
     * @param number2 2
     * @return 百分比数字符串
     */
    public static String convertToPercentageAppend(BigDecimal number1, BigDecimal number2) {
        if (0 == new BigDecimal(0).compareTo(number2)) {
            return "0.00%";
        }
        String number = number1.divide(number2, 4, RoundingMode.HALF_DOWN).toString();
        return convertToPercentage(number) + "%";
    }

    /**
     * number1 / number2
     *
     * @param number1      1
     * @param number2      2
     * @param scale        小数点位数
     * @param roundingMode 取舍规则
     * @return 处理后
     */
    public static BigDecimal divide(BigDecimal number1, BigDecimal number2, int scale, int roundingMode) {
        return number1.divide(number2, scale, roundingMode);
    }

    /**
     * 格式化数字，保留2位小数 四舍五入
     *
     * @param number 数字
     * @return BigDecimal 格式后的数字
     */
    public static BigDecimal numberFormat(BigDecimal number) {
        return number.setScale(2, RoundingMode.HALF_DOWN);
    }

    /**
     * 计算Bmi 保留2位小数 四舍五入
     *
     * @param heightCm 身高（cm）
     * @param weightKg 体重（kg）
     * @return bmi
     */
    public static Double getBmi(Double heightCm, Double weightKg) {
        BigDecimal height = new BigDecimal(heightCm).divide(new BigDecimal(100));
        BigDecimal weight = new BigDecimal(weightKg);
        Double bmi = weight.divide(height.multiply(height), 2, BigDecimal.ROUND_HALF_UP).doubleValue();
        return bmi;
    }

    /**
     * 计算折扣
     *
     * @param amount 价格(分) 100
     * @param ratio  折扣 80=8折，100=不打折
     * @return 折扣后价格(分)
     */
    public static Long calculateDiscount(Long amount, Integer ratio) {
        if (null == ratio || ratio > 100 || ratio < 0) {
            throw new CustomException(RATIO_ERROR);
        }

        BigDecimal a = new BigDecimal(amount);
        BigDecimal r = new BigDecimal(ratio).divide(new BigDecimal(100));

        return a.multiply(r).setScale(0, RoundingMode.HALF_DOWN).longValue();
    }


    /**
     * 计算折扣
     *
     * @param amount 价格(分) 100
     * @param ratio  折扣 8.88=8.88折，5.00=5折
     * @return 折扣后价格(分)
     */
    public static Long calculateDiscount(Long amount, BigDecimal ratio) {
        if (null == ratio || ratio.compareTo(new BigDecimal(10)) > 0) {
            throw new CustomException(RATIO_ERROR);
        }

        BigDecimal a = new BigDecimal(amount);
        BigDecimal r = ratio.divide(new BigDecimal(10));

        return a.multiply(r).setScale(0, RoundingMode.HALF_DOWN).longValue();
    }


    /**
     * 将单位分转换成单位元.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the string
     */
    public static String fenToYuan(Long fen) {
        if (null == fen) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(fen) / 100).setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

    /**
     * 将单位分转换成单位元.
     *
     * @param fen 将要被转换为元的分的数值
     * @return the string
     */
    public static BigDecimal fenToYuanToBigDecimal(Long fen) {
        if (null == fen) {
            return null;
        }
        return BigDecimal.valueOf(Double.valueOf(fen) / 100).setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    /**
     * 单位分转换为元，只保留有效的小数
     * example：
     * 100 -> 1
     * 110 -> 1.1
     * 101 -> 1.01
     *
     * @param fen 将要被转换的分值
     * @return String
     */
    public static String fenToYuanShort(Long fen) {
        if (null == fen) {
            return "0";
        }
        String string = fenToYuanToBigDecimal(fen).toPlainString();
        return string.replaceAll("0+$", "").replaceAll("\\.$", "");
    }

    /**
     * 将单位元转换成单位分.
     *
     * @param yuan 将要被转换为元的分的数值
     * @return the string
     */
    public static Long yuanToFen(BigDecimal yuan) {
        if (null == yuan) {
            return null;
        }
        return yuan.multiply(BigDecimal.valueOf(100)).longValue();
    }


    public static void main(String[] args) {

        System.out.println(new BigDecimal(10.1).compareTo(new BigDecimal(10)) > 0);

    }
}
