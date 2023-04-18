package com.practice.security1.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/review")
public class ReviewController {
	
	@PostMapping
	public ResponseEntity<String> WriteReview(){
		return ResponseEntity.ok().body("리뷰 등록이 완료되었습니다.");
		
	}
	
}
