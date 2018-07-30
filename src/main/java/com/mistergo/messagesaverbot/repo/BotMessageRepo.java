package com.mistergo.messagesaverbot.repo;

import com.mistergo.messagesaverbot.model.BotMessage;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BotMessageRepo extends CrudRepository<BotMessage, Long> {
    /**
     * Количество сообщений пользователя
     *
     * @param telegramId    ID пользователя
     * @return              Количество сообщений
     */
    Long countByTelegramId(Integer telegramId);

    /**
     * Найти все сообщения пользователя по ID
     *
     * @param telegramId    ID пользователя
     * @return              Список сообщений
     */
    List<BotMessage> findByTelegramId(Integer telegramId);

    /**
     * Удалить все сообщения пользователя по ID
     *
     * @param telegramId    ID пользователя
     * @return              Список удалённых сообщений
     */
    @Transactional
    List<BotMessage> deleteByTelegramId(Integer telegramId);
}
