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
 *
 */
public class KeyManager {
    String keyPath;
    String publicKeyName = "pub.key";
    String privateKeyName="priv.key";
    public KeyManager(String keyPath) {
        this.keyPath = keyPath;
    }

    public KeyManager(String keyPath, String publicKeyName, String privateKeyName) {
        if(keyPath.endsWith("/")){
            this.keyPath = keyPath;
        }
        else{
            this.keyPath = keyPath+"/";
        }

        this.publicKeyName = publicKeyName;
        this.privateKeyName = privateKeyName;
    }

    public final void generateKeys() {
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
            FileOutputStream fPriv = new FileOutputStream("key.prv");
            System.out.println(new String(privateKey.getEncoded()));
            fPriv.write(privateKey.getEncoded());
            fPriv.close();
            FileOutputStream fPub = new FileOutputStream("key.pub");
            fPub.write(publicKey.getEncoded());
            fPub.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public final RSAPublicKey getPublicKey()  {
        FileInputStream pubfile = null;
        RSAPublicKey pubkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            pubfile = new FileInputStream(keyPath+publicKeyName);
            byte[] byte_pub = new byte[pubfile.available()];
            pubfile.read(byte_pub);
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

    public final RSAPrivateKey getPrivateKey()  {
        FileInputStream prvfile = null;
        RSAPrivateKey prvkey = null;
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            prvfile = new FileInputStream(keyPath+privateKeyName);
            byte[] bytePrv = new byte[prvfile.available()];
            prvfile.read(bytePrv);
            PKCS8EncodedKeySpec prvSpec = new PKCS8EncodedKeySpec(bytePrv);
            prvkey = (RSAPrivateKey) factory.generatePrivate(prvSpec);
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
