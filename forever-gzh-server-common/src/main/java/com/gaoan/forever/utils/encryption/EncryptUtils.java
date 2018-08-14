package com.gaoan.forever.utils.encryption;

/**
 * Created by keymean on 2015/10/6.
 */

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.util.ByteSource;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.PBEKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * 提供部分加密方法
 */
public class EncryptUtils {

    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    /**
     * 对字符串进行MD5进行加密处理
     */
    public static String encryptMD5(String msg){
        return encrypt(msg, null);
    }

    /**
     * 对字符串进行MD5进行加密处理，并处理编码
     * @param origin
     * @param charsetname
     * @return
     */
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }
    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 基本加密处理
     */
    private static String encrypt(String msg, String type){
        MessageDigest md;
        StringBuilder password = new StringBuilder();

        try {
            md = MessageDigest.getInstance("MD5");

            if(StringUtils.isNotBlank(type)){
                md.update(type.getBytes());
            }else {
                md.update(msg.getBytes());
            }

            byte[] bytes = md.digest();
            for (int i = 0; i < bytes.length; i++) {
                String param = Integer.toString((bytes[i] & 0xff) + 0x100, 16);
                password.append(param.substring(1));
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return password.toString();
    }

    public static String encryptSalt(String msg,String salt) {
        return encrypt(msg, salt);
    }

    public static String encryptSHA(String msg,String salt) {
        StringBuilder sb = new StringBuilder();
        try{
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(salt.getBytes());
            byte[] bytes = md.digest(msg.getBytes());
            for(int i=0; i< bytes.length ;i++){
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
        }catch(Exception e){

        }

        return sb.toString();
    }

    public static String encryptPBKDF2(String msg,String salt) {
        try {
            int iterations = 1000;
            char[] chars = msg.toCharArray();
            byte[] saltBytes = salt.getBytes();

            PBEKeySpec spec = new PBEKeySpec(chars, saltBytes, iterations, 64 * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = skf.generateSecret(spec).getEncoded();

            return iterations + toHex(saltBytes) + toHex(hash);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 转化十六进制
     * @param array
     * @return
     */
    private static String toHex(byte[] array) {
        BigInteger bi = new BigInteger(1, array);
        String hex = bi.toString(16);
        int paddingLength = (array.length * 2) - hex.length();
        if(paddingLength > 0) {
            return String.format("%0" + paddingLength + "d", 0) + hex;
        }else{
            return hex;
        }
    }

    private static String getSaltSHA1(){
        SecureRandom sr;
        byte[] salt = new byte[16];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG");
            sr.nextBytes(salt);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return salt.toString();
    }

    private static String getSalt(){
        SecureRandom sr;
        byte[] salt = new byte[16];
        try {
            sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
            sr.nextBytes(salt);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return salt.toString();
    }

    public static byte[] desEdeEcbPkcs5Decrypt(byte[] data,byte[] keyData)throws Exception {
        if(keyData.length<24) {
            keyData = make3DesKey(keyData);
        }
        Key localKey = makeDesKey(keyData);
        Cipher localCipher = Cipher.getInstance("DESede/ECB/PKCS5Padding");
        localCipher.init(Cipher.DECRYPT_MODE, localKey);
        return localCipher.doFinal(data);
    }

    public static byte[] make3DesKey(byte[] keyData) throws Exception {
        byte[] key3 = null;
        byte[] key1 = new byte[8];
        ByteBuffer buf = ByteBuffer.wrap(keyData);
        buf.get(key1);
        buf.clear();
        buf = ByteBuffer.allocate(24);
        buf.put(keyData);
        buf.put(key1);
        buf.flip();
        key3 = buf.array();
        buf.clear();
        return key3;
    }

    public static Key makeDesKey(byte[] keyData)throws Exception {
        DESedeKeySpec keySpec = new DESedeKeySpec(keyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
        return keyFactory.generateSecret(keySpec);
    }
    
    public static String MD5HashSalt(String value, String username) {
    	Md5Hash md5Hash = new Md5Hash(value, ByteSource.Util.bytes(username));
    	return md5Hash.toString();
    }

    public static void main(String[] args) {
	System.out.println(encryptSalt("123456", "admin"));
	System.out.println(new Md5Hash("123456",ByteSource.Util.bytes("admin")));
	System.out.println(new Md5Hash("123456","admin"));
    }
}

