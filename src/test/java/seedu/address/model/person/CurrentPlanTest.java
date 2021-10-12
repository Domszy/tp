package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class CurrentPlanTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new CurrentPlan(null));
    }

    @Test
    public void constructor_invalidCurrentPlan_throwsIllegalArgumentException() {
        String invalidCurrentPlan = " ";
        assertThrows(IllegalArgumentException.class, () -> new CurrentPlan(invalidCurrentPlan));
    }

    @Test
    public void isCurrentPlan() {
        // null address
        assertThrows(NullPointerException.class, () -> CurrentPlan.isValidCurrentPlan(null));

        // invalid addresses
        assertFalse(CurrentPlan.isValidCurrentPlan(" ")); // spaces only

        // valid addresses
        assertTrue(CurrentPlan.isValidCurrentPlan("")); // empty string
        assertTrue(CurrentPlan.isValidCurrentPlan("Prudential Proshield")); //Any string
        assertTrue(CurrentPlan.isValidCurrentPlan("-")); // one character
        assertTrue(CurrentPlan.isValidCurrentPlan("Prduential Long String Financial Plan")); // long string
    }
}
