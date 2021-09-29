package com.loginsystem.loginsystem.security;

import com.loginsystem.loginsystem.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {
    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", userDetails.getUsername());
        return Jwts
                .builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setExpiration(new Date(System.currentTimeMillis() + (100*60*60*10)))
                .signWith(SignatureAlgorithm.HS256, "secret").compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        String username = extractUserName(token);
        if (! userDetails.getUsername().equals(username)) {
            return false;
        }
        Date expire = extractClaim(token, Claims::getExpiration);
        return !expire.before(new Date());
    }

    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey("secret").parseClaimsJws(token).getBody();
    }
}
