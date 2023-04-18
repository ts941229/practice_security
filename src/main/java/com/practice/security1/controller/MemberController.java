package com.practice.security1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
public class MemberController {
	
	@PostMapping("/login")
	public ResponseEntity<String> login(){
		return ResponseEntity.ok().body("token");
	}
	
}
