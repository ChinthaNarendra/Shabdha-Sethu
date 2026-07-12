package com.shabdhasethu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shabdhasethu.dto.LoginRequest;
import com.shabdhasethu.dto.RegisterRequest;
import com.shabdhasethu.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	public String register(@RequestBody RegisterRequest request) {

	    return authService.register(request);
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginRequest request) {

		return authService.login(request.getEmail(), request.getPassword());

	}

	@GetMapping("/test")
	public String test() {
		return "JWT is working";
	}

}