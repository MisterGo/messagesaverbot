package com.mistergo.messagesaverbot.bot;

import com.mistergo.messagesaverbot.model.BotMessage;
import com.mistergo.messagesaverbot.repo.BotMessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@Component
public class BotHandler extends TelegramLongPollingBot {
    @Autowired
    private BotMessageRepo botMessageRepo;
    @Value("${telegram.bot.username}")
    private String botUserName;
    @Value("${telegram.bot.token}")
    private String botToken;

    /**
     * Обработка принятого сообщения
     * @param update принятое сообщение
     */
    public void onUpdateReceived(Update update) {
        Message recievedMessage = update.getMessage();
        if (update.hasMessage() && recievedMessage.hasText()) {
            SendMessage answerMessage = new SendMessage()
                    .setChatId(recievedMessage.getChatId().toString());

            Integer telegramUserId = recievedMessage.getFrom().getId();

            StringBuilder answerText = new StringBuilder();
            switch (recievedMessage.getText()) {
                case "/start": case "/help":
                    answerText.append("Привет. Я есть бот.\n")
                            .append("Набери любое сообщение, чтобы оно сохранилось.\n")
                            .append("Набери /my, чтобы посмотреть свои сообщения.\n")
                            .append("Набери /clean, чтобы очистить все свои сообщения.");
                    answerMessage.setText(answerText.toString());
                    break;
                case "/my":
                    if (botMessageRepo.countByTelegramId(telegramUserId) == 0) {
                        answerMessage.setText("Сообщений пока нет");
                    } else {
                        answerText.append("Вот что мне удалось найти: \n");

                        botMessageRepo.findByTelegramId(telegramUserId)
                                .forEach(t -> answerText.append(t.getText()).append("\n"));

                        answerMessage.setText(answerText.toString());
                    }
                    break;
                case "/clean":
                    botMessageRepo.deleteByTelegramId(telegramUserId);
                    answerMessage.setText("Сообщения почищены");
                    break;
                default:
                    BotMessage botMessage = new BotMessage(telegramUserId, recievedMessage.getText());
                    botMessageRepo.save(botMessage);
                    answerMessage.setText("Записано...");
                    break;
            }

            try {
                execute(answerMessage);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Получить имя бота
     * @return имя бота
     */
    public String getBotUsername() {
        return botUserName;
    }

    /**
     * Получить токен бота
     * @return токен
     */
    public String getBotToken() {
        return botToken;
    }
}
