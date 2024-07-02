package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$$;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class DebitPage extends MainPage {

    private final ElementsCollection fields = $$(".input__control");
    private final SelenideElement cardNumberField = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement expirationMonthField = $("[placeholder='08']");
    private final SelenideElement expirationYearField = $("[placeholder='22']");
    private final SelenideElement cardHolderNameField = fields.get(3);
    private final SelenideElement cardCVVField = $("[placeholder='999']");
    private final SelenideElement continueButton = $(withText("Продолжить"));

    private final SelenideElement successfulMessage = $(withText("Успешно"));
    private final SelenideElement errorMessage = $(withText("Ошибка"));
    private final SelenideElement invalidFormatError = $(withText("Неверный формат"));
    private final SelenideElement fieldIsRequired = $(withText("Поле обязательно для заполнения"));
    private final SelenideElement errorMessageForInvalidYear = $(withText("Истёк срок действия карты"));
    private final SelenideElement errorMessageForInvalidMonth = $(withText("Неверно указан срок действия карты"));

    public void enterCardData(DataHelper.CardInfo cardInfo){
        cardNumberField.setValue(cardInfo.getCardNumber());
        expirationMonthField.setValue(cardInfo.getMonth());
        expirationYearField.setValue(cardInfo.getYear());
        cardHolderNameField.setValue(cardInfo.getName());
        cardCVVField.setValue(cardInfo.getCvv());
        continueButton.click();
    }

    public void successfulMessageCardPayment(){
        successfulMessage.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void errorMessageCardPayment(){
        errorMessage.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void invalidFormatError() {
        invalidFormatError.shouldBe(visible);
    }

    public void fieldIsRequired() {
        fieldIsRequired.shouldBe(visible);
    }

    public void errorMessageForInvalidYear() {
        errorMessageForInvalidYear.shouldBe(visible);
    }

    public void errorMessageForInvalidMonth() {
        errorMessageForInvalidMonth.shouldBe(visible);
    }

}
