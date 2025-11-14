package com.franjazul.api.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    // Generar clave secreta
    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Generar token JWT
    public String generateToken(String idUsuario, String cargo, Integer idPerfil, String nombrePerfil) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("cargo", cargo);
        claims.put("idPerfil", idPerfil);
        claims.put("nombrePerfil", nombrePerfil);

        return Jwts.builder()
                .claims(claims)
                .subject(idUsuario)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey())
                .compact();
    }

    // Extraer claims del token
    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    // Extraer ID de usuario del token
    public String extractIdUsuario(String token) {
        return extractAllClaims(token).getSubject();
    }

    // Extraer cargo del token
    public String extractCargo(String token) {
        return extractAllClaims(token).get("cargo", String.class);
    }

    // Extraer ID de perfil del token
    public Integer extractIdPerfil(String token) {
        return extractAllClaims(token).get("idPerfil", Integer.class);
    }

    // Validar token
    public boolean validateToken(String token) {
        try {
            extractAllClaims(token);
            return !isTokenExpired(token);
        } catch (JwtException e) {
            return false;
        }
    }

    // Verificar si el token está expirado
    private boolean isTokenExpired(String token) {
        return extractAllClaims(token).getExpiration().before(new Date());
    }
}