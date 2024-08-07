# План автоматизации

## 1. Перечень автоматизируемых сценариев

Приложение в собственной СУБД должно сохранять информацию о том, успешно ли был совершён платёж и каким способом. Данные карт при этом сохранять не допускается.Тестирование проводится для возможности покупки тура двумя способами:

* Обычная оплата: по дебетовой карте.
* Уникальная технология: выдача кредита по данным банковской карты.

**_Карты, представленные для тестирования (файл data.json):_**

1. 4444 4444 4444 4441, status APPROVED
2. 4444 4444 4444 4442, status DECLINED

**_Тестируемые СУБД:_**

* MySQL;
* PostgreSQL.

**_Характеристика вводимых валидных тестовых данных:_**

* поле «Номер карты»: 16 цифр
* поле «Месяц»: двузначное числовое значение от 01 до 12
* поле «Год»: двузначное числовое значение;
* поле «Владелец»: допустимые значения из латинских букв, дефисов, пробелов
* поле «CVC/CCV»: три цифры

## Позитивные сценарии

**1.** **Оплата по валидной дебетовой карте со статусом APPROVED:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» – трехзначное цифровое значение;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: всплывающее окно с уведомлением «Успешно! Операция одобрена банком», В БД статус операции APPROVED._

**2.** **Оплата по валидной дебетовой карте со статусом DECLINED:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4442;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначное цифровое значение;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: появилось всплывающее окно «Ошибка! Банк отказал в проведении операции», в БД статус операции DECLINED._

**3. Оплата через кредит по данным карты со статусом APPROVED:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить в кредит»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначное цифровое значение;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: появилось всплывающее окно «Успешно! Операция одобрена банком», в БД статус операции APPROVED._

**4. Оплата в кредит по данным карты со статусом DECLINED:**

1. Открыть страницу http://localhost:8080/ ; 
2. Кликнуть по кнопке «Купить в кредит»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4442;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначное цифровое значение;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: появилось всплывающее окно «Ошибка! Банк отказал в проведении операции», в БД статус операции DECLINED._

**5. Покупка дебетовой картой срок действия - текущий месяц этого года:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - текущий месяц -1;
5. Ввести в поле «Год» - последние две цифры текущего года;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначное цифровое значение;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: появилось всплывающее окно «Успешно! Операция одобрена банком»_

## Негативные сценарии

_Примечание: негативные сценарии необходимо выполнять для двух форм - обычная оплата и оплата в кредит_

**1. Оплата по карте с пустыми полями:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить в кредит»;
3. Оставить все поля пустыми;
4. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке под каждым из полей. Форма не отправлена_

**2. Оплата с невалидным номером карты:**

1. Открыть страницу http://localhost:8080/ ; 
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 1111 2222 3333 444;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» – трехзначный номер;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке «Неверный формат» под полем «Номер карты». Форма не отправлена._

**3. Оплата по карте с невалидным значением месяца:**

1. Открыть страницу http://localhost:8080/ ; 
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» – 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - число 13;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке «Неверно указан срок действия карты» под полем «Месяц». Форма не отправлена._
Оплата по карте с истекшим сроком действия карты (месяц)

**4. Оплата по карте с истекшим сроком действия карты (месяц):**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» – 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - оследние две цифры текучего месяца -1;
5. Ввести в поле «Год» - последние две цифры текущего года;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке «Истёк срок действия карты» под полем «Месяц». Форма не отправлена._

**5. Оплата по карте с невалидным сроком действия карты (год):**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» – любое число от 13 до 99;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке «Неверно указан срок действия карты» под полем «Год». Форма не отправлена_

**6. Оплата по карте с истекшим сроком действия карты (год):**

1. Открыть страницу http://localhost:8080/ ; 
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» – последние две цифры текущего года -1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить».

_Ожидаемый результат: уведомление об ошибке «Истёк срок действия карты» под полем «Год». Форма не отправлена_

**7. Оплата по карте с числовым значением поля «Владелец»:**

1. Открыть страницу http://localhost:8080/ ; 
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - 666666 6666;
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить»

_Ожидаемый результат: уведомление об ошибке «Неверный формат» внизу поля «Владелец». Форма не отправлена._

**8. Оплата по карте с именем владельца карты в поле «Владелец» на кирилице:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - на кирилице;
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить»

_Ожидаемый результат: уведомление об ошибке «Неверный формат» внизу поля «Владелец». Форма не отправлена._

**9. Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено одним словом:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - одно слово;
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить»

_Ожидаемый результат: уведомление об ошибке «Неверный формат» внизу поля «Владелец». Форма не отправлена._

