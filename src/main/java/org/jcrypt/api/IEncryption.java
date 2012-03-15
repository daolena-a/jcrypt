package org.jcrypt.api;

/**
 * Created by IntelliJ IDEA.
 * User: adrien.daolena
 * Date: 15/03/12
 * Time: 11:25
 * To change this template use File | Settings | File Templates.
 */
public interface IEncryption {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
