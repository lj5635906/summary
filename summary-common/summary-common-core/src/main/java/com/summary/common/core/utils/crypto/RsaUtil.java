package com.summary.common.core.utils.crypto;


import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.codec.Base64Encoder;
import com.summary.common.core.exception.CustomException;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.summary.common.core.exception.code.BaseExceptionCode.*;

/**
 * RSA 加密、解密工具
 *
 * @author jie.luo
 * @since 2024/5/29
 */
public class RsaUtil {

    private static final String CHARSET_NAME = "UTF-8";
    private static final String RSA = "RSA";
    private static KeyPair KEY_PAIR = null;

    static {
        try {
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance(RSA);
            generator.initialize(1024, random);
            KEY_PAIR = generator.generateKeyPair();
        } catch (Exception e) {
            throw new CustomException(RSA_INIT_FAIL);
        }
    }

    /**
     * 获取 base64 编码后的公钥
     *
     * @return 公钥
     */
    public static String generatePublicKey() {
        PublicKey publicKey = KEY_PAIR.getPublic();
        return new String(Base64Encoder.encode(publicKey.getEncoded()));
    }

    /**
     * 获取 base64 编码后的私钥
     *
     * @return 私钥
     */
    public static String generatePrivateKey() {
        PrivateKey privateKey = KEY_PAIR.getPrivate();
        return new String(Base64Encoder.encode(privateKey.getEncoded()));
    }

    /**
     * 数据编码
     *
     * @param str 待编码的字符串
     * @return 编码后的数据
     */
    public static String encrypt(String str) {
        // base64编码的公钥
        byte[] decoded = KEY_PAIR.getPublic().getEncoded();
        return encrypt(str, decoded);
    }

    /**
     * 数据编码
     *
     * @param str       待编码的字符串
     * @param publicKey 公钥
     * @return 编码后的数据
     */
    public static String encrypt(String str, String publicKey) {
        // base64编码的公钥
        byte[] decoded = Base64Decoder.decode(publicKey);
        return encrypt(str, decoded);
    }

    /**
     * 数据编码
     *
     * @param str       待编码的字符串
     * @param publicKey 公钥
     * @return 编码后的数据
     */
    private static String encrypt(String str, byte[] publicKey) {
        try {
            RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance(RSA).generatePublic(new X509EncodedKeySpec(publicKey));
            // RSA加密
            Cipher cipher = Cipher.getInstance(RSA);
            cipher.init(Cipher.ENCRYPT_MODE, pubKey);
            return Base64Encoder.encode(cipher.doFinal(str.getBytes(CHARSET_NAME)));
        } catch (Exception e) {
            throw new CustomException(ENCRYPT_FAIL);
        }
    }

    /**
     * base64 的数据进行解码
     *
     * @param string 需要解码的字符串
     * @return 解码后的数据
     */
    public static String decrypt(String string) {
        try {
            // 64位解码加密后的字符串
            byte[] inputByte = Base64Decoder.decode(string.getBytes(CHARSET_NAME));
            // base64编码的私钥
            byte[] privateKeyBytes = KEY_PAIR.getPrivate().getEncoded();

            return decrypt(inputByte, privateKeyBytes);
        } catch (Exception e) {
            throw new CustomException(DECRYPT_FAIL);
        }
    }

    /**
     * base64 的数据进行解码
     *
     * @param string     需要解码的字符串
     * @param privateKey 私钥
     * @return 解码后的数据
     */
    public static String decrypt(String string, String privateKey) {
        try {
            // 64位解码加密后的字符串
            byte[] inputByte = Base64Decoder.decode(string.getBytes(CHARSET_NAME));
            // base64编码的私钥
            byte[] privateKeyBytes = Base64Decoder.decode(privateKey);

            return decrypt(inputByte, privateKeyBytes);
        } catch (Exception e) {
            throw new CustomException(DECRYPT_FAIL);
        }
    }

    /**
     * 数据解码
     *
     * @param byteArray 需要解码 byteArray
     * @return 解码后的数据
     */
    private static String decrypt(byte[] byteArray, byte[] privateKeyBytes) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);
        PrivateKey privateKey = KeyFactory.getInstance(RSA).generatePrivate(new PKCS8EncodedKeySpec(privateKeyBytes));
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(byteArray));
    }

