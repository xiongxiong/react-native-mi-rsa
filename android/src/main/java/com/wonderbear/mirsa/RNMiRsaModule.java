
package com.wonderbear.mirsa;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.Callback;

import org.bouncycastle.util.encoders.Base64;

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

public class RNMiRsaModule extends ReactContextBaseJavaModule {

    private final ReactApplicationContext reactContext;
    private final KeyPair keyPair = initKey();

    public RNMiRsaModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public String getName() {
        return "RNMiRsa";
    }

    private KeyPair initKey() {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            SecureRandom random = new SecureRandom();
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA", "BC");
            generator.initialize(1024, random);
            return generator.generateKeyPair();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 生成public key
     *
     * @return
     */
    @ReactMethod
    public void generateBase64PublicKey(Callback callback) {
        RSAPublicKey key = (RSAPublicKey) keyPair.getPublic();
        callback.invoke(Base64.toBase64String(key.getEncoded()));
    }

    /**
     * 生成private key
     *
     * @return
     */
    @ReactMethod
    public void generateBase64PrivateKey(Callback callback) {
        RSAPrivateKey key = (RSAPrivateKey) keyPair.getPrivate();
        callback.invoke(Base64.toBase64String(key.getEncoded()));
    }

    /**
     * 加密
     *
     * @param string
     * @return
     */
    @ReactMethod
    public void encryptBase64(String string, String keyStr, Callback callback) {
        callback.invoke(Base64.toBase64String(encrypt(Base64.decode(string), keyStr)));
    }

    @ReactMethod
    private void encrypt(String str, String keyStr, Callback callback) {
        callback.invoke(Base64.toBase64String(encrypt(str.getBytes(), keyStr)));
    }

    private byte[] encrypt(byte[] str, String keyStr) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPublicKey pbk = (RSAPublicKey) getPublicKeyFromPemStr(keyStr);
            cipher.init(Cipher.ENCRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(str);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] encrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPublicKey pbk = (RSAPublicKey) keyPair.getPublic();
            cipher.init(Cipher.ENCRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解密
     *
     * @param string
     * @return
     */
    @ReactMethod
    public void decryptBase64(String string, String keyStr, Callback callback) {
        callback.invoke(new String(decrypt(Base64.decode(string), keyStr)));
    }

    @ReactMethod
    private void decrypt(String str, String keyStr, Callback callback) {
        callback.invoke(new String(decrypt(Base64.decode(str), keyStr)));
    }

    private byte[] decrypt(byte[] str, String keyStr) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPrivateKey pbk = (RSAPrivateKey) getPrivateKeyFromPemStr(keyStr);
            cipher.init(Cipher.DECRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(str);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private byte[] decrypt(byte[] string) {
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
            RSAPrivateKey pbk = (RSAPrivateKey) keyPair.getPrivate();
            cipher.init(Cipher.DECRYPT_MODE, pbk);
            byte[] plainText = cipher.doFinal(string);
            return plainText;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public PrivateKey getPrivateKeyFromPem() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("e:/pkcs8_privatekey.pem"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        byte[] b = Base64.decode(str);

        // 生成私匙
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        return privateKey;
    }

    public PrivateKey getPrivateKeyFromPemStr(String keyStr) throws Exception {
        byte[] b = Base64.decode(keyStr);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(b);
        PrivateKey privateKey = kf.generatePrivate(keySpec);
        return privateKey;
    }

    public PublicKey getPublicKeyFromPem() throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("e:/publickey.pem"));
        String s = br.readLine();
        String str = "";
        s = br.readLine();
        while (s.charAt(0) != '-') {
            str += s + "\r";
            s = br.readLine();
        }
        byte[] b = Base64.decode(str);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }

    public PublicKey getPublicKeyFromPemStr(String keyStr) throws Exception {
        byte[] b = Base64.decode(keyStr);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(b);
        PublicKey pubKey = kf.generatePublic(keySpec);
        return pubKey;
    }
}