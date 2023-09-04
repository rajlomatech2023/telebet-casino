package com.telebet.userservice.entities;

import lombok.Data;

@Data
public class AuthResponse {

	private String message;
	private String status;
	
	public AuthResponse() {
	}
	
	public AuthResponse(String message, String status) {
		this.message = message;
		this.status = status;
	}
	
}
