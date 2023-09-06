package com.lomatech.telebetwebsocketclient.client;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EndpointConfig;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.lomatech.telebetwebsocketclient.service.MessageHandler;

@Component
@ClientEndpoint
public class TelebetWebSocketClient {
	
	//@Value("${telebet.ws.url}")
	//private String URI;
	private String URI = "ws://room.fxdd6678.cc/ws/room/1-101-929-13/b8876dd1-738d-4f70-9230-ab295f4fab9d";
	
	private Session session;
	
	@Autowired
	private MessageHandler messageHandler;
	
	public TelebetWebSocketClient() {
		
		try {
			WebSocketContainer container = ContainerProvider.getWebSocketContainer();
			container.connectToServer(this, new URI(URI));
		} catch (DeploymentException | IOException | URISyntaxException e) {
			e.printStackTrace();
		}  
	}
	
	@OnOpen
	public void onOpen(Session session, EndpointConfig endpointConfig) {
		//System.out.println("hi in onOpen() method...");
		this.session = session;
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		//System.out.println("in onClose() method..");
		this.session = null;
	}
	
	@OnMessage
	public void onMessage(String message) {
		//System.out.println("hi in onMessage() method...");
		System.out.println("ws url: "+URI);
		if(this.messageHandler != null) {
			this.messageHandler.handleMessage(message);
		}
	}
	
	 @OnMessage
    public void onMessage(ByteBuffer bytes) {
        System.out.println("Handle byte buffer");
    }
	
	public void addMessageHandler(MessageHandler messageHandler) {
		this.messageHandler = messageHandler;
	}
	
	public void sendMessage(String message) {
		try {
			this.session.getAsyncRemote().sendText(message);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
