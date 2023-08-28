package com.telebet.telebetauthservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthRequest {

	private String email;
	private String name;
	private String password;
}
