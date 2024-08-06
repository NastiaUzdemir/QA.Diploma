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

    // позитивные тесты

    @DisplayName("Оплата по валидной дебетовой карте со статусом APPROVED")
    @Test
    public void paymentDebitCardAPPROVED(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardInfo();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.successfulMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getDebitStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Оплата по валидной дебетовой карте со статусом DECLINED")
    @Test
    public void paymentDebitCardDECLINED(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardInfo();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getDebitStatus(paymentId);
        Assertions.assertEquals("DECLINED", statusPayment);
    }

    @DisplayName("Покупка дебетовой картой срок действия - текущий месяц этого года")
    @Test
    public void paymentDebitCardValidityPeriodMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCurrentYearAndMonthApproved();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.successfulMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getDebitStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    // негативные тесты
    @DisplayName("Оплата по карте с пустыми полями")
    @Test
    public void paymentDebitCardEmptyFields(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getSendingAFormWithEmptyFields();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatErrorAndFieldIsRequired();
    }

    @DisplayName("Оплата с невалидным номером карты")
    @Test
    public void paymentDebitInvalidCard(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardNumber();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

    @DisplayName("Оплата с невалидным значением месяца")
    @Test
    public void paymentDebitCardFakeMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getFakeMonth();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageForInvalidData();
    }

    @DisplayName("Оплата по карте с истекшим сроком действия карты (месяц)")
    @Test
    public void paymentDebitCardInvalidMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getExpiredMonthCard();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageForInvalidData();
    }


    @DisplayName("Оплата по карте с истекшим сроком действия карты (год)")
    @Test
    public void paymentDebitCardInvalidYear(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getExpiredYearCard();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageForInvalidYearMonth();
    }

    @DisplayName("Оплата по карте с невалидным сроком действия карты (год)")
    @Test
    public void paymentDebitCardFakeYear(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getFakeYear();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageForInvalidData();
    }

    @DisplayName("Оплата по карте с числовым значением поля «Владелец»")
    @Test
    public void paymentCardNumericValueOwnerField(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameFilledWithNumber();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец» на кирилице")
    @Test
    public void paymentCardNameOwnerInRussian(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameInRussian();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено одним словом")
    @Test
    public void paymentCardNameOneWord(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameOneWord();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено спецсимволами")
    @Test
    public void paymentCardNameSpecialCharacters(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameSpecialCharacters();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец» без ограничений на ввод букв")
    @Test
    public void paymentCardNameNoRestrictionsInput(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameRandomLetters();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с указанием невалидного формата поля CVC/CVV")
    @Test
    public void paymentCardInvalidCVV(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVV();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

    @DisplayName("Оплата по карте унлевым CVC/CVV")
    @Test
    public void paymentCardInvalidZeroCVV(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getZeroCVV();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }


   //Пустое поле для каждого из полей

    @DisplayName("Номер карты")
    @Test
    public void paymentDebitCardEmptyFieldNumberCard(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardNumberEmptyField();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

    @DisplayName("Месяц")
    @Test
    public void paymentDebitCardMonthEmptyField(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getMonthEmptyField();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

    @DisplayName("Год")
    @Test
    public void paymentDebitCardYearEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getYearEmptyFiled();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

    @DisplayName("Владелец")
    @Test
    public void paymentDebitCardNameEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameEmptyFiled();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.fieldIsRequired();
    }

    @DisplayName("CVC")
    @Test
    public void paymentDebitCardCVC(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCVCEmptyFiled();
        var debitPage = mainPage.payOnCard();
        debitPage.enterCardData(cardInfo);
        debitPage.invalidFormatError();
    }

}
