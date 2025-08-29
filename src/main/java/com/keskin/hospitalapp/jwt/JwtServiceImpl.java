package com.keskin.hospitalapp.jwt;

import com.keskin.hospitalapp.config.JwtConfig;
import com.keskin.hospitalapp.entities.AppUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class JwtServiceImpl implements IJwtService {

    private final JwtConfig jwtConfig;

    @Override
    public Jwt generateAccessToken(AppUser user) {

        return generateToken(user, jwtConfig.getAccessTokenExpiration());
    }

    @Override
    public Jwt generateRefreshToken(AppUser user) {

        return generateToken(user, jwtConfig.getRefreshTokenExpiration());
    }

    private Jwt generateToken(AppUser user, long tokenExpiration) {
        var claims = Jwts.claims()
                .subject(user.getId().toString())
                .add("email", user.getEmail())
                .add("name", user.getName())
                .add("role", user.getRole())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + 1000 * tokenExpiration))
                .build();

        return new Jwt(claims, jwtConfig.getSecretKey());
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(jwtConfig.getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    @Override
    public Jwt parseToken(String token) {
        try {
            var claims = getClaims(token);
            return new Jwt(claims, jwtConfig.getSecretKey());
        }catch (JwtException e){
            return null;
        }
    }

}
