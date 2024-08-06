package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
import ru.netology.data.SQLHelper;
import ru.netology.page.MainPage;

import static com.codeborne.selenide.Selenide.open;

public class CreditCardTest {

    @BeforeAll
    static void setUpAll(){
        SelenideLogger.addListener("allure", new AllureSelenide());
    }
    @AfterAll
    static void tearDownAll(){
        SelenideLogger.removeListener("allure");
    }
    @BeforeEach
    public void openChrome(){
        open("http://localhost:8080/");
    }

    // позитиыные тесты

    @DisplayName("Оплата в кредит по данным карты со статусом DECLINED")
    @Test
    public void paymentCreditCardDECLINED(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardInfo();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.successfulMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("DECLINED", statusPayment);
    }

    @DisplayName("Оплата через кредит по данным карты со статусом APPROVED")
    @Test
    public void paymentCreditCardAPPROVED(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getValidCardInfo();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Покупка кредитной картой срок действия - текущий месяц этого года")
    @Test
    public void paymentCreditCardValidityPeriodMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardInfoCurrentYearAndMonthApproved();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.successfulMessageCardPayment();

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    // негативные тесты
    @DisplayName("Оплата по карте с пустыми полями")
    @Test
    public void paymentDebitCardEmptyFields(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getSendingAFormWithEmptyFields();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatErrorAndFieldIsRequired();
    }

    @DisplayName("Оплата с невалидным номером карты")
    @Test
    public void paymentDebitInvalidCard(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCardNumber();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }

    @DisplayName("Оплата с невалидным значением месяца")
    @Test
    public void paymentDebitCardFakeMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getFakeMonth();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData();
    }

    @DisplayName("Оплата по карте с истекшим сроком действия карты (месяц)")
    @Test
    public void paymentDebitCardInvalidMonth(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getExpiredMonthCard();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData();
    }


    @DisplayName("Оплата по карте с истекшим сроком действия карты (год)")
    @Test
    public void paymentDebitCardInvalidYear(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getExpiredYearCard();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidYearMonth();
    }

    @DisplayName("Оплата по карте с невалидным сроком действия карты (год)")
    @Test
    public void paymentDebitCardFakeYear(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getFakeYear();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData();
    }

    @DisplayName("Оплата по карте с числовым значением поля «Владелец»")
    @Test
    public void paymentCardNumericValueOwnerField(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameFilledWithNumber();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец» на кирилице")
    @Test
    public void paymentCardNameOwnerInRussian(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameInRussian();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено одним словом")
    @Test
    public void paymentCardNameOneWord(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameOneWord();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец», если поле заполнено спецсимволами")
    @Test
    public void paymentCardNameSpecialCharacters(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameSpecialCharacters();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец» без ограничений на ввод букв")
    @Test
    public void paymentCardNameNoRestrictionsInput(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameRandomLetters();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment();
    }

    @DisplayName("Оплата по карте с указанием невалидного формата поля CVC/CVV")
    @Test
    public void paymentCardInvalidCVV(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getInvalidCVV();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }

    @DisplayName("Оплата по карте нулевым CVC/CVV")
    @Test
    public void paymentCardInvalidZeroCVV(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getZeroCVV();
        var creditPage = mainPage.payOnCard();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }


    //Пустое поле для каждого из полей

    @DisplayName("Номер карты")
    @Test
    public void paymentDebitCardEmptyFieldNumberCard(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCardNumberEmptyField();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }

    @DisplayName("Месяц")
    @Test
    public void paymentDebitCardMonthEmptyField(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getMonthEmptyField();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }

    @DisplayName("Год")
    @Test
    public void paymentDebitCardYearEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getYearEmptyFiled();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }

    @DisplayName("Владелец")
    @Test
    public void paymentDebitCardNameEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getNameEmptyFiled();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.fieldIsRequired();
    }

    @DisplayName("CVC")
    @Test
    public void paymentDebitCardCVC(){
        var mainPage = new MainPage();
        var cardInfo = DataHelper.getCVCEmptyFiled();
        var creditPage = mainPage.buyOnCredit();
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError();
    }
}
