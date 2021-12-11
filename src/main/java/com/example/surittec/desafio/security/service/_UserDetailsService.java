package com.example.surittec.desafio.security.service;

import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class _UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepository userRepository;


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User u = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário " + username + " não encontrado"));

       return _UserDetails.build(u);
        //return UserDetails


    }
}
