Здравствуйте, отправляю тестовое задание, выполнил и обязательные условия, и дополнительные (обработка ошибок, кэширование, Unit тесты для репозитория).

Также сделал тёмную и светлую тему, добавил функционал добавления пользователей в закладки, и отображение их на отдельном экране, использовал Type safe navigation.
Для запуска нюансов нет, просто сохранить проект на компьютер и запустить через Android Studio.

Используемый стек: Jetpack Compose, Material 3, Type safe navigation, Clean Code, Clean Architecture,
MVI (ViewModel + Intents/Events), Room для кэширования, Dagger Hilt для DI, Retrofit2, OkHttp3, Gson converter, Coroutines + Flow.

![1 Главный экран](https://github.com/user-attachments/assets/16f82e55-dbfb-4dfb-a149-c308d8596695)
![2 Экран деталей](https://github.com/user-attachments/assets/e1afd130-c208-4bf2-8046-960405c1f0e5)
![3 Экран закладок](https://github.com/user-attachments/assets/905a1781-fdd7-4cc8-b7f2-579d0040fefa)

Задание
Создать простое Android-приложение, которое:
1.	Делает HTTP-запрос к публичному API (https://jsonplaceholder.typicode.com/users).
2.	Отображает список пользователей (ID, имя, email) в простом списке.
3.	Позволяет открыть экран с деталями пользователя (имя, email, телефон, город).
 
Технические требования
•	Асинхронность: Coroutines + Flow.
•	Запрос в сеть: Retrofit или Ktor (по выбору кандидата).
•	UI: Jetpack Compose или XML (на выбор).
•	Архитектура: MVVM (ViewModel + Repository).
•	DI: Можно без DI, но плюсом будет Koin или Hilt.
 
Дополнительно (необязательно, но плюс)
•	Обработка ошибок (если API недоступно, показывать сообщение).
•	Локальное кеширование (например, в List в памяти или через Room).
•	Минимальные Unit-тесты (хотя бы для ViewModel).
