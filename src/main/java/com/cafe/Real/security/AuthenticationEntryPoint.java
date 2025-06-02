package com.cafe.Real.security;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class AuthenticationEntryPoint implements org.springframework.security.web.AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, 
                       HttpServletResponse response,
                       AuthenticationException authException) throws IOException {
        
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Não autorizado");
    }
}
