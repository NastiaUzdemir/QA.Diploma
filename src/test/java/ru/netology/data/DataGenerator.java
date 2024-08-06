package ru.netology.data;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static ru.netology.data.DataHelper.faker;

public class DataGenerator {
    private static final LocalDate actualData = LocalDate.now();
    private static final DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yy");
    private static final DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM");

    public static String getShiftedYear(int numYears) {
        LocalDate newDate = actualData.plusYears(numYears);
        return formatterYear.format(newDate);
    }

    public static String fakeYear() {
        return Integer.toString(faker.number().numberBetween(28, 99));
    }

    public static String getShiftedMonth(int numMonths) {
        LocalDate newDate = actualData.plusMonths(numMonths);
        return formatterMonth.format(newDate);
    }

    public static String fakeMonth() {
        return Integer.toString(faker.number().numberBetween(1, 12)); // Месяцы от 1 до 12
    }

    public static String generateRandomCVV(){
        return Integer.toString(faker.number().numberBetween(100, 999));
    }

    public static String generateRandomNumber(){
        return Integer.toString(faker.number().numberBetween(1, 999));
    }



    @Value
    public static class Year {
        String year;
    }

    @Value
    public static class Month {
        String month;
    }
}
