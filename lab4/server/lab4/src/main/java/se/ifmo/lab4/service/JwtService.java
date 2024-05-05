package se.ifmo.lab4.service;


import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

    private static final String SECRET_KEY = "35nZtYU5TxZdQbYGqYFj0iSnr76CWLAw0irRpk0DjTeHloFFTdeTNuEAJQHHddIo";
    
     public String extractLogin(String token){
        return extractClaim(token, Claims::getSubject);
     }

     public <T> T extractClaim(String token, Function<Claims, T> claimsResolver){
      final Claims claims = extractClaims(token);
      return claimsResolver.apply(claims);
     }

     public String generateToken(UserDetails userDetails){
      return generateToken(new HashMap<>(), userDetails);
     }

     public String generateToken(Map <String, Object> extraClaims, UserDetails userDetails){
      return Jwts
            .builder()
            .claims().empty().add(extraClaims).and()
            .subject(userDetails.getUsername())
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + 1000 *  24 * 3600))
            .signWith((SecretKey) getSignInKey(),  Jwts.SIG.HS256)
            .compact();
     }

     private Claims extractClaims(String token){
        return Jwts.parser().setSigningKey((SecretKey) getSignInKey()).build().parseSignedClaims(token).getPayload();
     }

     private Key getSignInKey() {
        byte[] bytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(bytes);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
      String login = extractLogin(token);
      return (login.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
      return extractTokenExpiration(token).before(new Date());
    }

    private Date extractTokenExpiration(String token){
      return extractClaim(token, Claims::getExpiration);
    }
}
