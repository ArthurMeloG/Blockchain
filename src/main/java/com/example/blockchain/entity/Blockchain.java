package com.example.blockchain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Getter
@Setter
public class Blockchain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Block> chain = new ArrayList<>();

}
