package com.example.blockchain.service;

import com.example.blockchain.entity.Block;
import com.example.blockchain.entity.BlockPayload;
import com.example.blockchain.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public Block saveBlock(Block block) {
        return blockRepository.save(block);
    }

    public List<Block> getAllBlock() {
        return blockRepository.findAll();
    }

    public List<BlockPayload> findAllBlockPayloads(Block block) {
        return blockRepository.findAllBlockPayloads();
    }

}


