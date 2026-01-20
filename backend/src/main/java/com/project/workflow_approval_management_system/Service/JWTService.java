package com.project.workflow_approval_management_system.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;


import org.springframework.stereotype.Service;

import com.project.workflow_approval_management_system.model.UserPrincipal;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;


@Service
public class JWTService {
	private SecretKey secretKey = Jwts.SIG.HS256.key().build();
	public String generateToken(String username) {
//		Map<String,Object> claim = new HashMap<>();
		return Jwts.builder()
				.claim("type", "ACCESS")
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+15*60*1000))
				.signWith(secretKey)
				.compact();
	}
	public String generateRefreshToken(String username) {
		Map<String, Object> claim = new HashMap<>();
		return Jwts.builder()
				.claims()
				.add(claim)
				.subject(username)
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis()+7L*24*60*60*1000))
				.and()
				.signWith(secretKey)
				.compact();
	}
	public String extractUsername(String token) {
		return extractClaim(token,Claims::getSubject);
	}
	private <T>T extractClaim(String token, Function<Claims,T> claimResolver) {
		final Claims claim = extractAllClaims(token);
		return claimResolver.apply(claim);
	}
	private Claims extractAllClaims(String token) {
		return Jwts.parser()
				.verifyWith(secretKey)
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public boolean validateToken(String token,UserPrincipal userPrincipal) {
		String username = extractUsername(token);
		return (!isTokenExpired(token) && username.equals(userPrincipal.getUsername()));
	}
	private boolean isTokenExpired(String token) {
		Date expiration = extractExpiration(token);
		return (expiration.before(new Date()));
	}
	private Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public boolean validateRefreshToken(String loginToken, UserPrincipal userPrincipal) {
		return extractUsername(loginToken).equals(userPrincipal.getUsername())
				&& !isTokenExpired(loginToken);
	}
	
}
