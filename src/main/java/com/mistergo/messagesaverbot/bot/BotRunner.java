package com.mistergo.messagesaverbot.bot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotRunner {
    @Autowired
    private BotHandler botHandler;

    @Autowired
    public void runBot() {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        try {
            telegramBotsApi.registerBot(botHandler);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
