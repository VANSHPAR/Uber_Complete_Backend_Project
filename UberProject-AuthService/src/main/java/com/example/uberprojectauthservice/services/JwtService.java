package com.example.uberprojectauthservice.services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService  implements CommandLineRunner{

    @Value("${jwt.expiry}")
    private int expiry;

    @Value("${jwt.secret}")
    private  String SECRET;

    //this method create token based on payload
    private String createToken(Map<String, Object> payload, String email) {
        Date  now = new Date();
        Date expiryDate = new Date(now.getTime() + expiry*1000L);

        return Jwts.builder()
                .claims(payload)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(expiryDate)
                .subject(email)
                .signWith(getSignKey())
                .compact();

    }
//
//    private <T> T extractPayload(String token, Function<Claims, T> resolverFunction) {
//        SecretKey key = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
//        final claims = Jwts.parser().decryptWith(key).build().parseClaimsJws(token).getPayload();
//    }

    private Claims getAllPayloads(String token) {
        return Jwts.parser()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllPayloads(token);
        return claimsResolver.apply(claims);
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }
    //This method checks if the token expiry was before the current timestamp or not.
    private Boolean isTokenExpired(String token) {
       return extractExpiration(token).before(new Date());
    }

    private Key getSignKey(){
        return Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
    }

    private Boolean validateToken(String token,String email) {
        final String userEmailFetchedFromToken=extractEmail(token);
        return (userEmailFetchedFromToken.equals(email)) && !isTokenExpired(token);
    }

    private String extractPhoneNumber(String token) {
       Claims claims = getAllPayloads(token);
       String ph=claims.get("phoneNumber", String.class);
       return ph;
    }

    @Override
    public void run(String... args) throws Exception {
        Map<String,Object> mp= new HashMap<>();
        mp.put("email","person@gmail.com");
        mp.put("phoneNumber","9798554345");

        String result=createToken(mp,"person@gmail.com");

        System.out.println("Generated token is: "+result);
       // System.out.println(extractPhoneNumber(result));
        System.out.println(validateToken(result,"person@gmail.com"));
    }
}
