package ru.netology.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement cardButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));

    public DebitPage payOnCard() {
        cardButton.click();;
        return new DebitPage();
    }

    public CreditPage buyOnCredit() {
        creditButton.click();
        return new CreditPage();
    }
}
