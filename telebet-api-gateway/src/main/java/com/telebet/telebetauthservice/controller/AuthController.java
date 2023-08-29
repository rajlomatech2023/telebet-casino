package com.telebet.telebetauthservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.telebet.telebetauthservice.entities.AuthRequest;
import com.telebet.telebetauthservice.entities.UserVO;
import com.telebet.telebetauthservice.service.AuthService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

	@Autowired
	private AuthService authService;
	
	@PostMapping(value = "/register")
	public ResponseEntity register(@RequestBody UserVO request){
		return ResponseEntity.ok(authService.register(request));
	}
	
}
