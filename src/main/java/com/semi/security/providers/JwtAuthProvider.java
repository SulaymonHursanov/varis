package com.semi.security.providers;

import com.semi.security.authentication.JwtAuthentication;
import com.semi.security.details.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

/**
 * Date 22.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Component
public class JwtAuthProvider implements AuthenticationProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthentication tokenAuthentication = (JwtAuthentication) authentication;

        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(jwtSecret)
                    .parseClaimsJws(tokenAuthentication.getName())
                    .getBody();
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid token");
        }

        UserDetails userDetails = new UserDetailsImpl(
                Long.parseLong(body.get("sub").toString()),
                body.get("role").toString(),
                body.get("username").toString()
        );
        tokenAuthentication.setUserDetails(userDetails);
        tokenAuthentication.setAuthenticated(true);
        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
