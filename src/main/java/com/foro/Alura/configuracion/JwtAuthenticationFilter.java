package com.foro.Alura.configuracion;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final SecretKeySpec jwtSecretKey;

    public JwtAuthenticationFilter(SecretKeySpec jwtSecretKey) {
        this.jwtSecretKey = jwtSecretKey;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");

        // Validar la cabecera Authorization
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7);

        try {
            // Validar y analizar el token JWT
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();
            List<SimpleGrantedAuthority> roles = extractRoles(claims);

            if (username != null) {
                // Crear el token de autenticación
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null, roles);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (Exception e) {
            // En caso de token inválido, devolver 401
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }

    private List<SimpleGrantedAuthority> extractRoles(Claims claims) {
        var rolesClaim = claims.get("roles", List.class);
        if (rolesClaim != null) {
            return rolesClaim.stream()
                    .map(role -> new SimpleGrantedAuthority(role.toString()))
                    .toList();
        }
        return Collections.emptyList();
    }
}
