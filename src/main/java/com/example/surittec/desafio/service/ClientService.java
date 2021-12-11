package com.example.surittec.desafio.service;

import com.example.surittec.desafio.domain.Client;
import com.example.surittec.desafio.domain.Phone;
import com.example.surittec.desafio.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    ClientRepository clientRepository;


    public Optional<Client> getClientById(Long id) {
        return clientRepository.findByid(id);
    }

    public List<Client> getAll(Pageable pageable) {
        return clientRepository.findAllByOrderByNameAsc(pageable);
    }

    @Transactional
    public Client save(Client client) {
        if (client.getEmails() != null) {
            client.getEmails().remove(client.getEmail());
        }


        final Phone[] phone = {null};
        client.getPhones().forEach(p -> {
            System.out.println(p.equals(client.getPhone()));
            System.out.println(p.hashCode());
            System.out.println(client.getPhone().hashCode());
            phone[0] = p;
        });


        client.getPhone().setClient(client);
        if (client.getPhones() != null) {
            client.getPhones().forEach(f -> f.setClient(client));
            client.getPhones().remove(client.getPhone());
        }

        return clientRepository.save(client);
    }
}
