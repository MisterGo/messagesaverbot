
# Тестовый проект: телеграм-бот для сохранения пользовательских сообщений  
  
Имя бота [message_saver_bot](https://t.me/message_saver_bot)  
  
### Перед сборкой проекта необходимо выполнить:  
*(Действия выполняются пользователем, под которым установлен Postgresql)*  
1. Создать БД Postgresql:  
```  
postgres@server:/home$ createdb -U postgres <имя БД>  
```  
2. В файле проекта application.properties прописать имя БД:  
```  
spring.datasource.url=jdbc:postgresql://localhost:5432/<имя БД>  
```	

### Сборка и запуск проекта:
1. ```mvn clean package```
2. ```java -jar message-saver-bot-1.0-SNAPSHOT.jar```

