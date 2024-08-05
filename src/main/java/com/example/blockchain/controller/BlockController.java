package com.example.blockchain.controller;

import com.example.blockchain.dto.KeyPairDTO;
import com.example.blockchain.dto.TransactionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.blockchain.entity.Block;
import com.example.blockchain.service.BlockService;
import com.example.blockchain.service.TransactionService;
import com.example.blockchain.util.RSAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.util.List;

@RestController
@RequestMapping("/blocks")
public class BlockController {

    @Autowired
    private BlockService blockService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping()
    public List<Block> getAllBlocks() {
        return blockService.getAllBlock();
    }

    @PostMapping("/transaction")
    public String postTransaction(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.saveTransaction(transactionDTO.getBase64Pdf(), transactionDTO.getPublicKey());
    }

    @GetMapping("/createKeyPair")
    @ResponseStatus(HttpStatus.CREATED)
    public KeyPairDTO createKeyPair() {
        return KeyPairDTO.fromKeyPair(RSAEncryption.generateKeyPair());
    }

    @GetMapping("/pdf")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getBase64Pdf(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.getTransaction(transactionDTO.getBase64Pdf(), transactionDTO.getPrivateKey());
    }
}
