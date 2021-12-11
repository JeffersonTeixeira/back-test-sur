package com.example.surittec.desafio.controller;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

    @Autowired
    ClientService clientService;


    @GetMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> getClientes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(clientService.getAll(paging));
    }


    @PostMapping("/save")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> saveClient(@Valid @RequestBody Client client) {

        if (client.getId() == null) {
            return new ResponseEntity<>(clientService.save(client), HttpStatus.CREATED);
        } else {
            return ResponseEntity.ok(clientService.save(client));
        }
    }
}
