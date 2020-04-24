package com.rigby.jazzit.security;

import com.rigby.jazzit.config.exception.ForbiddenException;
import com.rigby.jazzit.config.exception.UnauthorizedException;
import com.rigby.jazzit.service.tool.JwtTool;
import io.jsonwebtoken.Jwt;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Aspect
@Component
public class SecurityAspect {

    private static Pattern pattern = Pattern.compile("Bearer (?<token>[a-zA-Z0-9-._]+)");
    private static final String TOKEN_TAG = "token";
    private static final String AUTH_HEADER = "Authorization";

    @Autowired JwtTool jwtTool;


    // Antes de, que se ejecute cualquier metodo dentro de una clase con la anotacion RestController
    // Y que no tenga la anotación SecurityIgnore
    @Before("within(@org.springframework.web.bind.annotation.RestController *) && !@annotation(com.rigby.jazzit.security.SecurityIgnore)")
    public void hello(JoinPoint joinPoint) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String authorization = request.getHeader(AUTH_HEADER);
        if (authorization == null || authorization.trim().isEmpty()) {
            throw new UnauthorizedException("token null or empty");
        }

        Matcher matcher = pattern.matcher(authorization);
        if (!matcher.matches()) {
            throw new UnauthorizedException("token doesn't match");
        }

        String token = matcher.group(TOKEN_TAG);

        if (!jwtTool.checkToken(token)){
            throw new UnauthorizedException("token not valid");
        }
    }


}
