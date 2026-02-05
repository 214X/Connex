package com.burakkurucay.connex.exception.handler;

import com.burakkurucay.connex.dto.common.ApiResponse;
import com.burakkurucay.connex.exception.codes.ErrorCode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityExceptionHandlers
                implements AuthenticationEntryPoint, AccessDeniedHandler {

        private final ObjectMapper objectMapper;

        public SecurityExceptionHandlers() {
                this.objectMapper = new ObjectMapper();
                this.objectMapper.registerModule(new JavaTimeModule());
        }

        /**
         * 401 - No token or invalid token
         */
        @Override
        public void commence(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        org.springframework.security.core.AuthenticationException authException) throws IOException {

                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ApiResponse<Void> body = ApiResponse.error(
                                ErrorCode.AUTH_UNAUTHORIZED.name(),
                                "Authentication required",
                                List.of("Missing, invalid or expired token"));

                response.getWriter()
                                .write(objectMapper.writeValueAsString(body));
        }

        /**
         * 403 - No authentication
         */
        @Override
        public void handle(
                        HttpServletRequest request,
                        HttpServletResponse response,
                        AccessDeniedException accessDeniedException) throws IOException {

                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType(MediaType.APPLICATION_JSON_VALUE);

                ApiResponse<Void> body = ApiResponse.error(
                                ErrorCode.AUTH_FORBIDDEN.name(),
                                "Access denied",
                                List.of("You do not have permission to access this resource"));

                response.getWriter()
                                .write(objectMapper.writeValueAsString(body));
        }
}
