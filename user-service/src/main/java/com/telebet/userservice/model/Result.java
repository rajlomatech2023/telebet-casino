package com.telebet.userservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result<T> {
	
	private String type;
	private String responseCode;
	private String responseMessage;

	
	public static <T> Result<T> succeed(String type, String responseCode, String responseMessage ){
		return new Result(type, responseCode, responseMessage);
	}

}
