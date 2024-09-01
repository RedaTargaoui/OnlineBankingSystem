/**
 * Represents client repository
 */
package com.redat.Repository;

import com.redat.Model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    /**
     * Find client by full name and password
     * @param fullName full name
     * @param password password
     * @return Client
     */
    Client findByFullNameAndPassword(String fullName, String password);

    /**
     * Find client by full name
     * @param fullName full name
     * @return Client
     */
    Client findByFullName(String fullName);
}
