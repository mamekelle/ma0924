package ma0924;

import java.time.LocalDate;
import java.time.Month;

public class HolidayUtils {

    public static LocalDate getIndependenceDay(int year) {
        LocalDate july4th = LocalDate.of(year, Month.JULY, 4);
        // If falls on Sat apply Fri
        if (july4th.getDayOfWeek().getValue() == 6) { 
            return july4th.minusDays(1);
        } 
        // If falls on Sun apply Mon
        else if (july4th.getDayOfWeek().getValue() == 7) { 
            return july4th.plusDays(1);
        }
        return july4th;
    }

    public static LocalDate getLaborDay(int year) {
        LocalDate laborDay = LocalDate.of(year, Month.SEPTEMBER, 1);
        // get first Monday
        while (laborDay.getDayOfWeek().getValue() != 1) { 
            laborDay = laborDay.plusDays(1);
        }
        return laborDay;
    }
}

