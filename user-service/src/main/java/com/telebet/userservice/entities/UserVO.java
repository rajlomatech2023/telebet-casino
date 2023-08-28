package com.telebet.userservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserVO {

	private String id;
	private String email;
	private String name;
	private String password;
	private String role;
}
