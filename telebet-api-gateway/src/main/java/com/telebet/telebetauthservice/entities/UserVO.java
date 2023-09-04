package com.telebet.telebetauthservice.entities;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

	private Long userId;
	private String email;
	
	//@NotNull(message = "Username must not be empty")
	//@NotBlank(message = "Username must not be empty")
	//@NotEmpty(message = "Username must not be empty")
	private String userName;
	
	//@NotNull(message = "Password must not be empty")
	//@NotBlank(message = "Password must not be empty")
	//@NotEmpty(message = "Password must not be empty")
	private String password;
	private String role;
}
