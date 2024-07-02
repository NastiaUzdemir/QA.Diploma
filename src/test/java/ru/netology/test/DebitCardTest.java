package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.data.SQLHelper.cleanDB;

public class DebitCardTest {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
        cleanDB();
    }
    @BeforeEach
    public void openChrome(){
        open("http://localhost:8080/");
    }

    // Позитивные тесты

    @DisplayName("Оплата по валидной дебетовой карте со статусом APPROVED")
    @Test
    public void paymentDebitCardAPPROVED(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getValidCardInfo();
        var debitPage = mainPage.payOnCard(cardInfo);
        debitPage.enterCardData(cardInfo);
        debitPage.successfulMessageCardPayment();
        Assertions.assertEquals("APPROVED", SQLHelper.getDebitStatus());
    }
}
