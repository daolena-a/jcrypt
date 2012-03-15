package org.jcrypt.blowfish;

import org.jcrypt.api.IEncryption;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;

/**
 * Created by IntelliJ IDEA.
 * User: adrien.daolena
 * Date: 15/03/12
 * Time: 17:53
 * To change this template use File | Settings | File Templates.
 */
public class BlowfishEncryption implements IEncryption {
    String password;
    Key clef;

    public BlowfishEncryption(final String pass) {
        password =pass;
        try {
            clef = new SecretKeySpec(password.getBytes("UTF-8"), "Blowfish");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] encrypt(final byte[] acrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.ENCRYPT_MODE, clef);
            return cipher.doFinal(acrypt);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] decrypt(final byte[] aDecrypt) {
        try {
            Cipher cipher = Cipher.getInstance("Blowfish");
            cipher.init(Cipher.DECRYPT_MODE, clef);
            byte[] temp = cipher.doFinal(aDecrypt);
            return temp;
        } catch (Exception e) {
            System.out.println("Error during decryption");
            e.printStackTrace();
            return null;
        }
    }
}