//    public static void main(String[] args) {
//
////        String encrypt = RsaUtil.encrypt("123456");
////        System.out.println("encrypt: " + encrypt);
////        String decrypt = RsaUtil.decrypt(encrypt);
////        System.out.println("decrypt: " + decrypt);
////
////
////        String privateKey = RsaUtil.generatePrivateKey();
////        String publicKey = RsaUtil.generatePublicKey();
////        System.out.println("privateKey: " + privateKey);
////        System.out.println("publicKey: " + publicKey);
////        System.out.println();
////
////        String encrypt1 = RsaUtil.encrypt("123456789", publicKey);
////        System.out.println("encrypt1: " + encrypt1);
////        String decrypt1 = RsaUtil.decrypt(encrypt1, privateKey);
////        System.out.println("decrypt1: " + decrypt1);
////
////        String e2 = "kUsjhFb3lpq4l2XegliufHFfr31ZwsE1jTRc3GktH+9T0GhCFTIHYiBPh9k5cOuZFncS0J1AVtDPdV0peLZYx9172nVomK+lr7pbUi50s1ZpEdWScdVj7IY3KhTQUeS+EfU9iw0RhXEjN/xPbSm8FaGCmu4FPTPaMfoqcugrT0g=";
////        System.out.println(RsaUtil.decrypt(e2));
//
//
////        String data = "510902199309117833";
////        String encrypt1 = RsaUtil.encrypt(data, "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCSCSz8bGAlifXmzSRbZmFlNaO0urwWNy48QPPJ6tNAWHQsQhZ2UVHIDXovfovTw7cUcuprdsuFxjtH58+Mw2rIvtFvdLuKHDcRZzhR+C8tS/ucsRh1KEKitQlMQZ/iFqxe4KSvpz5ZsTzellcehLwzTZ0+zO/apERts1pLtwMaUwIDAQAB");
////        System.out.println("加密前：" + data);
////        System.out.println("加密后：" + encrypt1);
////        System.out.println("解密后：" + RsaUtil.decrypt(encrypt1,"MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJIJLPxsYCWJ9ebNJFtmYWU1o7S6vBY3LjxA88nq00BYdCxCFnZRUcgNei9+i9PDtxRy6mt2y4XGO0fnz4zDasi+0W90u4ocNxFnOFH4Ly1L+5yxGHUoQqK1CUxBn+IWrF7gpK+nPlmxPN6WVx6EvDNNnT7M79qkRG2zWku3AxpTAgMBAAECgYBvGAvg+5Eo/YrTybuezIPLOsMO+f+b5t6Fd+P992Y9zQyTnrL3z2UnixkoiFxW/MyKAeHTXsv1OJhgWzjJHOBeKqZITgA1GI0ODWtuGn60RLqa+qe1K1vnRg+aTL2RXUF5VV+hsv/S/2LrdBHUFu7jXGutEKlrv6BZT5GfnWT4AQJBAOxmWb8n0rGUM0jf80fT/U7aESZXEqPw+kl/8vKQITOMaxG//W144Mj0l+NptJkHx6TkFtH+xW65Mzz/96m9H+MCQQCeJNNjDE51JCXzqD/EgD8WhfEkie4V0BVZgkgTIQQeLkvDQ2LuWAJG+oAXLCO2x9a7tNXXqcoSUZc3sYAszEbRAkEAtJVO824pTcvHDWE+B12R//0m4qHbNG7t0t2xqceeBTG7zJhbsJeJJxlpOahS9B90S6qNICc9jGJfGLbz6q71PwJAI/Yt1605viudMb5F2fJr5jxJLMzsxisOX89fgnIUlKRkeB+/xIFg6zEVpa9U48Cn1QF3S1aB7A5/u5tykT1WsQJAK7yLUHgNgjVART41WYOpmxVrZ9Clewn37fyCS/n5sTUn7yMVWhx3aDI4MAyWDDrPBBdbCFs25154VJynZR0Srg=="));
//
//        String encrypt1 = "gDNPHkUuMctPaB6dZAiEHGIN7g7SwR1rvHUlFUevFx1w8y+OyXSgaM1fpBA1CBbw9Ho80o0scTcrgLYsZ2xqTvSlOP4AhsC9/xTBHzBKYPh6KqC99lT3xF1Dqrss/0VyyWMCUqEgsJX9c0Sbq4CZEyGe1upeQBZNOlt9EXBNCjA=";
//        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJIJLPxsYCWJ9ebNJFtmYWU1o7S6vBY3LjxA88nq00BYdCxCFnZRUcgNei9+i9PDtxRy6mt2y4XGO0fnz4zDasi+0W90u4ocNxFnOFH4Ly1L+5yxGHUoQqK1CUxBn+IWrF7gpK+nPlmxPN6WVx6EvDNNnT7M79qkRG2zWku3AxpTAgMBAAECgYBvGAvg+5Eo/YrTybuezIPLOsMO+f+b5t6Fd+P992Y9zQyTnrL3z2UnixkoiFxW/MyKAeHTXsv1OJhgWzjJHOBeKqZITgA1GI0ODWtuGn60RLqa+qe1K1vnRg+aTL2RXUF5VV+hsv/S/2LrdBHUFu7jXGutEKlrv6BZT5GfnWT4AQJBAOxmWb8n0rGUM0jf80fT/U7aESZXEqPw+kl/8vKQITOMaxG//W144Mj0l+NptJkHx6TkFtH+xW65Mzz/96m9H+MCQQCeJNNjDE51JCXzqD/EgD8WhfEkie4V0BVZgkgTIQQeLkvDQ2LuWAJG+oAXLCO2x9a7tNXXqcoSUZc3sYAszEbRAkEAtJVO824pTcvHDWE+B12R//0m4qHbNG7t0t2xqceeBTG7zJhbsJeJJxlpOahS9B90S6qNICc9jGJfGLbz6q71PwJAI/Yt1605viudMb5F2fJr5jxJLMzsxisOX89fgnIUlKRkeB+/xIFg6zEVpa9U48Cn1QF3S1aB7A5/u5tykT1WsQJAK7yLUHgNgjVART41WYOpmxVrZ9Clewn37fyCS/n5sTUn7yMVWhx3aDI4MAyWDDrPBBdbCFs25154VJynZR0Srg==";
//        String decrypt1 = RsaUtil.decrypt(encrypt1, privateKey);
//        System.out.println("decrypt1: " + decrypt1);
//    }
}