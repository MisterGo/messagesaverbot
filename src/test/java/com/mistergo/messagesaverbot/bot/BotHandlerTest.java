package com.mistergo.messagesaverbot.bot;

import com.mistergo.messagesaverbot.model.BotMessage;
import com.mistergo.messagesaverbot.repo.BotMessageRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.User;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class BotHandlerTest {
    @Spy
    @InjectMocks
    private BotHandler botHandler;

    @Value("${telegram.bot.token}")
    private String token;
    @Value("${telegram.bot.username}")
    private String botUserName;

    @Mock
    private BotMessageRepo botMessageRepo;
    @Mock
    private User userFrom;
    @Mock
    private Message receivedMessage;
    @Mock
    private Update update;

    @Before
    public void setUp() throws Exception {
        when(userFrom.getId()).thenReturn(1);
        when(receivedMessage.getChatId()).thenReturn(1L);
        when(receivedMessage.getFrom()).thenReturn(userFrom);

        when(receivedMessage.hasText()).thenReturn(Boolean.TRUE);

        when(update.getMessage()).thenReturn(receivedMessage);
        when(update.hasMessage()).thenReturn(Boolean.TRUE);
    }

    @Test
    public void onUpdateReceivedCleanTest() throws TelegramApiException {
        when(receivedMessage.getText()).thenReturn("/clean");

        botHandler.onUpdateReceived(update);
        verify(botMessageRepo, times(1)).deleteByTelegramId(1);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botHandler).execute(sendMessageCaptor.capture());
        assertEquals("Сообщения почищены", sendMessageCaptor.getValue().getText());
    }

    @Test
    public void onUpdateReceivedMyNoMessagesTest() throws TelegramApiException {
        when(receivedMessage.getText()).thenReturn("/my");

        botHandler.onUpdateReceived(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botHandler).execute(sendMessageCaptor.capture());
        assertEquals("Сообщений пока нет", sendMessageCaptor.getValue().getText());
    }

    @Test
    public void onUpdateReceivedMyTest() throws TelegramApiException {
        when(receivedMessage.getText()).thenReturn("/my");
        when(botMessageRepo.countByTelegramId(1)).thenReturn(1L);

        botHandler.onUpdateReceived(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botHandler).execute(sendMessageCaptor.capture());
        assertTrue(sendMessageCaptor.getValue().getText().contains("Вот что мне удалось найти"));
    }

    @Test
    public void onUpdateReceivedStartTest() throws TelegramApiException {
        when(receivedMessage.getText()).thenReturn("/start");
//        when(botMessageRepo.countByTelegramId(1)).thenReturn(1L);

        botHandler.onUpdateReceived(update);

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botHandler).execute(sendMessageCaptor.capture());
        assertTrue(sendMessageCaptor.getValue().getText().contains("Я есть бот"));
    }

    @Test
    public void onUpdateReceivedNewMessageTest() throws TelegramApiException {
        when(receivedMessage.getText()).thenReturn("any other");
//        when(botMessageRepo.countByTelegramId(1)).thenReturn(1L);

        botHandler.onUpdateReceived(update);
        verify(botMessageRepo).save(any(BotMessage.class));

        ArgumentCaptor<SendMessage> sendMessageCaptor = ArgumentCaptor.forClass(SendMessage.class);
        verify(botHandler).execute(sendMessageCaptor.capture());
        assertTrue(sendMessageCaptor.getValue().getText().contains("Записано"));
    }

    @Test
    public void getBotUsername() {
        assertEquals(botUserName, botHandler.getBotUsername());
    }

    @Test
    public void getBotToken() {
        assertEquals(token, botHandler.getBotToken());
    }
}