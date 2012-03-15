package org.jcrypt.rsa;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by IntelliJ IDEA.
 * User: adrien.daolena
 * Date: 15/03/12
 * Time: 11:22
 * To change this template use File | Settings | File Templates.
 */
public class RSAEncryption {
    KeyManager keyManager;

    public RSAEncryption(KeyManager keyManager) {
        this.keyManager = keyManager;
    }

    public byte[] rsaEncrypt(byte[] data){
        RSAPublicKey publicKey = keyManager.getPublicKey();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            cipherData = cipher.doFinal(data);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }

    public byte[] rsaDecrypt(byte[] data)  {
        RSAPrivateKey privateKey = keyManager.getPrivateKey();
        byte[] cipherData = null;
        try {
            Cipher cipher = Cipher.getInstance("RSA");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            cipherData = cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cipherData;

    }
}
