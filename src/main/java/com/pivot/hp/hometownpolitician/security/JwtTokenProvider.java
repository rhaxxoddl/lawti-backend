package com.pivot.hp.hometownpolitician.security;

import com.pivot.hp.hometownpolitician.entity.UserRole;
import com.pivot.hp.hometownpolitician.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtTokenProperty jwtTokenProperty;

    private final CustomUserDetails customUserDetails;

    private String encodedSecretKey;

    @PostConstruct
    protected void init() {
        encodedSecretKey = Base64.getEncoder().encodeToString(jwtTokenProperty.getSecretKey().getBytes());
    }

    public String createToken(String identifier, UserRole userRole) {
        Claims claims = Jwts.claims().setSubject(identifier);
        if (userRole != null) {
            claims.put("auth", userRole);
        }
        Date now = new Date();
        Date validity = new Date(now.getTime() + jwtTokenProperty.getExpireLength());
        return Jwts.builder()
                .setClaims(claims)
                .setIssuer(jwtTokenProperty.getIssuer())
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, encodedSecretKey)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(getIdentifier(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getIdentifier(String token) {
        return Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(encodedSecretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CustomException("Expired or invalid JWT token", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
