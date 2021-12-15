package com.example.surittec.desafio.repository;

import com.example.surittec.desafio.domain.Operation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationRepository extends JpaRepository<Operation, Long> {
}
