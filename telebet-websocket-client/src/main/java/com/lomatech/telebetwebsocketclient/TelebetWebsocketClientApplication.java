package com.lomatech.telebetwebsocketclient;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.lomatech.telebetwebsocketclient.client.TelebetWebSocketClient;

@SpringBootApplication
public class TelebetWebsocketClientApplication {

	public static void main(String[] args) {
		//SpringApplication.run(TelebetWebsocketClientApplication.class, args);
		
		//openWebSocket 
		final TelebetWebSocketClient clientEndPoint = new TelebetWebSocketClient();
		
		//add listener
		clientEndPoint.addMessageHandler(new TelebetWebSocketClient.MessageHandler() {
			
			@Override
			public void handleMessage(String message) {
				System.out.println("from handleMessage(): "+message);
			}
		});
		
		// send message to websocket
        //clientEndPoint.sendMessage("{'event':'addChannel','channel':'ok_btccny_ticker'}");

        // wait 5 seconds for messages from websocket
        try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
