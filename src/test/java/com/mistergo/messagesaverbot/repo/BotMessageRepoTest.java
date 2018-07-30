package com.mistergo.messagesaverbot.repo;

import com.mistergo.messagesaverbot.model.BotMessage;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BotMessageRepoTest {
    private static final int TELEGRAM_ID = 111;

    @Autowired
    private TestEntityManager testEntityManager;
    @Autowired
    private BotMessageRepo botMessageRepo;

    private BotMessage message1 = new BotMessage(TELEGRAM_ID, "First text");
    private BotMessage message2 = new BotMessage(TELEGRAM_ID, "Second text");

    @Before
    public void setUp() throws Exception {
        testEntityManager.persist(message1);
        testEntityManager.persist(message2);
        testEntityManager.flush();
    }

    @Test
    public void countByTelegramId() {
        assertEquals(botMessageRepo.countByTelegramId(TELEGRAM_ID), Long.valueOf(2L));
        assertEquals(botMessageRepo.countByTelegramId(222), Long.valueOf(0L));
    }

    @Test
    public void findByTelegramId() {
        assertEquals(botMessageRepo.findByTelegramId(TELEGRAM_ID).size(), 2);
        assertEquals(botMessageRepo.findByTelegramId(TELEGRAM_ID).get(0).getText(), "First text");
    }

    @Test
    public void deleteByTelegramId() {
        assertEquals(botMessageRepo.deleteByTelegramId(TELEGRAM_ID).size(), 2);
        assertEquals(botMessageRepo.countByTelegramId(TELEGRAM_ID), Long.valueOf(0L));
    }
}