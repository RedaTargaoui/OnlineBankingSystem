/**
 * Represents transaction repository
 */
package com.redat.Repository;

import com.redat.Model.Account;
import com.redat.Model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    /**
     * Find all transactions for an issuer account
     * @param account Account
     * @return List of transaction
     */
    List<Transaction> findByIssuerAccount(Account account);
}
