package com.keskin.hospitalapp.jwt;

import com.keskin.hospitalapp.entities.AppUser;
import jakarta.servlet.http.HttpServletRequest;

public interface IJwtService {

    Jwt generateAccessToken(AppUser user);

    Jwt generateRefreshToken(AppUser user);

    Jwt parseToken(String token);

}
