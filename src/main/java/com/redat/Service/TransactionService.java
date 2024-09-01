package com.redat.Service;

import com.redat.Model.Account;
import com.redat.Model.Transaction;
import com.redat.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {
    // Attributes:
    private final TransactionRepository transactionRepository;

    /**
     * Initialize object
     * @param transactionRepository TransactionRepository
     */
    @Autowired
    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    /**
     * Save a transaction
     * @param transaction Transaction
     */
    public void saveTransaction(Transaction transaction) {
        transactionRepository.save(transaction);
    }

    /**
     * Find all transactions for an issuer account
     * @param account Account
     * @return List of transaction
     */
    public List<Transaction> getTransactions(Account account) {
        return transactionRepository.findByIssuerAccount(account);
    }
}
