package ru.netology.page;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CreditPage {

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
    private final SelenideElement errorMessageForInvalidYearMonth = $(withText("Истёк срок действия карты"));
    private final SelenideElement errorMessageForInvalidData = $(withText("Неверно указан срок действия карты"));

    public void enterCardData(DataHelper.CardInfo cardInfo){
        cardNumberField.setValue(cardInfo.getCardNumber());
        expirationMonthField.setValue(cardInfo.getMonth());
        expirationYearField.setValue(cardInfo.getYear());
        cardHolderNameField.setValue(cardInfo.getName());
        cardCVVField.setValue(cardInfo.getCvv());
        continueButton.click();
    }

    public void successfulMessageCardPayment(DataHelper.CardInfo cardInfo){
        successfulMessage.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void errorMessageCardPayment(DataHelper.CardInfo cardInfo){
        errorMessage.shouldBe(visible, Duration.ofSeconds(20));
    }

    public void invalidFormatError(DataHelper.CardInfo cardInfo) {
        invalidFormatError.shouldBe(visible);
    }

    public void invalidFormatErrorAndFieldIsRequired(DataHelper.CardInfo cardInfo) {
        invalidFormatError.shouldBe(visible);
        fieldIsRequired.shouldBe(visible);
    }

    public void fieldIsRequired(DataHelper.CardInfo cardInfo) {
        fieldIsRequired.shouldBe(visible);
    }

    public void errorMessageForInvalidYearMonth(DataHelper.CardInfo cardInfo) {
        errorMessageForInvalidYearMonth.shouldBe(visible);
    }

    public void errorMessageForInvalidData(DataHelper.CardInfo cardInfo) {
        errorMessageForInvalidData.shouldBe(visible);
    }
}
