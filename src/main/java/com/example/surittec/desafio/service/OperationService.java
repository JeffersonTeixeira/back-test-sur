package com.example.surittec.desafio.service;

import com.example.surittec.desafio.domain.Operation;
import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.repository.OperationRepository;
import com.example.surittec.desafio.security.service._UserDetails;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class OperationService {
    @Autowired
    OperationRepository operationRepository;

    @Autowired
    ObjectMapper objectMapper;

    public String stringfyObject(Object obj) throws JsonProcessingException {
        return objectMapper.writeValueAsString(obj);
    }

    public Operation save(Operation operation) {
        return this.operationRepository.save(operation);
    }

    public User getAuthenticatedUser() {
        _UserDetails userDetails = (_UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User u = new User();
        u.setId(userDetails.getId());
        return u;
    }
}
