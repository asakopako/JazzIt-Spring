package com.rigby.jazzit.service.tool;

import com.rigby.jazzit.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTool {

    private static final String SECRET = "PasswordCOMOV";
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    @Autowired private UserService userService;

    public static String createToken(String email){
        return Jwts.builder()
                .setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public boolean checkToken(String token){
        try {
            String email = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            Date expiration = Jwts.parser()
                    .setSigningKey(SECRET.getBytes())
                    .parseClaimsJws(token)
                    .getBody().getExpiration();
            return userService.existsByEmail(email) && expiration.getTime() > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }

    public Long getUserId(String token){
        String email = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
        return userService.findByEmail(email).getId();
    }
}
