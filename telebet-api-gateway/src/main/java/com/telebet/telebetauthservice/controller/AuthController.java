package com.telebet.telebetauthservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.telebetauthservice.entities.AuthRequest;
import com.telebet.telebetauthservice.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

	private final AuthService authService;
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody AuthRequest request){
		return ResponseEntity.ok(authService.register(request));
	}
	
}
