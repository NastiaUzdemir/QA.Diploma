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
        var cardInfo = new DataHelper().getInvalidCardInfo();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.successfulMessageCardPayment(cardInfo);

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("DECLINED", statusPayment);
    }

    @DisplayName("Оплата через кредит по данным карты со статусом APPROVED")
    @Test
    public void paymentCreditCardAPPROVED(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getValidCardInfo();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment(cardInfo);

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    @DisplayName("Покупка кредитной картой срок действия - текущий месяц этого года")
    @Test
    public void paymentCreditCardValidityPeriodMonth(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getCardInfoCurrentYearAndMonthApproved();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.successfulMessageCardPayment(cardInfo);

        var paymentId = SQLHelper.getPaymentId();
        var statusPayment = SQLHelper.getCreditStatus(paymentId);
        Assertions.assertEquals("APPROVED", statusPayment);
    }

    // негативные тесты
    @DisplayName("Оплата по карте с пустыми полями")
    @Test
    public void paymentDebitCardEmptyFields(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getSendingAFormWithEmptyFields();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatErrorAndFieldIsRequired(cardInfo);
    }

    @DisplayName("Оплата с невалидным номером карты")
    @Test
    public void paymentDebitInvalidCard(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getInvalidCardNumber();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }

    @DisplayName("Оплата с невалидным значением месяца")
    @Test
    public void paymentDebitCardFakeMonth(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getFakeMonth();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData(cardInfo);
    }

    @DisplayName("Оплата по карте с истекшим сроком действия карты (месяц)")
    @Test
    public void paymentDebitCardInvalidMonth(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getExpiredMonthCard();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData(cardInfo);
    }


    @DisplayName("Оплата по карте с истекшим сроком действия карты (год)")
    @Test
    public void paymentDebitCardInvalidYear(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getExpiredYearCard();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidYearMonth(cardInfo);
    }

    @DisplayName("Оплата по карте с невалидным сроком действия карты (год)")
    @Test
    public void paymentDebitCardFakeYear(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getFakeYear();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageForInvalidData(cardInfo);
    }

    @DisplayName("Оплата по карте с числовым значением поля «Владелец»")
    @Test
    public void paymentCardNumericValueOwnerField(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getNameFilledWithNumber();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment(cardInfo);
    }

    @DisplayName("Оплата по карте с именем владельца карты в поле «Владелец» на кирилице")
    @Test
    public void paymentCardNameOwnerInRussian(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getNameInRussian();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.errorMessageCardPayment(cardInfo);
    }

    @DisplayName("Оплата по карте с указанием невалидного формата поля CVC/CVV")
    @Test
    public void paymentCardInvalidCVV(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getInvalidCVV();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }

    @DisplayName("Оплата по карте нулевым CVC/CVV")
    @Test
    public void paymentCardInvalidZeroCVV(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getZeroCVV();
        var creditPage = mainPage.payOnCard(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }


    //Пустое поле для каждого из полей

    @DisplayName("Номер карты")
    @Test
    public void paymentDebitCardEmptyFieldNumberCard(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getCardNumberEmptyField();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }

    @DisplayName("Месяц")
    @Test
    public void paymentDebitCardMonthEmptyField(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getMonthEmptyField();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }

    @DisplayName("Год")
    @Test
    public void paymentDebitCardYearEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getYearEmptyFiled();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }

    @DisplayName("Владелец")
    @Test
    public void paymentDebitCardNameEmptyFiled(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getNameEmptyFiled();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.fieldIsRequired(cardInfo);
    }

    @DisplayName("CVC")
    @Test
    public void paymentDebitCardCVC(){
        var mainPage = new MainPage();
        var cardInfo = new DataHelper().getCVCEmptyFiled();
        var creditPage = mainPage.buyOnCredit(cardInfo);
        creditPage.enterCardData(cardInfo);
        creditPage.invalidFormatError(cardInfo);
    }
}
