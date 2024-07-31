# Процедура запуска автотестов

## Тестовая документация

1. [Техническое задание](https://github.com/NastiaUzdemir/QA.Diploma/blob/master/documentation/Task.md);
2. [План тестирования](https://github.com/NastiaUzdemir/QA.Diploma/blob/master/documentation/Plan.md);
2. [Отчёт по итогам тестирования](https://github.com/NastiaUzdemir/QA.Diploma/blob/master/documentation/ReportTesting.md);
3. [Отчет по итогам автоматизации](https://github.com/NastiaUzdemir/QA.Diploma/blob/master/documentation/ReportAutomatization.md).

## Подготовительный этап
- Установить и запустить IntelliJ IDEA; 
- Установать и запустить Docker Desktop; 
- копировать репозиторий с Github по [ссылке](https://github.com/NastiaUzdemir/QA.Diploma). 
- Открыть проект в IntelliJ IDEA.

## Запуск тестового приложения
- Запустить MySQL, PostgreSQL, NodeJS через терминал командой:
`docker-compose up`
- В новой вкладке терминала запустить тестируемое приложение:
Для MySQL:
`java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`
Для PostgreSQL:
`java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/app -jar artifacts/aqa-shop.jar`
- Убедиться в готовности системы. Приложение должно быть доступно по адресу: `http://localhost:8080/`

## Запуск тестов

### В новой вкладке терминала запустить тесты:

- Для MySQL:
`./gradlew clean test -Ddb.url=jdbc:mysql://localhost:3306/app`
- Для PostgreSQL:
`./gradlew clean test -Ddb.url=jdbc:postgresql://localhost:5432/app`

### Перезапуск тестов и приложения
Для остановки приложения в окне терминала нужно ввести команду Ctrl+С и повторить необходимые действия из предыдущих разделов.

### Формирование отчёта о тестировании
Предусмотрено формирование отчётности через Allure. Для этого в новой вкладке терминала вводим команду `./gradlew allureserve`