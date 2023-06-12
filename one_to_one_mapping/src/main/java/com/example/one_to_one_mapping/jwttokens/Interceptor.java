package com.example.one_to_one_mapping.jwttokens;

import com.example.one_to_one_mapping.bglobal_exception.NoSuchNoTFieldException;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class Interceptor implements HandlerInterceptor {
    @Autowired
    private GenerateTokens generateToken;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String jwts = null;
        String token = request.getHeader("Authorization");

        if (token != null && token.startsWith("Bearer")) {
            jwts = token.substring(7, token.length());
        }
        if (!(request.getRequestURI().contains("/admin/api/login") || request.getRequestURI().contains("/admin/api/add"))) {

            try {
                Claims  claims = generateToken.verifyToken(jwts);

                if (claims.getIssuer().equals("Admin")) {
                    if (request.getRequestURI().contains("/admin/api")) {
                        claims = generateToken.verifyToken(jwts);
                    }

                } else if (claims.getIssuer().equals("User")) {
                    if (request.getRequestURI().contains("/users/api")) {
                          claims = generateToken.verifyToken(jwts);
                    }
                }
            } catch (NoSuchNoTFieldException e) {
                throw new NoSuchNoTFieldException();
            }
        }
        return HandlerInterceptor.super.preHandle(request, response, handler);
    }
}
