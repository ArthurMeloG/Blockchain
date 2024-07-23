package com.example.blockchain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class BlockHeader {

    @Id
    private Long id;

    @Column
    private Long nonce;

    @Column
    private String blockHash;

}
