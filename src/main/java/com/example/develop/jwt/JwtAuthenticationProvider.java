package com.example.develop.jwt;

import com.example.develop.exception.NotFoundException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

//mã hoá thông tin người dùng thành chuỗi JWT
//Jwt có 3 thành phần header(), payload, signature
//thời gian jwt có hiệu lực, jwt_secret
//tạo ra jwt từ thông tin user gồm: ngày khởi tạo, ngày hết hạn
@Component
public class JwtAuthenticationProvider {
    @Value("${app.jwt-secret}")
    private String jwtSecret;
    @Value("${app-jwt-expiration-milliseconds}")
    private long jwtExpireDate;

    public String generateToken(Authentication authentication){
        String username = authentication.getName();
        Date date = new Date();
        Date  expireDate = new Date(date.getTime()+jwtExpireDate);
        String token = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expireDate)
                .signWith(key())
                .compact();
        return token;

    }
    private Key key(){
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }
    public String getUserName(String token){
        return Jwts.parserBuilder().setSigningKey(key()).build().parseClaimsJws(token).getBody().getSubject();
    }
    public boolean validateToken(String authToken){
        try{
            Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
            return true;
        }catch (MalformedJwtException e){
            throw new NotFoundException("Invalid Jwt Token");
        }catch (ExpiredJwtException e){
            throw new NotFoundException("token is expired");
        }catch(UnsupportedJwtException e){
            throw new NotFoundException("token is unsupported");
        }catch (IllegalArgumentException e) {
            throw new NotFoundException("JWT claims string is empty");
        }
    }
}
