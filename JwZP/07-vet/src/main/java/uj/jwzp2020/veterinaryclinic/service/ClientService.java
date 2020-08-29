package uj.jwzp2020.veterinaryclinic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.repository.ClientRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class ClientService {

    private final ClientRepository clientRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public List<Client> getClients() {
        return clientRepository.findAll();
    }

    public Client getClientById(Long id) {
        return clientRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Unknown client with id " + id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown client with id " + id);
                });
    }

    @Transactional
    public Client save(Client client) {
        log.info("Saving new client: " + client.toString());
        return clientRepository.save(client);
    }


}
