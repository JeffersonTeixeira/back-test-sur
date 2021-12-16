package com.example.surittec.desafio.controller;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.repository.ClientRepository;
import com.example.surittec.desafio.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    ClientService clientService;


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
        } catch (EntityNotFoundException | NoSuchElementException ex) {
            return new ResponseEntity<>(new ErrorMessage("Não encontrado cliente com id:" + client.getId()), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(clientService.getClientById(id).orElseThrow(NoSuchElementException::new));
        } catch (EntityNotFoundException | NoSuchElementException ex) {
            return new ResponseEntity<>(new ErrorMessage("Não encontrado cliente com id:" + id), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> removeClient(@PathVariable Long id) {
        try {
            clientService.removeById(id);
            return ResponseEntity.ok("");
        } catch (EntityNotFoundException | NoSuchElementException ex) {
            return new ResponseEntity<>(new ErrorMessage("Não encontrado cliente com id:" + id), HttpStatus.NOT_FOUND);
        } catch (JsonProcessingException ex) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
