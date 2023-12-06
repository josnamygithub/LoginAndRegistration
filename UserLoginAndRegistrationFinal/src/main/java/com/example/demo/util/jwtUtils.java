package com.example.demo.util;

import io.jsonwebtoken.security.Keys;
import javax.crypto.SecretKey;
import java.util.Date;


import org.springframework.stereotype.Component;

import com.example.demo.bean.Userdetails;
import com.example.demo.common.AccessDeniedException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class jwtUtils {

   private static final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static String generateJwt(Userdetails userDetails) {
        Date issuedAt = new Date();
        int Id = userDetails.getId();

               Claims claims = Jwts.claims().setSubject(String.valueOf(Id));
        claims.setIssuedAt(issuedAt);

        
        Date expiration = new Date(issuedAt.getTime() + 3600000); // 1 hour in milliseconds

        claims.put("type", userDetails.getUser_type());
        claims.put("name", userDetails.getFirstname());
        claims.put("UserId", userDetails.getEmail());

        // Generate the JWT with a signature
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiration)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }


    public Claims verify(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parserBuilder().setSigningKey(secretKey).build().parseClaimsJws(authorization).getBody();
          System.out.println(claims.get("name"));//get payload
          return claims;
        
        }catch(Exception e) {
            	throw new AccessDeniedException("Access Denied");
            }
}
}



