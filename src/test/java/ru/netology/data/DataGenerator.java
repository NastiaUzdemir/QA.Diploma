package ru.netology.data;
import lombok.Value;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static ru.netology.data.DataHelper.faker;

public class DataGenerator {
    private static int length;
    private final LocalDate actualData = LocalDate.now();
    private final DateTimeFormatter formatterYear = DateTimeFormatter.ofPattern("yy");
    private final DateTimeFormatter formatterMonth = DateTimeFormatter.ofPattern("MM");

    public Year getShiftedYear(int numYears) {
        LocalDate newDate = actualData.plusYears(numYears);
        return new Year(formatterYear.format(newDate));
    }

    public Year fakeYear() {
        return new Year(Integer.toString(faker.number().numberBetween(28, 99)));
    }

    public Month getShiftedMonth(int numMonths) {
        LocalDate newDate = actualData.plusMonths(numMonths);
        return new Month(formatterMonth.format(newDate));
    }

    public Month fakeMonth() {
        return new Month(Integer.toString(faker.number().numberBetween(13, 99)));
    }

    public String generateRandomCVV(){
        return Integer.toString(faker.number().numberBetween(100, 999));
    }

    public String generateRandomNumber(){
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
