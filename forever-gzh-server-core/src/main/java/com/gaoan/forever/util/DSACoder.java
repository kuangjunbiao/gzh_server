package com.gaoan.forever.util;

import org.apache.shiro.codec.Base64;

import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 常用数字签名算法DSA
 *
 * @author longshengtang
 */
public class DSACoder {

    //数字签名，密钥算法  
    public static final String KEY_ALGORITHM = "DSA";
    /**
     * 数字签名
     * 签名/验证算法
     */
    public static final String SIGNATURE_ALGORITHM = "SHA1withDSA";

    /**
     * DSA密钥长度，RSA算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到1024位之间
     */
    private static final int KEY_SIZE = 1024;
    //公钥
    private static final String PUBLIC_KEY = "DSAPublicKey";
    //私钥  
    private static final String PRIVATE_KEY = "DSAPrivateKey";

    /**
     * 签名
     *
     * @return String 数字签名Base64编码
     */
    public static String sign(String dataStr, String privateKeyBase64) throws Exception {
//        System.out.println("dataStr======"+dataStr);
        byte[] data = dataStr.getBytes();
        byte[] privateKey = Base64.decode(privateKeyBase64);
        byte[] sign = sign(data, privateKey);
        return Base64.encodeToString(sign);
    }

    /**
     * 签名
     *
     * @param privateKey 私钥字节数组
     * @return byte[] 数字签名
     */
    public static byte[] sign(byte[] data, byte[] privateKey) throws Exception {
        //取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //生成私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        //实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        //初始化Signature
        signature.initSign(priKey);
        //更新
        signature.update(data);
        return signature.sign();
    }

    /**
     * 校验数字签名
     * //     * @param data 待校验数据
     * //     * @param publicKey 公钥Base64编码
     * //     * @param sign 数字签名Base64编码
     *
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(String dataStr, String publicKeyBase64, String signBase64) throws Exception {
        byte[] data = dataStr.getBytes();
        byte[] publicKey = Base64.decode(publicKeyBase64);
        byte[] sign = Base64.decode(signBase64);
        //验证
        return verify(data, publicKey, sign);
    }

    /**
     * 校验数字签名
     *
     * @param data      待校验数据
     * @param publicKey 公钥
     * @param sign      数字签名
     * @return boolean 校验成功返回true，失败返回false
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) throws Exception {
        //转换公钥材料，实例化密钥工厂
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        //初始化公钥，密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
        //产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        //实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        //初始化Signature
        signature.initVerify(pubKey);
        //更新
        signature.update(data);
        //验证
        return signature.verify(sign);
    }

    /**
     * 初始化密钥对
     *
     * @return Map 甲方密钥的Map
     */
    public static Map<String, Object> initKey() throws Exception {
        //实例化密钥生成器  
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
        //初始化密钥生成器  
        keyPairGenerator.initialize(KEY_SIZE, new SecureRandom());
        //生成密钥对  
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        //甲方公钥
        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        //甲方私钥
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();
        //将密钥存储在map中  
        Map<String, Object> keyMap = new HashMap<>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 生成DSA秘钥对,经过Base64编码后的
     *
     * @return Map<String, String>
     * @throws Exception
     */
    public static Map<String, String> generateKeyPairs() throws Exception {
        //初始化密钥,生成密钥对  
        Map<String, Object> keyMap = DSACoder.initKey();
        //公钥
        byte[] publicKey = DSACoder.getPublicKey(keyMap);
        String publicKeyBase64 = Base64.encodeToString(publicKey);
        //私钥
        byte[] privateKey = DSACoder.getPrivateKey(keyMap);
        String privateKeyBase64 = Base64.encodeToString(privateKey);

        System.out.println("公钥：\n" + publicKeyBase64);
        System.out.println("私钥：\n" + privateKeyBase64);

        Map<String, String> keyPairs = new HashMap<>();
        keyPairs.put(PUBLIC_KEY, publicKeyBase64);
        keyPairs.put(PRIVATE_KEY, privateKeyBase64);
        return keyPairs;
    }

    /**
     * 取得私钥
     *
     * @param keyMap 密钥map
     * @return byte[] 私钥
     */
    public static byte[] getPrivateKey(Map<String, Object> keyMap) {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return key.getEncoded();
    }

    /**
     * 取得公钥
     *
     * @param keyMap 密钥map
     * @return byte[] 公钥
     */
    public static byte[] getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return key.getEncoded();
    }

    public static void main(String[] args) throws Exception {
//        generateKeyPairs();
        //初始化密钥
        //生成密钥对
//        String str = "DSA数字签名算法";

        String selfPublic = "MIIBtzCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoDgYQAAoGAW2XG/swfL2Vx26z5UqLpHE2i9O7I1aEdzBp73eYPmVuW9JmUevg4NjOuBu3MEuyCANE0HjX4RCY5if5J2lAb/wUTeBpGPnMfmPEJiRkmNmf6S996+HgolhzPyROEXBweXW5mLfS7KvaGM390ybT73DSSNjl2L3IJCyzaB/8JxL4=";
        String selfPrivate = "MIIBSwIBADCCASwGByqGSM44BAEwggEfAoGBAP1/U4EddRIpUt9KnC7s5Of2EbdSPO9EAMMeP4C2USZpRV1AIlH7WT2NWPq/xfW6MPbLm1Vs14E7gB00b/JmYLdrmVClpJ+f6AR7ECLCT7up1/63xhv4O1fnxqimFQ8E+4P208UewwI1VBNaFpEy9nXzrith1yrv8iIDGZ3RSAHHAhUAl2BQjxUjC8yykrmCouuEC/BYHPUCgYEA9+GghdabPd7LvKtcNrhXuXmUr7v6OuqC+VdMCz0HgmdRWVeOutRZT+ZxBxCBgLRJFnEj6EwoFhO3zwkyjMim4TwWeotUfI0o4KOuHiuzpnWRbqN/C/ohNWLx+2J6ASQ7zKTxvqhRkImog9/hWuWfBpKLZl6Ae1UlZAFMO/7PSSoEFgIUNE8GIjsOuaxRVcUU3GU2vqSLTKI=";
        String str = "agreement=1&reqFlowId=528cc6f4b6ec4d6dac00226ac1aca656&reqSysId=2000&srcReqSysId=1001&username=13829795907@qeveworld.com";
        System.out.println("原文:" + str);
        String sign = DSACoder.sign(str, selfPrivate);
        System.out.println(sign);
        boolean verify = DSACoder.verify(str, selfPublic, sign);
        System.out.println("verify=" + verify);
    }
}  