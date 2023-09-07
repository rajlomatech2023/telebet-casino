package com.telebet.telebetauthservice.entities;

import lombok.Data;

@Data
public class AuthResponse {

	private String accessToken;
	private String message;
	private String status;
	private String responseCode;
	
	public AuthResponse() {
	}
	
	public AuthResponse(String message, String status, String token, String responseCode) {
		this.message = message;
		this.status = status;
		this.accessToken = token;
		this.responseCode = responseCode;
	}
}
