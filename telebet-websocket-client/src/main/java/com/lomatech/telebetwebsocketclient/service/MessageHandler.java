package com.lomatech.telebetwebsocketclient.service;

import org.springframework.stereotype.Component;

@Component
public interface MessageHandler {

    public void handleMessage(String message);
}