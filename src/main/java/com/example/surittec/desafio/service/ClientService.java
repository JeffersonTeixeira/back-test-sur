package com.example.surittec.desafio.service;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.domain.Operation;
import com.example.surittec.desafio.exception.ObsoleteDataEntity;
import com.example.surittec.desafio.repository.ClientRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    OperationService operationService;

    @Autowired
    private ModelMapper modelMapper;

    public Optional<Client> getClientById(Long id) {
        Operation operation = new Operation();
        operation.setOperation("select client id: " + id);
        operation.setUser(operationService.getAuthenticatedUser());
        operation.setDate(LocalDateTime.now());
        operationService.save(operation);
        return clientRepository.findByid(id);
    }

    public Page<Client> getAll(Pageable pageable) {
        //@TODO Does register the operation really necessary?
        Operation operation = new Operation();
        operation.setOperation("select all clients");
        operation.setUser(operationService.getAuthenticatedUser());
        operation.setDate(LocalDateTime.now());

        operationService.save(operation);
        return clientRepository.findAllByOrderByNameAsc(pageable);
    }

    @Transactional
    public Client save(Client client) throws ObsoleteDataEntity {
        if (client.getEmails() != null) {
            client.getEmails().remove(client.getEmail());
        }
        if (client.getPhones() != null) {
            client.getPhones().remove(client.getPhone());
        }

        Operation operation = new Operation();
        if (client.getId() == null) {
            operation.setOperation("create client");
        } else {
            Client dbVersion = this.getClientById(client.getId()).orElseThrow(EntityNotFoundException::new);
            operation.setDataBefore(operationService.stringfyObject(dbVersion));
            operation.setOperation("update client");

            if (client.getVersion() != dbVersion.hashCode()) {
                throw new ObsoleteDataEntity(
                        "Attempt to save an entity with different version from current",
                        dbVersion.setVersion(dbVersion.hashCode())
                );
            }
        }

        operation.setDate(LocalDateTime.now());
        operation.setUser(operationService.getAuthenticatedUser());
        Client cliSaved = clientRepository.save(client);
        clientRepository.removeOrphans();
        operation.setDataAfter(operationService.stringfyObject(cliSaved));
        operationService.save(operation);

        return cliSaved.setVersion(cliSaved.hashCode());
    }

    @Transactional
    public void removeById(Long id) {
        Client dbVersion = this.getClientById(id).orElseThrow(EntityNotFoundException::new);
        Operation operation = new Operation();
        operation.setOperation("delete client");
        operation.setDataBefore(operationService.stringfyObject(dbVersion));
        operation.setDate(LocalDateTime.now());
        operation.setUser(operationService.getAuthenticatedUser());
        clientRepository.deleteById(id);
        operationService.save(operation);
    }
}
