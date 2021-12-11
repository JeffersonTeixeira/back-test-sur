package com.example.surittec.desafio.service;

import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder encoder;

    public User save(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);

    }

    public Optional<User> findByName(String name) {
        return userRepository.findByName(name);
    }
}
