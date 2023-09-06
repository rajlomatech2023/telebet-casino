package com.lomatech.telebetwebsocketclient.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageHandlerServiceImpl implements MessageHandler {

	@Autowired
	private TelebetKafkaProducerServiceImpl telebetProducerService;
	
	@Override
	public void handleMessage(String message) {
		//System.out.println("from handleMessage(): "+message);
		telebetProducerService.sendCardDetails(message);
	}

}
