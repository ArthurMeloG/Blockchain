package com.example.blockchain.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.List;

public class RSAEncryption {

    private static final int AES_KEY_SIZE = 256;
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private static final int RSA_KEY_SIZE = 2048;

    // Encrypts byte[] data with AES, then encrypts the AES key with RSA
    public static byte[] encrypt(byte[] data, String base64PublicKey) throws Exception {
        // Generate AES key
        String aesKey = generateAESKey();

        // Encrypt the data using AES
        byte[] encryptedData = encryptAES(data, aesKey);

        // Encrypt the AES key using RSA
        byte[] encryptedAESKey = encryptRSA(aesKey.getBytes("UTF-8"), base64PublicKey);

        // Combine the encrypted AES key and the encrypted data
        byte[] combined = new byte[encryptedAESKey.length + encryptedData.length];
        System.arraycopy(encryptedAESKey, 0, combined, 0, encryptedAESKey.length);
        System.arraycopy(encryptedData, 0, combined, encryptedAESKey.length, encryptedData.length);

        return combined;
    }

    // Decrypts byte[] data by first decrypting the AES key with RSA, then using the AES key to decrypt the data
    public static byte[] decrypt(byte[] encryptedData, String base64PrivateKey) throws Exception {
        // Assuming the first part of encryptedData is the encrypted AES key, and the rest is the encrypted content
        byte[] encryptedAESKey = new byte[RSA_KEY_SIZE / 8]; // RSA key size divided by 8 gives the byte length
        byte[] encryptedContent = new byte[encryptedData.length - encryptedAESKey.length];

        System.arraycopy(encryptedData, 0, encryptedAESKey, 0, encryptedAESKey.length);
        System.arraycopy(encryptedData, encryptedAESKey.length, encryptedContent, 0, encryptedContent.length);

        // Decrypt the AES key using RSA
        byte[] aesKeyBytes = decryptRSA(encryptedAESKey, base64PrivateKey);

        // Decrypt the data using AES
        return decryptAES(encryptedContent, new String(aesKeyBytes, "UTF-8"));
    }

    public static KeyPair generateKeyPair()  {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        } catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            throw new RuntimeException();
        }
        keyPairGenerator.initialize(RSA_KEY_SIZE);
        return keyPairGenerator.generateKeyPair();
    }

    private static byte[] encryptAES(byte[] data, String aesKey) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    private static byte[] decryptAES(byte[] encryptedData, String aesKey) throws Exception {
        SecretKey secretKey = new SecretKeySpec(Base64.getDecoder().decode(aesKey), AES_ALGORITHM);
        Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(encryptedData);
    }

    private static byte[] encryptRSA(byte[] data, String base64PublicKey) throws Exception {
        byte[] publicKeyBytes = Base64.getDecoder().decode(base64PublicKey);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

        return cipher.doFinal(data);
    }

    private static byte[] decryptRSA(byte[] encryptedData, String base64PrivateKey) throws Exception {
        byte[] privateKeyBytes = Base64.getDecoder().decode(base64PrivateKey);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);

        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        return cipher.doFinal(encryptedData);
    }

    private static String generateAESKey() throws Exception {
        KeyGenerator keyGen = KeyGenerator.getInstance(AES_ALGORITHM);
        keyGen.init(AES_KEY_SIZE);
        SecretKey secretKey = keyGen.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());
    }

    public static String createHash(List<String> hashes) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            StringBuilder concatenatedHashes = new StringBuilder();
            for (String transaction : hashes) {
                String transactionHash = hash(transaction, digest);
                concatenatedHashes.append(transactionHash);
            }

            return hash(concatenatedHashes.toString(), digest);

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao calcular o hash do bloco: ", e);
        }
    }

    private static String hash(String data, MessageDigest digest) {
        byte[] hashBytes = digest.digest(data.getBytes());
        StringBuilder hexString = new StringBuilder();

        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }
}
