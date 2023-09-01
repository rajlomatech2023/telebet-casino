package com.telebet.telegramservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


@Component
public class CasinoTelegramBot extends TelegramLongPollingBot {
	
	Logger logger = LoggerFactory.getLogger(CasinoTelegramBot.class);
	
	@Value("${telebot.username}")
	private String botUserName;
	
	//@Value("${telebot.token}") 
	//private static String botTokens="6402412801:AAG9r6sDBCL8p9eyoDp_BSw0molyyFSSrWM";
	
	@Autowired
	private ResultsFetchingService service;
	
	public CasinoTelegramBot(@Value("${telebot.token}") String botTokens) {
		super(botTokens);
	}
	
    @Override
    public void onUpdateReceived(Update update) {
        
    	logger.info("botUserName: {}", botUserName);
    	String command = update.getMessage().getText();

        if(command.equals("/placebet")){
            //String message = "hi run command received...";
        	String message = service.sendHelloText();
            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId().toString());
            response.setText(message);

            try{
                execute(response);
            }catch (TelegramApiException E){
                E.printStackTrace();
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }
}
