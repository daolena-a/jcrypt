package org.jcrypt.api;


public interface IEncryption {
    byte[] encrypt(byte[] data);
    byte[] decrypt(byte[] data);
}
