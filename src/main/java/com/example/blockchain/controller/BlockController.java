package com.example.blockchain.controller;

import com.example.blockchain.entity.Block;
import com.example.blockchain.service.BlockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @GetMapping()
    public List<Block> getAllBlocks() {
        return blockService.getAllBlock();
    }
}
