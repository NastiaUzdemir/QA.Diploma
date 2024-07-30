package ru.netology.page;

import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {

    private final SelenideElement cardButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));

    public DebitPage payOnCard(DataHelper.CardInfo cardInfo) {
        cardButton.click();;
        return new DebitPage();
    }

    public CreditPage buyOnCredit(DataHelper.CardInfo cardInfo) {
        creditButton.click();
        return new CreditPage();
    }
}
