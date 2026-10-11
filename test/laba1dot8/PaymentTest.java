package laba1dot8;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PaymentTest {

    @Test
    public void testNestedDateOperations() {
        Payment.Date d = new Payment.Date(2024, 2, 28);
        assertTrue(d.isLeapYear());

        Payment.Date next = d.addDays(1);
        assertEquals("2024.02.29", next.toString());

        Payment.Date march = next.addDays(1);
        assertEquals("2024.03.01", march.toString());
        assertEquals(2, d.daysBetween(march));
    }

    @Test
    public void testExperienceWithNestedDate() {
        Payment.Date hire = new Payment.Date(2020, 6, 15);
        Payment p = new Payment("Тест", 15000, hire, 0, 20, 20);

        Payment.Date beforeAnniversary = new Payment.Date(2025, 6, 14);
        assertEquals(4, p.getExperience(beforeAnniversary));

        Payment.Date onAnniversary = new Payment.Date(2025, 6, 15);
        assertEquals(5, p.getExperience(onAnniversary));
    }

    @Test
    public void testSalaryCalculationFormulas() {

        Payment p = new Payment("Тест", 20000, new Payment.Date(2022, 1, 1), 20.0, 20, 20);

        assertEquals(24000.0, p.calculateGrossSalary(), 1e-4);


        assertEquals(3360.0, p.calculateWithheldAmount(), 1e-4);

        assertEquals(20640.0, p.calculateNetSalary(), 1e-4);
    }
}