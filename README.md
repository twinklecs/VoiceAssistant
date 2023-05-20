# Голосовой помошник на Android Studio с Kotlin

![Общение с помошником](https://github.com/twinklecs/VoiceAssistant/blob/main/Pictures/SCT2bsKMtJw.jpg)

Это приложение разработано на Android Studio и написано на языке Kotlin. Оно позволяет пользователям взаимодействовать с технологией на основе искусственного интеллекта. Оно использует технологию нейронных сетей, в частности, Chat GPT,, который может помочь пользователям в различных сферах их жизни.

## Установка и настройка

Для того, чтобы установить и настроить приложение, выполните следующие шаги:

1. Склонируйте репозиторий в свою папку командой git clone или из других источников.
2. Откройте проект в Android Studio.
3. Установите SQL Server и Microsoft SQL Server Management Studio.
4. Перетащите туда script.sql и выполните запрос.
5. Установите Pyhton и PyCharm.
6. Откройте проект в PyCharm.
7. Установите зависимости Flask и openai.
8. Запустите сервер командой `python main.py`.
9. Запустите приложение на эмуляторе или на вашем Android-устройстве.

![У вас должно получится](https://github.com/twinklecs/VoiceAssistant/blob/main/Pictures/Eo6prc6YKZs.jpg)

## Функционал

Приложение "Голосовой помошник" имеет следующий функционал:

- Пользователи могут авторизоваться и зарегистрироваться в приложении.
- Пользователи могут отправлять голосовые и текстовые сообщения Голосовому помошнику.
- Голосовому помошник обрабатывает голосовые и текстовые сообщения и возвращает соответствующий результат.
- Пользователи могут получить ответ в виде текстового собщения и прослушивать его на своих Android-устройствах.

![KEfv4zIadsU](https://github.com/twinklecs/VoiceAssistant/blob/main/Pictures/KEfv4zIadsU.jpg)

## Архитектура

Приложение "Голосовой помошник" имеет клиент-серверную архитектуру. Клиентская часть написана на языке Kotlin, а серверная часть - на Python.

Клиентская часть приложения состоит из следующих компонентов:

- MainActivity - основная активность приложения, на которой пользователь отправляет голосовые и текстовые сообщения и получает результаты.
- AuthActivity- активность, позволяет пользователю авторизоваться в приложении.
- RegistrationActivity- активность, позволяет пользователю зарегистрироваться в приложении.

Серверная часть приложения основана на Flask - микрофреймворке для создания веб-приложений на языке Python. Сервер состоит из следующих компонентов:

- main.py - главный файл сервера, который обрабатывает запросы и возвращает соответствующий результат.

## Использование

Для использования приложением "Голосовой помошник", вам необходимо выполнить следующие действия:

1. Запустить приложение на своем Android-устройстве или на эмуляторе.
2. Напишите сообщение или нажмите на кнопку "Speak" и заговорите запрос голосом.
3. Отправьте запрос, нажав на кнопку "Send".
4. Дождитесь ответа от сервера.
5. Прочтите или прослушайте сообщение.

## Контакты

В случае возникновения проблем или вопросов по использованию приложения, вы можете связаться с нами по следующим контактным данным:

- Email: dim.iwanencko2014@gmail.com
- Telegram: @twinkle_e
