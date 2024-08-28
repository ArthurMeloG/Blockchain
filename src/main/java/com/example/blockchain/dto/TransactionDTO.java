package com.example.blockchain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransactionDTO {
    private byte[] base64Pdf;
    private String hash;
    private String privateKey;
    private String publicKey;
    private String owner;
}
