package com.summary.common.core.utils.crypto;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.util.StrUtil;
import com.summary.common.core.exception.CustomException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;

import static com.summary.common.core.exception.code.BaseExceptionCode.ENCRYPT_FAIL;

/**
 * DES加密 解密
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class DesUtil {
    private static final String DES = "DES";
    private static final String CHARSET_NAME = "UTF-8";
    /**
     * 默认key
     */
    private static final String DEFAULT_KEY = "DEFAULT_KEY";

    /**
     * 使用 默认key 加密
     *
     * @param data 待加密数据
     * @return 加密后的数据
     */
    public static String encrypt(String data) {
        if (StrUtil.isBlank(data)) {
            return null;
        }
        try {
            byte[] bt = encrypt(data.getBytes(CHARSET_NAME), DEFAULT_KEY.getBytes(CHARSET_NAME));
            return Base64Encoder.encode(bt);
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    /**
     * 使用 默认key 解密
     *
     * @param data 待解密数据
     * @return 机密后的数据
     */
    public static String decrypt(String data) {
        try {
            if (data == null) {
                return null;
            }
            byte[] buf = Base64Decoder.decode(data);
            byte[] bt = decrypt(buf, DEFAULT_KEY.getBytes(CHARSET_NAME));
            return new String(bt, CHARSET_NAME);
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    /**
     * 根据键值进行加密
     *
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     */
    public static String encrypt(String data, String key) {
        try {
            byte[] bt = encrypt(data.getBytes(CHARSET_NAME), key.getBytes(CHARSET_NAME));
            return Base64Encoder.encode(bt);
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    /**
     * 根据键值进行解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key) {
        try {
            if (data == null) {
                return null;
            }
            byte[] buf = Base64Decoder.decode(data);
            byte[] bt = decrypt(buf, key.getBytes(CHARSET_NAME));
            return new String(bt, CHARSET_NAME);
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    /**
     * 根据键值进行加密
     *
     * @param data 加密数据的 byte[]
     * @param key  加密键byte数组
     * @return 机密后的byte[]
     * @throws Exception e
     */
    private static byte[] encrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成加密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }

    /**
     * 根据键值进行解密
     *
     * @param data 解密数据的byte[]
     * @param key  加密键byte数组
     * @return 解密后数据的byte[]
     * @throws Exception e
     */
    private static byte[] decrypt(byte[] data, byte[] key) throws Exception {
        // 生成一个可信任的随机数源
        SecureRandom sr = new SecureRandom();

        // 从原始密钥数据创建DESKeySpec对象
        DESKeySpec dks = new DESKeySpec(key);

        // 创建一个密钥工厂，然后用它把DESKeySpec转换成SecretKey对象
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
        SecretKey secretKey = keyFactory.generateSecret(dks);

        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance(DES);

        // 用密钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, sr);

        return cipher.doFinal(data);
    }

    public static void main(String[] args) {
        String data = "123124151321";
        String key = "qwerrewq";
        System.out.println("加密前===>" + data);
        try {
            String jiamihou1 = encrypt(data);
            String jiamihou2 = encrypt(data);
            System.out.println("加密后1===>" + jiamihou1);
            System.out.println("解密后1===>" + decrypt(jiamihou1));

            System.out.println("加密后2===>" + jiamihou2);
            System.out.println("解密后2===>" + decrypt(jiamihou2));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
