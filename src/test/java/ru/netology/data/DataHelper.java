package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;

public class DataHelper {
    static Faker faker = new Faker();
    static Faker enOptionFaker = new Faker(new Locale("en"));
    static Faker ruOptionFaker = new Faker(new Locale("ru"));
    static DataGenerator dataGenerator = new DataGenerator();

    public String getCardNumberApproved(){
        return "4444444444444441";
    }
    public String getCardNumberDeclined(){
        return "4444444444444442";
    }
    public String getCarNumberFake(){
        return "444444444";
    }

    public CardInfo getValidCardInfo(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getInvalidCardInfo(){
        return new CardInfo(
                getCardNumberDeclined(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getCardInfoCurrentYearAndMonthApproved() {
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(0).getMonth(),
                dataGenerator.getShiftedYear(0).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getCardInfoCurrentYearAndMonthDeclined() {
        return new CardInfo(
                getCardNumberDeclined(),
                dataGenerator.getShiftedMonth(0).getMonth(),
                dataGenerator.getShiftedYear(0).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getSendingAFormWithEmptyFields() {
        return new CardInfo(
                "",
                "",
                "",
                "",
                ""
        );
    }

    public CardInfo getCardNumberEmptyField() {
        return new CardInfo(
                "",
                dataGenerator.getShiftedMonth(5).getMonth(),
                dataGenerator.getShiftedYear(1).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getMonthEmptyField() {
        return new CardInfo(
                getCardNumberApproved(),
                "",
                dataGenerator.getShiftedYear(1).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getYearEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                "",
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getNameEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                "",
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getCVCEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                enOptionFaker.name().fullName(),
                ""
        );
    }

    public CardInfo getZeroCVV(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                enOptionFaker.name().fullName(),
                "000"
        );
    }

    public CardInfo getExpiredMonthCard(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(-1).getMonth(),
                dataGenerator.getShiftedYear(0).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getExpiredYearCard(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(-1).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getInvalidCardNumber(){
        return new CardInfo(
                getCarNumberFake(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(1).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getFakeMonth(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.fakeMonth().getMonth(),
                dataGenerator.getShiftedYear(1).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getFakeYear(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.fakeYear().getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getNameFilledWithNumber(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(1).getYear(),
                dataGenerator.generateRandomNumber(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getNameOneWord(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                "Ivanov",
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getNameSpecialCharacters(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(2).getYear(),
                "%$$#@&&*",
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getInvalidCVV(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(3).getYear(),
                enOptionFaker.name().fullName(),
                Integer.toString(enOptionFaker.number().numberBetween(0, 99))
        );
    }

    public CardInfo getZeroInTheMonthFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                "00",
                dataGenerator.getShiftedYear(3).getYear(),
                enOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }

    public CardInfo getZeroInTheCVVFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(3).getYear(),
                enOptionFaker.name().fullName(),
                "000"
        );
    }

    public CardInfo getNameInRussian(){
        return new CardInfo(
                getCardNumberApproved(),
                dataGenerator.getShiftedMonth(1).getMonth(),
                dataGenerator.getShiftedYear(1).getYear(),
                ruOptionFaker.name().fullName(),
                dataGenerator.generateRandomCVV()
        );
    }


    @Value
    public static class CardInfo {
        String cardNumber;
        String month;
        String year;
        String name;
        String cvv;
    }


}
