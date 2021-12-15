package com.example.surittec.desafio.controller;

import com.example.surittec.desafio.domain.User;
import com.example.surittec.desafio.security.jwt.JwtHelper;
import com.example.surittec.desafio.security.service._UserDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user/auth")
@CrossOrigin(origins = "*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping
    public ResponseEntity<AuthorizationResponse> auth(@RequestBody @Valid User user) {


        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getName(), user.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(auth);
        String tokenJwt = jwtHelper.generateJwt(auth);

        _UserDetails userDetails = (_UserDetails) auth.getPrincipal();

        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()
                );

        return ResponseEntity.ok(new AuthorizationResponse(
                tokenJwt, userDetails.getUsername(), roles)
        );

    }

    @Getter
    @Setter
    class AuthorizationResponse {
        final String type = "Bearer";
        String token;
        String user;
        List<String> roles;

        public AuthorizationResponse(String token, String user, List<String> roles) {
            this.token = token;
            this.user = user;
            this.roles = roles;
        }
    }

}
