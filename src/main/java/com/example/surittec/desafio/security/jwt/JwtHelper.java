package com.example.surittec.desafio.security.jwt;

import com.example.surittec.desafio.security.service._UserDetails;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.MacProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Component
@Slf4j
public class JwtHelper {

    private byte[] jwtSecret;

    @Value("${surittec.desafio.jwt.expire-seconds}")
    private int jwtExpireSeconds;

    @Value("${surittec.desafio.jwt.secret:#{''}}")
    private String appSecret;


    public String generateJwt(Authentication authentication) {

        _UserDetails userDetails = (_UserDetails) authentication.getPrincipal();

        //System.out.println(TextCodec.BASE64.encode(jwtSecret));

        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(new Date().getTime() + jwtExpireSeconds * 1000L))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();


    }

    @Bean
    private void setJwtSecret() {
        if (this.appSecret.isEmpty()) {
            log.info("Using a generated JWT secret");
            this.jwtSecret = MacProvider.generateKey(SignatureAlgorithm.HS512).getEncoded();
        } else {
            log.info("Using provided JWT secret");
            this.jwtSecret = TextCodec.BASE64.decode(this.appSecret);
        }
    }

    public boolean validateJwtToken(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (Exception exception) {
            return false;
        }

    }

    public String getUserNameFromJwtToken(String jwt) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt).getBody().getSubject();
    }
}
