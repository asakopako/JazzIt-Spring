package com.rigby.jazzit.service.tool;

import com.rigby.jazzit.config.exception.ForbiddenException;
import com.rigby.jazzit.config.exception.NotFoundException;
import com.rigby.jazzit.repository.UserRepository;
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

    @Autowired private UserService userService; // no sé si esto puede ser static -> no se puede


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
//        System.out.println("CheckToken: " + email + " - " + expiration);
            return userService.existsByEmail(email) && expiration.getTime() > System.currentTimeMillis();
        } catch (Exception e) {
            return false;
        }
    }
}