**10. Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено спецсимволами:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - спецсимволы;
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить»

_Ожидаемый результат: уведомление об ошибке «Неверный формат» внизу поля «Владелец». Форма не отправлена._

**11. Оплата по карте с именем владельца карты в поле «Владелец» без ограничений на ввод букв:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - набор букв на латинском алфавите;
7. Ввести в поле «CVC/CVV» - трехзначный номер;
8. Кликнуть по кнопке «Продолжить»

_Ожидаемый результат: уведомление об ошибке «Неверный формат» внизу поля «Владелец». Форма не отправлена._

**12. Оплата по карте с указанием невалидного формата поля CVC/CVV:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» значения в интервале 0-99;
8. Кликнуть по кнопке «Продолжить»;

_Ожидаемый результат: уведомление об ошибке «Неверный формат» под полем «CVC/CVV». Форма не отправлена._

**13. Оплата по карте нулевым CVC/CVV:**

1. Открыть страницу http://localhost:8080/ ;
2. Кликнуть по кнопке «Купить»;
3. Ввести в поле «Номер карты» - 4444 4444 4444 4441;
4. Ввести в поле «Месяц» - любое число от 01 до 12;
5. Ввести в поле «Год» - последние две цифры текущего года +1;
6. Ввести в поле «Владелец» - валидное значение (например Ivanov Ivan);
7. Ввести в поле «CVC/CVV» значение 000;
8. Кликнуть по кнопке «Продолжить»;

_Ожидаемый результат: уведомление об ошибке «Неверный формат» под полем «CVC/CVV». Форма не отправлена._


# 2. Перечень используемых инструментов с обоснованием выбора
1. IntelliJ IDEA 2024.1.1 (Ultimate Edition) - среда разработки ПО с поддержкой Java 11
2. Java 11 - язык программирования для написания автотестов. 
3. Gradle - cистема автоматической сборки проектов, позволяет подключать нужные библиотеки через настройку зависимостей
4. JUnit 5 - библиотека для модульного тестирования программного обеспечения на языке Java
5. Git - система управления версиями с распределенной архитектурой, обеспечивающая отслеживание изменений программного кода и управления ими
6. GitHub - веб-сервис для хостинга IT-проектов и их совместной разработки
7. Lombok - библиотека для генерации кода, позволяющая убрать шаблонный код при создании классов
8. Selenide - библиотека для автоматизированного тестирования веб-приложений на основе Selenium WebDriver, позволяющая использовать CSS селекторы
9. Faker - библиотека, которую можно использовать для создания широкого массива реальных данных, легко настраивается, много типов данных.
10. Allure - фреймворк от Яндекса для создания простых и понятных отчётов автотестов
11. Docker&DockerCompose - эффективный инструмент для контейнеризации и управления базами данных, что улучшает использование ресурсов.
12. База данных SQL - используется для хранения информации о платежах и их успешном выполнении. 
13. MySQL и PostgreSQL - свободные системы управления базами данных, обеспечивающие надежное хранение информации. 
14. Chrome DevTools - инструмент, который используется в тестировании сайтов и приложений. В нем представлено множество функций, которые помогают тестировщикам проверять, отлаживать и оптимизировать веб-приложения.


# 3. Перечень и описание возможных рисков при автоматизации
1. Возникновение сложностей при первом запуске системы (SUT) и взаимодействии с базой данных (БД).
2. Вероятность возникновения проблем из-за необходимости поддерживать две разные системы управления базами данных (СУБД) - MySQL и Postgres.
3. Возможны сложности в поддержании актуальности состояния автоматизированных тестов при изменениях в данных.
4. Недостаточное покрытие тестами может привести к проблемам в процессе тестирования.
5. Без достаточной документации может быть затруднительно понять ожидаемое поведение системы и определить, что является ошибкой.
6. Могут возникнуть сложности при поиске и использовании необходимых CSS-селекторов на веб-страницах.


# 4. Интервальная оценка с учётом рисков в часах
* Настройка тестовых данных и написание тест-кейса - 10 часов
* Подготовка и настройка проекта - 8ч.
* Написание автотестов - от 30 до 60 часов;
* Составление отчетов о тестировании - 12 часов
* Исправление ошибок - 10 часов

# 5. План сдачи работ
* Настройка тестовых данных и написание тест-кейса - 23-26 июня;
* Подготовка и настройка проекта - 26-27 июня;
* Написание автотестов - 28 июня-19 июля;
* Составление отчетов о тестировании - 20-22 июля;
* Исправление ошибок - 29-31 июля;
