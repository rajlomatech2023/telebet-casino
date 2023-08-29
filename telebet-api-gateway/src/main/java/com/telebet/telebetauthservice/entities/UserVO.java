package com.telebet.telebetauthservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

	private Long userId;
	private String email;
	private String userName;
	private String password;
	private String role;
}
