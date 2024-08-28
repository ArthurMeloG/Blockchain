package com.example.blockchain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Base64;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String hash;


    @Column
    @Lob
    private byte[] hashedContent;

    @Column(length = 500)
    private String owner;

    public Transaction (byte[] hashedContent) {
        this.hashedContent = hashedContent;
    }

    public Transaction (byte[] hashedContent, String hash, String owner) {
        this.hashedContent = hashedContent;
        this.hash = hash;
        this.owner = owner;
    }

    public Transaction (String hash) {
        this.hash = hash;
    }
}
