package com.example.blockchain.service;

import com.example.blockchain.entity.Block;
import com.example.blockchain.entity.Transaction;
import com.example.blockchain.repository.TransactionRepository;
import com.example.blockchain.util.RSAEncryption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.blockchain.util.RSAEncryption.encrypt;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private BlockService blockService;

    private final List<Transaction> transactions = new ArrayList<>();

    public String saveTransaction(String base64Pdf, String publicKey) {
        String hashContentTransaction = "";
        try {
            hashContentTransaction = RSAEncryption.encrypt(base64Pdf, publicKey);
        } catch (Exception e) {
            System.out.println(e);
        }
        transactions.add(new Transaction(hashContentTransaction));
        if(transactions.size() == 2) {
            List<Transaction> transactionsPersisted = transactionRepository.saveAll(transactions);
            blockService.saveAllTransactionsInBlock(transactionsPersisted);
            transactions.clear();
        }
        return hashContentTransaction;
    }

    public String getTransaction(String hash, String privateKey) {
        Transaction transaction = transactionRepository.findTransactionsByHashedContent(hash);
        String pdf64 = "";
        try {
            pdf64 = RSAEncryption.decrypt(transaction.getHashedContent(), privateKey);

        } catch (Exception exception) {
            return "wrong privateKey";
        }
        return pdf64;
    }
}
