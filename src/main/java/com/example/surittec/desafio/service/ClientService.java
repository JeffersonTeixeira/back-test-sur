package com.example.surittec.desafio.service;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    public Optional<Client> getClientById(Long id) {
        return clientRepository.findByid(id);
    }

    public Page<Client> getAll(Pageable pageable) {
        return clientRepository.findAllByOrderByNameAsc(pageable);
    }

    //@Transactional
    public Client save(Client client) {
        if (client.getEmails() != null) {
            client.getEmails().remove(client.getEmail());
        }

        if (client.getPhones() != null) {
            client.getPhones().remove(client.getPhone());
        }

        return clientRepository.save(client);
    }

    public void removeById(Long id) {
        clientRepository.deleteById(id);
    }
}
