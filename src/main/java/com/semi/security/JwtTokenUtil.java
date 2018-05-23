package com.semi.security;

import com.semi.models.User;
import com.semi.security.details.UserDetailsImpl;
import com.semi.security.role.Role;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Date 23.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Component
public class JwtTokenUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    private Clock clock = DefaultClock.INSTANCE;

    public User getUserFromToken(String token){
        Claims body;
        try {
            body = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (MalformedJwtException | SignatureException e) {
            e.printStackTrace();
            throw new AuthenticationServiceException("Invalid token");
        }

        return User.builder()
                .id(Long.parseLong(body.get("sub").toString()))
                .username(body.get("username").toString())
                .role(Role.valueOf(body.get("role").toString()))
                .build();
    }

    public Date getIssuedAtDateFromToken(String token){
        return getClaimFromToken(token, Claims::getIssuedAt);
    }

    public Date getExpirationDateFromToken(String token){
        return getClaimFromToken(token, Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(clock.now());
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset){
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(User user){
        final Date createdDate = clock.now();
        final Date expirationDate = calculateExpirationDate(createdDate);
        return Jwts.builder()
                    .claim("username", user.getUsername())
                    .claim("role", user.getRole().toString())
                    .setSubject(user.getId().toString())
                    .setIssuedAt(createdDate)
                    .setExpiration(expirationDate)
                    .signWith(SignatureAlgorithm.HS512, secret)
                    .compact();
    }

    public<T> T getClaimFromToken(String token, Function<Claims, T> claimResolver){
        Claims claims = getAllClaimsFromToken(token);
        return claimResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();
    }

    private Date calculateExpirationDate(Date createdDate) {
        return new Date(createdDate.getTime() + expiration * 1000);
    }
}
