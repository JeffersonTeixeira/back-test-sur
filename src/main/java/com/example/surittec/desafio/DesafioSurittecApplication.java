package com.example.surittec.desafio;

import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.reference.Role;
import com.example.surittec.desafio.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashSet;

@SpringBootApplication
public class DesafioSurittecApplication {

    @Autowired
    UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(DesafioSurittecApplication.class, args);
    }

    @Bean
    public void CreateUsers() {

        if (!userService.findByName("admin").isPresent()) {
            User userAdmin = new User("admin", "123456", new HashSet<>());
            userAdmin.getRoles().add(Role.ADMIN);
            userService.save(userAdmin);

            System.out.println("Created user: admin, password: 123456, Roles:[ADMIN]");

        }

        if (!userService.findByName("user").isPresent()) {
            User user = new User("user", "123456", new HashSet<>());
            user.getRoles().add(Role.USER);
            userService.save(user);
            System.out.println("Created user: user, password: 123456, Roles:[USER]");
        }


    }


}
