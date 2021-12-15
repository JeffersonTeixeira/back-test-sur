package com.example.surittec.desafio.repository;

import com.example.surittec.desafio.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByid(Long id);

    Page<Client> findAllByOrderByNameAsc(Pageable pageable);


}
