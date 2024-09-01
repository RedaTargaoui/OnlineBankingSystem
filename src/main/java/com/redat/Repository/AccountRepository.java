/**
 * Represents account repository
 */
package com.redat.Repository;

import com.redat.Model.Account;
import com.redat.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {

    /**
     * Find client's account
     * @param client Client
     * @return Account
     */
    Account findByClient(Client client);
}
