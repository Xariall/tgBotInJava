package com.example.tgbotinjava.service;

import com.example.tgbotinjava.config.BotConfig;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    startCommandReceived(chatId,update.getMessage().getChat().getFirstName());
            }

        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = "Привет! " + name + ", как дела?";

        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String texToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(texToSend);

        try{
            execute(message);
        }
        catch (TelegramApiException e){

        }

    }

   @Override
   public String getBotToken(){ return config.getToken();}

    final BotConfig config;

    public TelegramBot(BotConfig config){
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

}
