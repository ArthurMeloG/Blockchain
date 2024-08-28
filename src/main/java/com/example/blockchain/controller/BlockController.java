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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.security.KeyPair;
import java.util.Base64;
import java.util.List;

@RestController
@RequestMapping("/blocks")
@CrossOrigin(origins = "http://localhost:3000")
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
    public String postTransaction(
        @RequestParam("pdf") MultipartFile pdf,
        @RequestParam("publicKey") String publicKey,
        @RequestParam("hash") String hash,
        @RequestParam("privateKey") String privateKey,
        @RequestParam("owner") String owner
    ) {
        byte[] pdfBytes;
        try {
            pdfBytes = pdf.getBytes();
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid PDF file", e);
        }
        return transactionService.saveTransaction(pdfBytes, publicKey);
    }

    @GetMapping("/createKeyPair")
    @ResponseStatus(HttpStatus.CREATED)
    public KeyPairDTO createKeyPair() {
        return KeyPairDTO.fromKeyPair(RSAEncryption.generateKeyPair());
    }

    @PostMapping("/pdf")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String getBase64Pdf(@RequestBody TransactionDTO transactionDTO) {
        byte[] response = transactionService.getTransaction(transactionDTO.getHash(), transactionDTO.getPrivateKey());
        return Base64.getEncoder().encodeToString(response);
    }

    @PostMapping("/findPdfByPk")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<TransactionDTO> getAllPdfsByPK(@RequestBody TransactionDTO transactionDTO) {
        return transactionService.getAllPdfsFromPublicKey(transactionDTO);
    }
}
