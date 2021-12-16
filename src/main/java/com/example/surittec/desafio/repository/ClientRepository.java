package com.example.surittec.desafio.repository;

import com.example.surittec.desafio.domain.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByid(Long id);

    Page<Client> findAllByOrderByNameAsc(Pageable pageable);

    @Query(value = "DELETE ph " +
            "FROM phone ph " +
            "left JOIN client_phone cliph " +
            "on ph.id = cliph.phones_id " +
            "LEFT JOIN client cli " +
            "on ph.id = cli.main_phone " +
            "WHERE cliph.phones_id IS NULL and cli.main_phone IS NULL", nativeQuery = true)
    void removeOrphans();

}
