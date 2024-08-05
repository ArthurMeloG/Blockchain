package com.example.blockchain.repository;

import com.example.blockchain.entity.Block;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b FROM Block b WHERE b.id = (SELECT MAX(b2.id) FROM Block b2)")
    Block findLastBlockById();

    long count();
}