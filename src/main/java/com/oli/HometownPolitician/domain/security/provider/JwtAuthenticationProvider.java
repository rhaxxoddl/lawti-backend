package com.oli.HometownPolitician.domain.security.provider;

import com.oli.HometownPolitician.domain.security.domain.JwtAuthenticationToken;
import com.oli.HometownPolitician.domain.security.exception.JwtAuthenticationException;
import com.oli.HometownPolitician.global.property.JwtAuthenticationProperty;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final JwtAuthenticationProperty property;

    private final UserDetailsService userDetailsService;

    private byte[] accessSecretBytes;

    @PostConstruct
    void init() {
        accessSecretBytes = property.getAccessSecret().getBytes();
    }

    private Collection<? extends GrantedAuthority> createGrantedAuthorities(Claims claims) {
        List<String> roles = (List) claims.get("roles");
        return roles
                .stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Claims claims;
        try {
            claims = Jwts
                    .parserBuilder()
                    .setSigningKey(accessSecretBytes)
                    .build()
                    .parseClaimsJws(((JwtAuthenticationToken) authentication).getJsonWebToken())
                    .getBody();
            userDetailsService.loadUserByUsername(claims.getSubject() + ":" + claims.get("roles").toString());
        } catch (UsernameNotFoundException usernameNotFoundException) {
            throw new JwtAuthenticationException("non existing user", usernameNotFoundException);
        } catch (SignatureException signatureException) {
            throw new JwtAuthenticationException("signature key is different", signatureException);
        } catch (ExpiredJwtException expiredJwtException) {
            throw new JwtAuthenticationException("expired token", expiredJwtException);
        } catch (MalformedJwtException malformedJwtException) {
            throw new JwtAuthenticationException("malformed token", malformedJwtException);
        } catch (IllegalArgumentException illegalArgumentException) {
            throw new JwtAuthenticationException("using illegal argument like null", illegalArgumentException);
        }
        return new JwtAuthenticationToken(claims.getSubject(), "", createGrantedAuthorities(claims));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
