package com.example.blockchain;

import com.example.blockchain.service.BlockService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BlockchainApplication {

    private final BlockService blockService;

    // Constructor injection
    public BlockchainApplication(BlockService blockService) {
        this.blockService = blockService;
    }

    public static void main(String[] args) {
        SpringApplication.run(BlockchainApplication.class, args).getBean(BlockchainApplication.class).buildDatabaseBlockchain();
    }

    private void buildDatabaseBlockchain() {
        if (blockService.blocksAreEmpty()) {
            blockService.createGenesisBlock();
        }
    }
}
