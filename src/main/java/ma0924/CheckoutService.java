package ma0924;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class CheckoutService {

    public RentalAgreement checkout(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        if (rentalDays < 1) throw new IllegalArgumentException("Rental days should be 1 or greater");
        if (discountPercent < 0 || discountPercent > 100) throw new IllegalArgumentException("Discount percent should be between 0 and 100");

        LocalDate dueDate = checkoutDate.plusDays(rentalDays);
        int chargeableDays = calculateChargeableDays(checkoutDate, dueDate, tool);
        
        BigDecimal preDiscountCharge = tool.getDailyCharge().multiply(BigDecimal.valueOf(chargeableDays));
        BigDecimal discountAmount = preDiscountCharge.multiply(BigDecimal.valueOf(discountPercent / 100.0));
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new RentalAgreement(
            tool.getToolCode(),
            tool.getToolType(),
            tool.getBrand(),
            rentalDays,
            checkoutDate,
            dueDate,
            tool.getDailyCharge(),
            chargeableDays,
            preDiscountCharge,
            discountPercent,
            discountAmount,
            finalCharge
        );        
    }

    private int calculateChargeableDays(LocalDate checkoutDate, LocalDate dueDate, Tool tool) {
        int chargeableDays = 0;
        Set<LocalDate> holidays = getHolidays(checkoutDate.getYear(), dueDate.getYear());

        for (LocalDate date = checkoutDate.plusDays(1); !date.isAfter(dueDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();
            boolean isHoliday = holidays.contains(date);

            if (tool.isWeekdayCharge() && dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY && !isHoliday) {
                chargeableDays++;
            } else if (tool.isWeekendCharge() && (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY)) {
                chargeableDays++;
            } else if (tool.isHolidayCharge() && isHoliday) {
                chargeableDays++;
            }
        }
        return chargeableDays;
    }

    private Set<LocalDate> getHolidays(int startYear, int endYear) {
        Set<LocalDate> holidays = new HashSet<>();
        for (int year = startYear; year <= endYear; year++) {
            holidays.add(HolidayUtils.getIndependenceDay(year));
            holidays.add(HolidayUtils.getLaborDay(year));
        }
        return holidays;
    }
}
