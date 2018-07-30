package com.mistergo.messagesaverbot.model;

import javax.persistence.*;

@Entity(name = "message")
public class BotMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "telegram_id")
    private Integer telegramId;
    @Column
    private String text;

    public BotMessage() {
    }

    public BotMessage(Integer telegramId, String text) {
        this.telegramId = telegramId;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTelegramId() {
        return telegramId;
    }

    public void setTelegramId(Integer telegramId) {
        this.telegramId = telegramId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "BotMessage{" +
                "id=" + id +
                ", telegramId=" + telegramId +
                ", text='" + text + '\'' +
                '}';
    }
}
