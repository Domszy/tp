package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class DisposableIncomeTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new DisposableIncome(null));
    }

    @Test
    public void constructor_invalidDisposableIncome_throwsIllegalArgumentException() {
        String invalidDisposableIncome = "-9";
        assertThrows(IllegalArgumentException.class, () -> new DisposableIncome(invalidDisposableIncome));
    }

    @Test
    public void isValidDisposableIncome() {
        // null phone number
        assertThrows(NullPointerException.class, () -> RiskAppetite.isValidRiskAppetite(null));

        // invalid phone numbers
        assertFalse(DisposableIncome.isValidDisposableIncome(" ")); // spaces only
        assertFalse(DisposableIncome.isValidDisposableIncome("-91")); // negative integer
        assertFalse(DisposableIncome.isValidDisposableIncome("risk")); // non-numeric
        assertFalse(DisposableIncome.isValidDisposableIncome("9011p041")); // alphabets within digits
        assertFalse(DisposableIncome.isValidDisposableIncome("9312 1534")); // spaces within digits

        // valid phone numbers
        assertTrue(DisposableIncome.isValidDisposableIncome("2432")); // Integer between 1 and 5
        assertTrue(DisposableIncome.isValidDisposableIncome("5327891732")); // Integer between 1 and 5
    }
}
