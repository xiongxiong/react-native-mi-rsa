package com.wonderbear.mirsa;

import android.util.Base64;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Security;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by wonderbear on 27/10/2017.
 */

public class RSAUtils {

    private static final KeyPair keyPair = initKey();

    private static KeyPair initKey(){
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成public key
     * @return
     */
    public static String generateBase64PublicKey(){
        RSAPublicKey key = (RSAPublicKey)keyPair.getPublic();
        return new String(Base64.encode(key.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 生成private key
     * @return
     */
    public static String generateBase64PrivateKey(){
        RSAPrivateKey key = (RSAPrivateKey)keyPair.getPrivate();
        return new String(Base64.encode(key.getEncoded(), Base64.DEFAULT));
    }

    /**
     * 加密
     * @param string
     * @return
     */
    public static String encryptBase64(String string) {
        return new String(decrypt(Base64.decode(string, Base64.DEFAULT)));
    }

    /**
     * 解密
     * @param string
     * @return
     */
    public static String decryptBase64(String string) {
        return new String(decrypt(Base64.decode(string, Base64.DEFAULT)));
    }

    private static byte[] encrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPublicKey pbk = (RSAPublicKey)keyPair.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] decrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPrivateKey pbk = (RSAPrivateKey)keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static PrivateKey getPrivateKeyFromPem() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("e:/pkcs8_privatekey.pem"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        byte[] b = Base64.decode(str, Base64.DEFAULT);

        // 生成私匙
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        return privateKey;
    }

    public static PublicKey getPublicKeyFromPem() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("e:/publickey.pem"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        byte[] b = Base64.decode(str, Base64.DEFAULT);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }
}
