package com.example.SpringSecurityDemoJWT.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@Service
public class JwtService {

    private SecretKey SECRET_KEY;

    public String generateToken(String username) throws NoSuchAlgorithmException {
        Map<String,Object> claims = new HashMap<>();

        return Jwts.builder()
                .claims(claims)
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1000*60*60))
                .signWith(getKey(),Jwts.SIG.HS256)
                .compact();
    }

    public SecretKey getKey() throws NoSuchAlgorithmException {

        SecretKey key = KeyGenerator.getInstance("HmacSHA256").generateKey();
        SECRET_KEY = Keys.hmacShaKeyFor(key.getEncoded());

        return SECRET_KEY;
    }
}
