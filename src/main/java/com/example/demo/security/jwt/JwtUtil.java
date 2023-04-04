package com.example.demo.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtUtil {
    private static final String SECRET_KEY = "thisisanicelongsecretkey12345678"; // for now
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // the token will last for 10h
                .signWith(getSigningKey()).compact();
        // will return a jwt token for the user
    }
    private Key getSigningKey() {
        byte[] keyBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
//        Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
        Claims claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY.getBytes()).build().parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims); // uses claimsResolver to figure out what the claims are
    }
    public String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }
    public Date extractExpiration(String token){
        return extractClaims(token, Claims::getExpiration);
    }

    public Boolean validateToken(String token, UserDetails userDetails){
        String username = extractUsername(token);
        boolean isTokenExpired = extractExpiration(token).before(new Date()); // says if its before the current date
        return (username.equals(userDetails.getUsername()) && !isTokenExpired);
    }

}
