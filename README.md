# Тестовый проект: телеграм-бот для сохранения пользовательских сообщений

Имя бота [message_saver_bot](https://t.me/message_saver_bot)

### Перед сборкой проекта необходимо выполнить:
*(Действия выполняются пользователем, под которым установлен Postgresql)*
1. Создать пользователя и БД Postgresql:
```
postgres@server:/home$ createuser <имя пользователя БД>
postgres@server:/home$ createdb <имя БД>
```
2. Дать права пользователю на БД:
```
postgres@server:/home$ psql
postgres=# grant all privileges on database <имя БД> to <имя пользователя БД>;
```
3. В файле проекта application.properties прописать имена БД и пользователя:
```
spring.datasource.url=jdbc:postgresql://localhost:5432/<имя БД>
spring.datasource.username=<имя пользователя БД>
```