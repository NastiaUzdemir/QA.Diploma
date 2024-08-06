package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;
import java.util.Locale;

import static ru.netology.data.DataGenerator.*;

public class DataHelper {
    static Faker faker = new Faker();
    static Faker enOptionFaker = new Faker(new Locale("en"));
    static Faker ruOptionFaker = new Faker(new Locale("ru"));
    private static final DataGenerator dataGenerator = new DataGenerator();

    public static String getCardNumberApproved(){
        return "4444444444444441";
    }
    public static String getCardNumberDeclined(){
        return "4444444444444442";
    }
    public static String getCarNumberFake(){
        return "444444444";
    }

    public static CardInfo getValidCardInfo(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getInvalidCardInfo(){
        return new CardInfo(
                getCardNumberDeclined(),
                getShiftedMonth(1),
                getShiftedYear(2),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getCardInfoCurrentYearAndMonthApproved() {
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(0),
                getShiftedYear(0),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getCardInfoCurrentYearAndMonthDeclined() {
        return new CardInfo(
                getCardNumberDeclined(),
                getShiftedMonth(0),
                getShiftedYear(0),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getSendingAFormWithEmptyFields() {
        return new CardInfo(
                "",
                "",
                "",
                "",
                ""
        );
    }

    public static CardInfo getCardNumberEmptyField() {
        return new CardInfo(
                "",
                getShiftedMonth(5),
                getShiftedYear(1),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getMonthEmptyField() {
        return new CardInfo(
                getCardNumberApproved(),
                "",
                getShiftedYear(1),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getYearEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                "",
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getNameEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                "",
                generateRandomCVV()
        );
    }

    public static CardInfo getCVCEmptyFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                enOptionFaker.name().fullName(),
                ""
        );
    }

    public static CardInfo getZeroCVV(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                enOptionFaker.name().fullName(),
                "000"
        );
    }

    public static CardInfo getExpiredMonthCard(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(-1),
                getShiftedYear(0),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getExpiredYearCard(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(-1),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getInvalidCardNumber(){
        return new CardInfo(
                getCarNumberFake(),
                getShiftedMonth(1),
                getShiftedYear(1),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getFakeMonth(){
        return new CardInfo(
                getCardNumberApproved(),
                fakeMonth(),
                getShiftedYear(1),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getFakeYear(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                fakeYear(),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getNameFilledWithNumber(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(1),
                generateRandomNumber(),
                generateRandomCVV()
        );
    }

    public static CardInfo getNameOneWord(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                "Ivanov",
                generateRandomCVV()
        );
    }

    public static CardInfo getNameSpecialCharacters(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                "%$$#@&&*",
                generateRandomCVV()
        );
    }

    public static CardInfo getNameRandomLetters(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(2),
                "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA" +
                        "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA",
                generateRandomCVV()
        );
    }

    public static CardInfo getInvalidCVV(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(3),
                enOptionFaker.name().fullName(),
                Integer.toString(enOptionFaker.number().numberBetween(0, 99))
        );
    }

    public static CardInfo getZeroInTheMonthFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                "00",
                getShiftedYear(3),
                enOptionFaker.name().fullName(),
                generateRandomCVV()
        );
    }

    public static CardInfo getZeroInTheCVVFiled(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(3),
                enOptionFaker.name().fullName(),
                "000"
        );
    }

    public static CardInfo getNameInRussian(){
        return new CardInfo(
                getCardNumberApproved(),
                getShiftedMonth(1),
                getShiftedYear(1),
                ruOptionFaker.name().fullName(),
                generateRandomCVV()
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
