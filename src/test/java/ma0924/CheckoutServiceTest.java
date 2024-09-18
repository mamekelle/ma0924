package ma0924;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.jupiter.api.Test;

public class CheckoutServiceTest {

    @Test
    public void testScenario1() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", new BigDecimal(2.99), true, false, false);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        assertThrows(IllegalArgumentException.class, () -> {
            service.checkout(tool, 5, 101, checkoutDate); // Discount above 100%
        });
    }

    @Test
    public void testScenario2() {
        Tool tool = new Tool("LADW", "Ladder", "Werner", new BigDecimal(1.99), true, true, false);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        RentalAgreement agreement = service.checkout(tool, 3, 10, checkoutDate);
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testScenario3() {
        Tool tool = new Tool("CHNS", "Chainsaw", "Stihl", new BigDecimal(1.49), true, false, true);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        RentalAgreement agreement = service.checkout(tool, 5, 25, checkoutDate);
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testScenario4() {
        Tool tool = new Tool("JAKD", "Jackhammer", "DeWalt", new BigDecimal(2.99), true, false, false);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);

        RentalAgreement agreement = service.checkout(tool, 6, 0, checkoutDate);
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testScenario5() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", new BigDecimal(2.99), true, false, false);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);

        RentalAgreement agreement = service.checkout(tool, 9, 0, checkoutDate);
        assertNotNull(agreement);
        agreement.print();
    }

    @Test
    public void testScenario6() {
        Tool tool = new Tool("JAKR", "Jackhammer", "Ridgid", new BigDecimal(2.99), true, false, false);
        CheckoutService service = new CheckoutService();
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        RentalAgreement agreement = service.checkout(tool, 4, 50, checkoutDate);
        assertNotNull(agreement);
        agreement.print();
    }
}
