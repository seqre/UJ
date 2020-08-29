package uj.jwzp2020.veterinaryclinic.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uj.jwzp2020.veterinaryclinic.model.pet.Pet;
import uj.jwzp2020.veterinaryclinic.repository.ClientRepository;
import uj.jwzp2020.veterinaryclinic.repository.PetRepository;

import javax.transaction.Transactional;
import java.util.List;

@Slf4j
@Service
public class PetService {

    private final PetRepository petRepository;
    private final ClientRepository clientRepository;

    @Autowired
    public PetService(PetRepository petRepository, ClientRepository clientRepository) {
        this.petRepository = petRepository;
        this.clientRepository = clientRepository;
    }

    public List<Pet> getPets() {
        return petRepository.findAll();
    }

    public Pet getPetById(Long id) {
        return petRepository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Unknown pet with id " + id);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown pet with id " + id);
                });
    }

    @Transactional
    public Pet save(Pet pet) {
        clientRepository.findById(pet.getOwnerId())
                .orElseThrow(() -> {
                    log.warn("Unknown owner with id " + pet.getOwnerId());
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "Unknown owner with id " + pet.getOwnerId());
                });

        log.info("Saving new pet: " + pet.toString());
        return petRepository.save(pet);
    }
}
