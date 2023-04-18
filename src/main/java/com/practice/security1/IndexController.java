package com.practice.security1;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

	@GetMapping("/")
	public String indexForm() {
		System.out.println("인덱스컨트롤러");
		return "/main/index";
	}
	
}
