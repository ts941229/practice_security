package com.practice.security1.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	// token 기간 만료 여부
	public static boolean isExpired(String token, String secretKey) {
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getExpiration()
						.before(new Date());
	}
	
	public static String createJwt(String userName, String secretKey, Long expiredMs) {
		Claims claims = Jwts.claims();
		claims.put("userName", userName);
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
					.signWith(key, SignatureAlgorithm.HS256)
					.compact();
	}
	
}
