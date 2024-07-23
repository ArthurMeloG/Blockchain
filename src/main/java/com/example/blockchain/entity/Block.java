package com.example.blockchain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Block {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @PrimaryKeyJoinColumn
    @OneToOne
    private BlockHeader header;

    @PrimaryKeyJoinColumn
    @OneToOne
    private BlockPayload payload;

}
