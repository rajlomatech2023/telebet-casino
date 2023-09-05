package com.telebet.telegramservice.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;



@Component
public class CasinoTelegramBot extends TelegramLongPollingBot {
	
	Logger logger = LoggerFactory.getLogger(CasinoTelegramBot.class);
	
	@Value("${telebot.username}")
	private String botUserName;
	
	@Autowired
	private ResultsFetchingService service;
	
	public CasinoTelegramBot(@Value("${telebot.token}") String botTokens) {
		super(botTokens);
	}
	
	
	
    @Override
    public void onUpdateReceived(Update update) {
        
    	logger.info("botUserName: {}", botUserName);
    	String command = update.getMessage().getText();

    	SendMessage responseMessage = new SendMessage();
    	
        if(command.equals("/placebet")){
            //String message = "hi run command received...";
        	String message = service.sendHelloText();
            
        	responseMessage.setChatId(update.getMessage().getChatId().toString());
        	responseMessage.setText(message);
          
            try{
                execute(responseMessage);
                
            }catch (TelegramApiException E){
                E.printStackTrace();
            }
        } else if(command.equals("/startbet1")) {
        	
        	responseMessage.setChatId(update.getMessage().getChatId().toString());
        	responseMessage.setText("buttons");
        	// Create ReplyKeyboardMarkup object
            ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
            // Create the keyboard (list of keyboard rows)
            List<KeyboardRow> keyboard = new ArrayList<>();
            // Create a keyboard row
            KeyboardRow row = new KeyboardRow();
            // Set each button, you can also use KeyboardButton objects if you need something else than text
            row.add("Row 1 Button 1");
            row.add("Row 1 Button 2");
            row.add("Row 1 Button 3");
            // Add the first row to the keyboard
            keyboard.add(row);
            // Create another keyboard row
            row = new KeyboardRow();
            // Set each button for the second line
            row.add("Row 2 Button 1");
            row.add("Row 2 Button 2");
            row.add("Row 2 Button 3");
            // Add the second row to the keyboard
            keyboard.add(row);
            // Set the keyboard to the markup
            keyboardMarkup.setKeyboard(keyboard);
            // Add it to the message
            responseMessage.setReplyMarkup(keyboardMarkup);
            try {
                execute(responseMessage); // Sending our message object to user
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        } else if(command.equals("/startbet")) {
        	StringBuilder sbf = new StringBuilder();
        	responseMessage.setChatId(update.getMessage().getChatId().toString());
        	sbf.append("Issue No. \n");
        	sbf.append("Start Betting \n");
        	sbf.append("Issue No. \n");
        	sbf.append("-------------------------------- \n");
			sbf.append("**Betting Instruction** \n");
			sbf.append("Bet Format 'item+space+amount' or \n");
			sbf.append(" 'item+slash+amount' \n ");
			sbf.append("Example 'leisure 1 level 100' or 'x1/P200' \n ");
			sbf.append("or 'idle 1/flip 100' or 'x1 F100' or \n");
			sbf.append("'idle 1 super 100' or 'X1/C200' \n");
			sbf.append("(X is not case-sensitive) bet format error is none \n");
			sbf.append("can't complete the bet \n");
			sbf.append("multiple bets are equivalent to stacked bets/raises \n");
			sbf.append("cancellation means cancellation of all bets \n");
			sbf.append("Example undo or cx \n");
			sbf.append("-------------------------------- \n");
			sbf.append("Minimum Red: 20$ \n");
			sbf.append("Super limit red: 2000$ \n");
			sbf.append("Double limit bonus: 5000$ \n");
			sbf.append("Double red limit: 10000$ \n");
			sbf.append("Players make mistakes during card deal authorities \n");
			
			
        	responseMessage.setText(sbf.toString());
        			
        	 InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
             InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
             inlineKeyboardButton1.setText("Check Balance");
             inlineKeyboardButton1.setCallbackData("Button Check Balance has been pressed");
             
             InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
             inlineKeyboardButton2.setText("Live Video");
             inlineKeyboardButton2.setCallbackData("Button Live Video has been pressed");
             
             List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<>();
             keyboardButtonsRow1.add(inlineKeyboardButton1);
             keyboardButtonsRow1.add(inlineKeyboardButton2);
             
             InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
             inlineKeyboardButton3.setText("Contact Customer Service");
             inlineKeyboardButton3.setCallbackData("Button Contact Customer Service has been pressed");
             
             InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();
             inlineKeyboardButton4.setText("Niuniu Game");
             inlineKeyboardButton4.setCallbackData("Button Niuniu Game has been pressed");
             
             List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<>();
             keyboardButtonsRow2.add(inlineKeyboardButton3);
             keyboardButtonsRow2.add(inlineKeyboardButton4);
             
             InlineKeyboardButton inlineKeyboardButton5 = new InlineKeyboardButton();
             inlineKeyboardButton5.setText("Contact Finance");
             inlineKeyboardButton5.setCallbackData("Button Contact Finance has been pressed");
             
             InlineKeyboardButton inlineKeyboardButton6 = new InlineKeyboardButton();
             inlineKeyboardButton6.setText("Go to upper and lower groups");
             inlineKeyboardButton6.setCallbackData("Button Go to upper and lower groups has been pressed");
             
             List<InlineKeyboardButton> keyboardButtonsRow3 = new ArrayList<>();
             keyboardButtonsRow3.add(inlineKeyboardButton5);
             keyboardButtonsRow3.add(inlineKeyboardButton6);
             
             List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
             rowList.add(keyboardButtonsRow1);
             rowList.add(keyboardButtonsRow2);
             rowList.add(keyboardButtonsRow3);
             inlineKeyboardMarkup.setKeyboard(rowList);
             
        	 responseMessage.setReplyMarkup(inlineKeyboardMarkup);  	
        	
        	 try {
                 execute(responseMessage); // Sending our message object to user
             } catch (TelegramApiException e) {
                 e.printStackTrace();
             }
        }
        
    }

    @Override
    public String getBotUsername() {
        return botUserName;
    }
}
