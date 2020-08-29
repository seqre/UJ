package uj.jwzp2020.veterinaryclinic.controller;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import uj.jwzp2020.veterinaryclinic.model.client.Client;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientCreationDTO;
import uj.jwzp2020.veterinaryclinic.model.client.dto.ClientResponseDTO;
import uj.jwzp2020.veterinaryclinic.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ModelMapper modelMapper;
    private final ClientService clientService;

    @Autowired
    public ClientController(ModelMapper modelMapper, ClientService clientService) {
        this.modelMapper = modelMapper;
        this.clientService = clientService;
    }

    @GetMapping
    @ResponseBody
    public List<ClientResponseDTO> getClients() {
        log.info("Got request for clients");
        List<Client> clients = clientService.getClients();
        return clients.stream()
                .map(client -> modelMapper.map(client, ClientResponseDTO.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ClientResponseDTO getClientById(@PathVariable("id") Long id) {
        log.info("Got request for client with id " + id);
        Client client = clientService.getClientById(id);
        return modelMapper.map(client, ClientResponseDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public ClientResponseDTO createClient(@RequestBody ClientCreationDTO dto) {
        log.info("Got request for creating client");
        Client client = modelMapper.map(dto, Client.class);
        client = clientService.save(client);
        return modelMapper.map(client, ClientResponseDTO.class);
    }
}
