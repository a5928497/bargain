package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {

	@GetMapping("/backend")
	public String toBackend() {
		return "backend/backend";
	}

	@PostMapping("login")
	public String login(User user) {
		System.out.println(user);
		return "/toregister";
	}
}
