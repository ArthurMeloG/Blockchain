package com.example.blockchain.service;

import com.example.blockchain.dto.TransactionDTO;
import com.example.blockchain.entity.Block;
import com.example.blockchain.entity.Transaction;
import com.example.blockchain.repository.TransactionRepository;
import com.example.blockchain.util.RSAEncryption;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.blockchain.util.RSAEncryption.encrypt;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BlockService blockService;

    private final List<Transaction> transactions = new ArrayList<>();

    public String saveTransaction(byte[] base64Pdf, String publicKey) {
        byte[] hashPdf = null;
        try {
            hashPdf = RSAEncryption.encrypt(base64Pdf, publicKey);
        } catch (Exception e) {
            System.out.println(e);
        }
        String transactionHash = RSAEncryption.createHash(Collections.singletonList(Base64.getEncoder().encodeToString(hashPdf)));
        transactions.add(new Transaction(hashPdf, transactionHash, publicKey));
        if(transactions.size() >= 2) {
            List<Transaction> transactionsPersisted = transactionRepository.saveAll(transactions);
            blockService.saveAllTransactionsInBlock(transactionsPersisted);
            transactions.clear();
        }
        return transactionHash;
    }

    @Transactional
    public byte[] getTransaction(String hash, String privateKey) {
        Transaction transaction = transactionRepository.findTransactionsByHash(hash);
        byte[] pdf64 = null;
        try {
            pdf64 = RSAEncryption.decrypt(transaction.getHashedContent(), privateKey);

        } catch (Exception exception) {
            System.out.println("wrong private key");
        }
        return pdf64;
    }

    @Transactional
    public List<TransactionDTO> getAllPdfsFromPublicKey(TransactionDTO transactionDTO) {
        List<Transaction> transactions = transactionRepository.findTransactionsByOwner(transactionDTO.getOwner());
        return  transactions.stream().map(this::convertTransactionToDTO).collect(Collectors.toList());
    }

    public TransactionDTO convertTransactionToDTO(Transaction transaction) {
        return new TransactionDTO(transaction.getHashedContent(),transaction.getHash(), "", "", transaction.getOwner());
    }
}
