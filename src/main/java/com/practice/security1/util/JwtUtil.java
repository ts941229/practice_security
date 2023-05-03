package com.practice.security1.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
		
		boolean isExpired = false;
		
		Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
		
		
		try {
			Claims claims = Jwts.parserBuilder()
										.setSigningKey(key)
										.build()
										.parseClaimsJws(token)
										.getBody();
			
			Date expiration = claims.getExpiration();
			
			if(expiration.before(new Date())) {
				isExpired = true;
				throw new ExpiredJwtException(null, null, "만료된 토큰입니다.");
			}
		} catch (ExpiredJwtException e) {
			isExpired = true;
		}
		
		return isExpired;
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
