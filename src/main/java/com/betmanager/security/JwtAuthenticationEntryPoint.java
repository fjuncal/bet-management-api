package com.betmanager.security;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


//Essa classe é usada na configuração de segurança para definir o comportamento padrão quando ocorre um erro de autenticação:
@Component
public class JwtAuthenticationEntryPoint  implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        // Responde com erro 401 quando o usuário não está autenticado
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized: Access is denied");
    }
}
