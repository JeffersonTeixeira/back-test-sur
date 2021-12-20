package com.example.surittec.desafio.controller;

import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.payload.UserDTO;
import com.example.surittec.desafio.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody User user) {

        if (userService.findByName(user.getName()).isPresent()) {
            throw new RuntimeException("Username already registered");
        }

        return new ResponseEntity<>(
                modelMapper.map(userService.save(user), UserDTO.class),
                HttpStatus.CREATED
        );

    }


}
