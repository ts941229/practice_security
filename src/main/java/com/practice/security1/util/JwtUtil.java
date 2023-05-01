package com.practice.security1.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtUtil {
	
	// token claim에서 memberName 꺼내기
	public static String getMemberName(String token, String secretKey) {
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody().get("memberName", String.class);
	}
	
	
	// token 기간 만료 여부
	public static boolean isExpired(String token, String secretKey) {
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.parserBuilder()
						.setSigningKey(key)
						.build()
						.parseClaimsJws(token)
						.getBody()
						.getExpiration()
						.before(new Date(System.currentTimeMillis()));
	}
	
	public static String createJwt(String memberName, String secretKey, Long expiredMs) {
		Claims claims = Jwts.claims();
		claims.put("memberName", memberName);
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		return Jwts.builder()
					.setClaims(claims)
					.setIssuedAt(new Date(System.currentTimeMillis()))
					.setExpiration(new Date(System.currentTimeMillis() + expiredMs))
					.signWith(key, SignatureAlgorithm.HS256)
					.compact();
	}
	
}
