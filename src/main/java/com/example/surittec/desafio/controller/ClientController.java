package com.example.surittec.desafio.controller;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.exception.ObsoleteDataEntity;
import com.example.surittec.desafio.service.ClientService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> getClients(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(clientService.getAll(paging));
    }


    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveClient(@Valid @RequestBody Client client) {
        try {
            if (client.getId() == null) {
                return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
            } else {
                return ResponseEntity.ok(clientService.save(client));
            }
        } catch (ObsoleteDataEntity ex) {
            return new ResponseEntity<>(ex.getLatestVersion(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> getClient(@PathVariable Long id) {

        Client client = clientService.getClientById(id).orElseThrow(EntityNotFoundException::new);
        client.setVersion(client.hashCode());
        return ResponseEntity.ok(client);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> removeClient(@PathVariable Long id) {

        clientService.removeById(id);
        return ResponseEntity.ok("");

    }

}
