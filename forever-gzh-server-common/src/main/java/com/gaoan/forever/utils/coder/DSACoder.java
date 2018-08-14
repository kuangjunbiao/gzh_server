package com.gaoan.forever.utils.coder;

import org.apache.shiro.codec.Base64;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 常用数字签名算法DSA
 *
 * @author flysky
 */
public class DSACoder {
    /**
     * 签名/验证算法:SHA1(最好使用SHA256)
     */
    public static final String SIGNATURE_ALGORITHM = "SHA256withDSA";
    /**
     * DSA密钥长度，SA算法的默认密钥长度是1024
     * 密钥长度必须是64的倍数，在512到1024位之间
     */
    private static final int KEY_SIZE = 2048;
    //JDK数字签名实例
    public static final String KEY_ALGORITHM = "DSA";

    /**
     * 签名
     *
     * @return String 数字签名Base64编码
     */
    public static String sign(String dataStr, String privateKeyBase64) throws Exception {
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
     * @param dataStr      待校验数据
     * @param publicKeyBase64 公钥Base64编码
     * @param signBase64      数字签名Base64编码
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
        keyMap.put("DSAPublicKey", publicKey);
        keyMap.put("DSAPrivateKey", privateKey);
        return keyMap;
    }

    /**
     * 生成DSA秘钥对,经过Base64编码后的
     *
     * @return Map<String   ,       String>
     * @throws Exception
     */
    public static Map<String, String> generateKeyPairs() throws Exception {
        //初始化密钥,生成密钥对  
        Map<String, Object> keyMap = DSACoder.initKey();
        Key key = (Key) keyMap.get("DSAPublicKey");
        //公钥
        byte[] publicKey = key.getEncoded();
        String publicKeyBase64 = Base64.encodeToString(publicKey);
        //私钥
        key = (Key) keyMap.get("DSAPrivateKey");
        byte[] privateKey = key.getEncoded();
        String privateKeyBase64 = Base64.encodeToString(privateKey);

        System.out.println("公钥：\n" + publicKeyBase64);
        System.out.println("私钥：\n" + privateKeyBase64);

        Map<String, String> keyPairs = new HashMap<>();
        keyPairs.put("DSAPublicKey", publicKeyBase64);
        keyPairs.put("DSAPrivateKey", privateKeyBase64);
        return keyPairs;
    }

    public static void main(String[] args) throws Exception {
        generateKeyPairs();
        //初始化密钥
        //生成密钥对
//        String str = "DSA数字签名算法";
//        //公钥
        String selfPublic = "MIIDQjCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veA4IBBQACggEAK6XbJ/nluJdn75CzHyxk59GQ51WFGrobpny+q5xsyXYgoLDMYRZlL31uFPN4xILp67/tnl0aI84mUHe2E4bq/dNy8dGUjxrVsI98Z63ejJyjig/1inomNc/HZ2Uz5Z53kq9+c5iA4zqcCPoT6gGVXeyFp3ztxCnGIf3kRvcPB7AJrl/q66i+HeU0MelQ/HSxyyJlEf1sqOz++92TGMM/BJ9nQ5W+m4xaIwd4QNeWlPvcrZ5n7UCjKJf3wbtk3Youaz6/aozinjyTMqQcQgPu4aSX+BcCTEW4gRHZM1a+domd4ZnTWvaain/DOciOadUMxuCKqp1L4oNkaWW4HMLSeA==";
////        //私钥
        String selfPrivate = "MIICXAIBADCCAjUGByqGSM44BAEwggIoAoIBAQCPeTXZuarpv6vtiHrPSVG28y7FnjuvNxjo6sSWHz79NgbnQ1GpxBgzObgJ58KuHFObp0dbhdARrbi0eYd1SYRpXKwOjxSzNggooi/6JxEKPWKpk0U0CaD+aWxGWPhL3SCBnDcJoBBXsZWtzQAjPbpUhLYpH51kjviDRIZ3l5zsBLQ0pqwudemYXeI9sCkvwRGMn/qdgYHnM423krcw17njSVkvaAmYchU5Feo9a4tGU8YzRY+AOzKkwuDycpAlbk4/ijsIOKHEUOThjBopo33fXqFD3ktm/wSQPtXPFiPhWNSHxgjpfyEc2B3KI8tuOAdl+CLjQr5ITAV2OTlgHNZnAh0AuvaWpoV499/e5/pnyXfHhe8ysjO65YDAvNVpXQKCAQAWplxYIEhQcE51AqOXVwQNNNo6NHjBVNTkpcAtJC7gT5bmHkvQkEq9rI837rHgnzGC0jyQQ8tkL4gAQWDt+coJsyB2p5wypifyRz6Rh5uixOdEvSCBVEy1W4AsNo0fqD7UielOD6BojjJCilx4xHjGjQUntxyaOrsLC+EsRGiWOefTznTbEBplqiuH9kxoJts+xy9LVZmDS7TtsC98kOmkltOlXVNb6/xF1PYZ9j897buHOSXC8iTgdzEpbaiH7B5HSPh++1/et1SEMWsiMt7lU92vAhErDR8C2jCXMiT+J67ai51LKSLZuovjntnhA6Y8UoELxoi34u1DFuHvF9veBB4CHAKrog11Axji6cLimHbcs3oINt1lgklJfbTkicg=";
        String str = "nonce=jIOKAs96hfm1vuJhX7fBgIwntrBZQR4Y&password=admin&username=admin";
        System.out.println("原文====>>>" + str);
        long s = System.nanoTime();
        String sign = DSACoder.sign(str, selfPrivate);
        System.out.println("签名====>>>" + sign);
        boolean verify = DSACoder.verify(str, selfPublic, sign);
        System.out.println("param=" + verify);
    }
}  