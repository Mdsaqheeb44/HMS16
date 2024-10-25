package com.hms16.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Service
public class JwtService {
@Value("${jwt.algorithm.kye}")
private String algorithmkye;

@Value("${jwt.issuer}")
private String issuer;

@Value("${jwt.expiryTime}")
private long expiryTime;

private Algorithm algorithm;

@PostConstruct
public void postconstruct() throws UnsupportedEncodingException {
   algorithm =  Algorithm.HMAC256(algorithmkye);
}
public String generateToken(String username){
    return JWT.create()
            .withClaim("name",username)
            .withExpiresAt(new Date(System.currentTimeMillis()+expiryTime))
            .withIssuer(issuer)
            .sign(algorithm);
}

public String getusername(String token){
    DecodedJWT decodedJWT = JWT.
            require(algorithm)
            .withIssuer(issuer)
            .build()
            .verify(token);
    return decodedJWT.getClaim("name").asString();
}
}
