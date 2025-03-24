package com.todo.demo.service;

import com.todo.demo.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private Logger logger = LoggerFactory.getLogger(JwtService.class);

    private final String SECRET_KEY="be347d644eddf843fd6cc27298e1214326db69cfb7669601365434e3a24233ca";


    public String extractEmail(String token){
        logger.info("Extracting email from token");
        return extractClaim(token, Claims::getSubject);
    }


    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        logger.info("Extracting claims from token");
        Claims claims=extractALLClaims(token);
        return resolver.apply(claims);
    }


    public boolean isValid(String token, UserDetails user){
        logger.info("Validating token for user: {}", ((User) user).getEmail());
        String email=extractEmail(token);
        boolean isValid = email.equals(((User) user).getEmail()) && !isTokenExpired(token);
        logger.info("Token validity for user {}: {}", user.getUsername(), isValid);
        return (isValid);
    }

    private boolean isTokenExpired(String token){
        boolean expired = extractExpiration(token).before(new Date());
        logger.info("Is token expired? {}", expired);
        return expired;
    }

    private Date extractExpiration(String token) {
        logger.info("Extracting expiration from token");
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractALLClaims(String token){
        logger.info("Extracting all claims from token");
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    public String generateToken(User user){
        logger.info("Generating token for user: {}", user.getEmail());
        String token= Jwts.builder()
                .subject(user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+1*60*60*1000))
                .signWith(getSigninKey())
                .compact();

        logger.info("Generated token: {}", token);

        return token;
    }

    private SecretKey getSigninKey(){

        logger.info("Getting signing key");
        byte[] keyBytes= Decoders.BASE64URL.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }
}
