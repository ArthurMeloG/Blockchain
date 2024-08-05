package com.example.blockchain.service;

import com.example.blockchain.entity.Block;
import com.example.blockchain.entity.Transaction;
import com.example.blockchain.repository.BlockRepository;
import com.example.blockchain.util.RSAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public String saveAllTransactionsInBlock(List<Transaction> transactions) {
        String hashBlock = RSAEncryption
                .createBlockHash(
                        transactions
                                .stream()
                                .map(Transaction::getHashedContent)
                                .collect(Collectors.toList()));
        var block = new Block();
        block.setTransactions(transactions);
        block.setHashBlock(hashBlock);
        Block previusBlock = blockRepository.findLastBlockById();
        block.setPreviusHash(previusBlock.getHashBlock());
        var response =  blockRepository.save(block);
        return response.getHashBlock();
    }

    public Block createGenesisBlock() {
        Block genesisBlock = new Block();
        var genesisTransaction = new ArrayList<String>();
        genesisTransaction.add("GENESIS");
        genesisBlock.setHashBlock(RSAEncryption.createBlockHash(genesisTransaction));
        return blockRepository.save(genesisBlock);
    }

    public boolean blocksAreEmpty() {
        return blockRepository.count() == 0;
    }
}


