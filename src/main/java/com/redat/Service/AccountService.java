/**
 * Represents account service
 */
package com.redat.Service;

import com.redat.Model.Account;
import com.redat.Model.Client;
import com.redat.Repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    // Attributes:
    private final AccountRepository accountRepository;

    /**
     * Initialize object
     * @param accountRepository AccountRepository
     */
    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    /**
     * Get client's account
     * @param client Client
     * @return Account
     */
    public Account getAccount(Client client) {
        return accountRepository.findByClient(client);
    }

    /**
     * Update account
     * @param account the account to update
     */
    public void updateAccount(Account account) {
        accountRepository.save(account);
    }
}
