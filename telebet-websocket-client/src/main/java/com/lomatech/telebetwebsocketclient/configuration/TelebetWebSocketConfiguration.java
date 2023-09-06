package com.lomatech.telebetwebsocketclient.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lomatech.telebetwebsocketclient.client.TelebetWebSocketClient;
import com.lomatech.telebetwebsocketclient.service.MessageHandlerServiceImpl;

@Configuration
public class TelebetWebSocketConfiguration {
	
	@Autowired
	private MessageHandlerServiceImpl serviceImpl;
	
	@Bean
	public TelebetWebSocketClient telebetWebSocketConfig() {
		//openWebSocket 
		final TelebetWebSocketClient clientEndPoint = new TelebetWebSocketClient();
		clientEndPoint.addMessageHandler(serviceImpl);
		
		// send message to websocket
        clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

        // wait 5 seconds for messages from websocket
        try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
        
        return clientEndPoint;
	}

}
