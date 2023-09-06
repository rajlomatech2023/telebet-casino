package com.lomatech.telebetwebsocketclient.service;

import org.springframework.stereotype.Component;

@Component
public interface TelebetKafkaProducerService {

	public String sendCardDetails(String message);
}
