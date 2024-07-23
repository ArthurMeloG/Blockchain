package com.example.blockchain.repository;

import com.example.blockchain.entity.Block;
import com.example.blockchain.entity.BlockPayload;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlockRepository extends JpaRepository<Block, Long> {

    @Query("SELECT b.payload FROM Block b")
    List<BlockPayload> findAllBlockPayloads();
}