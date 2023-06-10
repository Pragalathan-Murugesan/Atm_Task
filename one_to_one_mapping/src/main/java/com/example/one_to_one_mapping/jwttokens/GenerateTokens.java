package com.example.one_to_one_mapping.jwttokens;

import com.example.one_to_one_mapping.bglobal_exception.IllegalAuthException;
import com.example.one_to_one_mapping.dto.AdminDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class GenerateTokens {
    private  static  final String  privateKey= "This Private Key is Admin";
    Long takenTime = System.currentTimeMillis();
    Long expiredTime =takenTime * 1000L;
    Date takenAt = new Date(takenTime);
    Date expiredAt = new Date(expiredTime);
    public String generateTokens(AdminDto adminDTO){
        Claims claims = Jwts.claims()
                .setExpiration(expiredAt)
                .setIssuedAt(takenAt)
                .setIssuer(adminDTO.getRole());
//           claims.put("emailID",adminDTO.getEmailID());
//           claims.put("password",adminDTO.getPassword());
//           claims.put("role",adminDTO.getRole());
        return Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS384,privateKey).compact();

    }
    public Claims verifyToken(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(privateKey).parseClaimsJws(authorization).getBody();
            return claims;
        }catch (IllegalAuthException e){
            throw  new IllegalAuthException();
        }
}}
