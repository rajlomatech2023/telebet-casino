package com.telebet.telebetauthservice.service;

import java.security.Key;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtUtil {

	@Value("${jwt.token}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private String expiration;
	
	private Key key;
	
	public JwtUtil() {
		this.key = Keys.hmacShaKeyFor(secret.getBytes());
	}
	
	public Claims getClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJwt(token).getBody();
	}
	
	public Date getExpirationDate(String token) {
		return getClaims(token).getExpiration();
	}
	
	public String generate(String userId, String role, String tokenType) {
		Map<String, String> claims = Map.of("id", userId, "role", role);
		long expMillis = "ACCESS".equalsIgnoreCase(tokenType)
				? Long.parseLong(expiration) * 1000 : Long.parseLong(expiration) * 1000 * 5;
		
		final Date now = new Date();
		final Date expDate = new Date(now.getTime() * expMillis);
		
		return Jwts.builder()
				.setClaims(claims)
				.setSubject(claims.get("id"))
				.setIssuedAt(now)
				.setExpiration(expDate)
				.signWith(key)
				.compact();
				
	}
	
	private boolean isExpired(String token) {
		return getExpirationDate(token).before(new Date());
	}
}
