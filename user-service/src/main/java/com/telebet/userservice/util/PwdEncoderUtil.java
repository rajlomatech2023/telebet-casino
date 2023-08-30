package com.telebet.userservice.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PwdEncoderUtil {

	public static PasswordEncoder getDelegatingPasswordEncoder(String encodingId) {
		
		Map<String, PasswordEncoder> encoders = new HashMap<>();
		encoders.put("bcrypt", new BCryptPasswordEncoder());
		
		return encoders.get(encodingId);
	}
}
