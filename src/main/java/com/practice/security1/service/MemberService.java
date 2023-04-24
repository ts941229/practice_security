package com.practice.security1.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.practice.security1.util.JwtUtil;

@Service
public class MemberService {
	
	@Value("${jwt.secret}")
	private String secretKey;
	
	private Long expiredMs = 1000 * 60 * 60l;
	
	public String login(String userName, String password) {
		return JwtUtil.createJwt(userName, secretKey, expiredMs);
	}
	
}
