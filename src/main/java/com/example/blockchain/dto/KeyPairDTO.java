package com.example.blockchain.dto;

import java.util.Base64;
import java.security.KeyPair;

public class KeyPairDTO {
    private String publicKey;
    private String privateKey;

    public KeyPairDTO(String publicKey, String privateKey) {
        this.publicKey = publicKey;
        this.privateKey = privateKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public static KeyPairDTO fromKeyPair(KeyPair keyPair) {
        String publicKey = Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded());
        String privateKey = Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded());
        return new KeyPairDTO(publicKey, privateKey);
    }
}
