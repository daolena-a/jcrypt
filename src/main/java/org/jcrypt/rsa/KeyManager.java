package org.jcrypt.rsa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
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
 * Time: 11:54
 * To change this template use File | Settings | File Templates.
 */
public class KeyManager {
    String keyPath;
    String publicKeyName = "pub.key";
    String privateKeyName="priv.key";
    public KeyManager(String keyPath) {
        this.keyPath = keyPath;
    }

    public KeyManager(String keyPath, String publicKeyName, String privateKeyName) {
        this.keyPath = keyPath;
        this.publicKeyName = publicKeyName;
        this.privateKeyName = privateKeyName;
    }

    public void generateKeys() {
        try {
            File keyPrev = new File(keyPath+"/"+publicKeyName);
            File keyPub = new File(keyPath+"/"+privateKeyName);

            if(keyPrev.exists() || keyPub.exists()){
                throw new FileAlreadyExistsException(publicKeyName+" "+privateKeyName);
            }
            if (!keyPrev.exists()) {
                keyPrev.createNewFile();
            }
            if (!keyPub.exists()) {
                keyPrev.createNewFile();
            }
            KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
            kpg.initialize(2048, new SecureRandom());
            KeyPair keys = kpg.generateKeyPair();
            RSAPrivateKey privateKey = (RSAPrivateKey) keys.getPrivate();
            RSAPublicKey publicKey = (RSAPublicKey) keys.getPublic();
            FileOutputStream fpriv = new FileOutputStream("key.prv");
            System.out.println(new String(privateKey.getEncoded()));
            fpriv.write(privateKey.getEncoded());
            fpriv.close();
            FileOutputStream fpub = new FileOutputStream("key.pub");
            fpub.write(publicKey.getEncoded());
            fpub.close();
            System.out.println("public key written");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public RSAPublicKey getPublicKey()  {
        FileInputStream pubfile = null;
        RSAPublicKey pubkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pubfile = new FileInputStream("key.pub");
            byte[] byte_pub = new byte[pubfile.available()];
            pubfile.read(byte_pub);
            //     byte_pub = keyEncryptpionManager.decrypter(byte_pub);
            X509EncodedKeySpec pubspec = new X509EncodedKeySpec(byte_pub);
            pubkey = (RSAPublicKey) factory.generatePublic(pubspec);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                pubfile.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return pubkey;

    }

    public RSAPrivateKey getPrivateKey()  {
        FileInputStream prvfile = null;
        RSAPrivateKey prvkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            prvfile = new FileInputStream("key.prv");
            byte[] byte_prv = new byte[prvfile.available()];
            prvfile.read(byte_prv);
            //      byte_prv = keyEncryptpionManager.decrypter(byte_prv);
            PKCS8EncodedKeySpec prvspec = new PKCS8EncodedKeySpec(byte_prv);
            prvkey = (RSAPrivateKey) factory.generatePrivate(prvspec);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                prvfile.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return prvkey;

    }

}
