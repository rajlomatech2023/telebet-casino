package com.telebet.telebetauthservice.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.telebet.telebetauthservice.entities.UserVO;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

@Component
public class JwtUtil {

	//@Value("${jwt.secret}")
	private String secret="BvPHGM8C0ia4uOuxxqPD5DTbWC9F9TWvPStp3pb7ARo0oK2mJ3pd3YG4lxA9i8bj6OTbadwezxgeEByY";
	
	//@Value("${jwt.expiration}")
	private String expirationTime="86400";
	
	private Key key;
	
	@PostConstruct
	public void init() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public Claims getAllClaimsFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }
	
	public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }
	
	 public String generate(UserVO userVO, String type) {
	        Map<String, Object> claims = new HashMap<>();
	        claims.put("id", userVO.getUserId());
	        claims.put("role", userVO.getRole());
	        return doGenerateToken(claims, userVO.getEmail(), type);
	    }

	    private String doGenerateToken(Map<String, Object> claims, String username, String type) {
	        long expirationTimeLong;
	        if ("ACCESS".equals(type)) {
	            expirationTimeLong = Long.parseLong(expirationTime) * 1000;
	        } else {
	            expirationTimeLong = Long.parseLong(expirationTime) * 1000 * 5;
	        }
	        final Date createdDate = new Date();
	        final Date expirationDate = new Date(createdDate.getTime() + expirationTimeLong);

	        return Jwts.builder()
	                .setClaims(claims)
	                .setSubject(username)
	                .setIssuedAt(createdDate)
	                .setExpiration(expirationDate)
	                .signWith(key)
	                .compact();
	    }
	
	private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
	
	public Boolean validateToken(String token) {
        return !isTokenExpired(token);
    }
}
