package com.example.blockchain.repository;

import com.example.blockchain.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {

//    @Query("SELECT t from Transaction t where t.hash = :hashedContent")
    Transaction findTransactionsByHash(String hashedContent);

    List<Transaction> findTransactionsByOwner(@Param("owner") String owner);
}
