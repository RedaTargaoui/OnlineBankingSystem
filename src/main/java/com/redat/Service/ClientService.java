/**
 * Represents client service
 */
package com.redat.Service;

import com.redat.Model.Client;
import com.redat.Repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    // Attributes:
    private final ClientRepository clientRepository;

    /**
     * Initialize object
     * @param clientRepository ClientRepository
     */
    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    /**
     * Find client by full name and password
     * @param fullName full name
     * @param password password
     * @return Client
     */
    public Client findClient(String fullName, String password) {
        return clientRepository.findByFullNameAndPassword(fullName, password);
    }

    /**
     * Find client by full name
     * @param fullName full name
     * @return Client
     */
    public Client findClient(String fullName) {
        return clientRepository.findByFullName(fullName);
    }

    /**
     * Save a client
     * @param client Client
     */
    public void saveClient(Client client) {
        clientRepository.save(client);
    }
}
