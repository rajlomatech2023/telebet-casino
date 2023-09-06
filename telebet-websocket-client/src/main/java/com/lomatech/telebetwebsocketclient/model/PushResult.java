package com.lomatech.telebetwebsocketclient.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PushResult<T> implements Serializable {

	private T data;
	private Integer code;
	private String type;
	private String resp_msg;
}
