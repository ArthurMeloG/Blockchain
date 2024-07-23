package com.example.blockchain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table
@Getter
@Setter
public class BlockPayload {

    @Id
    private Long id;

    @Column
    private Long sequence;

    @Column
    private Instant timestamp;

    @Column
    private String data;

    @Column
    private String previousHash;

}
