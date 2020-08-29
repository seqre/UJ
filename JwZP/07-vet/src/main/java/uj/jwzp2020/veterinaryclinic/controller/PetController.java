package uj.jwzp2020.veterinaryclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientResponseDTO;
import uj.jwzp2020.veterinaryclinic.model.pet.Pet;
import uj.jwzp2020.veterinaryclinic.model.pet.dto.PetChangeOwnerDTO;
import uj.jwzp2020.veterinaryclinic.model.pet.dto.PetCreationDTO;
import uj.jwzp2020.veterinaryclinic.model.pet.dto.PetResponseDTO;
import uj.jwzp2020.veterinaryclinic.service.ClientService;
import uj.jwzp2020.veterinaryclinic.service.PetService;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;
    private final ClientService clientService;
    private final ModelMapper modelMapper;

    @Autowired
    public PetController(PetService petService, ClientService clientService, ModelMapper modelMapper) {
        this.petService = petService;
        this.clientService = clientService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @ResponseBody
    public List<PetResponseDTO> getPets() {
        log.info("Got request for pets");
        List<Pet> pets = petService.getPets();
        return pets.stream()
                .map(pet -> modelMapper.map(pet, PetResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public PetResponseDTO getPetById(@PathVariable("id") Long id) {
        log.info("Got request for pet with id " + id);
        Pet pet = petService.getPetById(id);
        return modelMapper.map(pet, PetResponseDTO.class);
    }

    @GetMapping("/{id}/owner")
    @ResponseBody
    public ClientResponseDTO getPetOwnerByPetId(@PathVariable("id") Long id) {
        log.info("Got request for owner of pet with id " + id);
        Pet pet = petService.getPetById(id);
        Client client = clientService.getClientById(pet.getOwnerId());
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @PatchMapping("/{id}/owner")
    @ResponseBody
    @Transactional
    public PetResponseDTO changePetOwnerByPetId(@PathVariable("id") Long id, @RequestBody PetChangeOwnerDTO dto) {
        log.info("Got request for changing owner of pet with id " + id);
        Pet pet = petService.getPetById(id);
        clientService.getClientById(dto.getOwnerId());
        pet.setOwnerId(dto.getOwnerId());
        pet = petService.save(pet);
        return modelMapper.map(pet, PetResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public PetResponseDTO createPet(@RequestBody PetCreationDTO dto) {
        log.info("Got request for creating new pet");
        Pet pet = modelMapper.map(dto, Pet.class);
        pet = petService.save(pet);
        return modelMapper.map(pet, PetResponseDTO.class);
    }
}
