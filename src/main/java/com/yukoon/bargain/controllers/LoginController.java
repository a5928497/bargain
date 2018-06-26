package com.yukoon.bargain.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/backend")
	public String toBackend() {
		return "backend/backend";
	}
}
