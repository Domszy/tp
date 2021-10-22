package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class SortDirectionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> SortDirection.of(null));
    }

    @Test
    public void constructor_invalidSortDirection_throwsIllegalArgumentException() {
        String invalidSortDirection = "asec";
        assertThrows(IllegalArgumentException.class, () -> SortDirection.of(invalidSortDirection));
    }

    @Test
    public void isValidSortDirection() {
        // null phone number
        assertThrows(NullPointerException.class, () -> SortDirection.isValidDirection(null));

        // invalid directions
        assertFalse(SortDirection.isValidDirection(" ")); // spaces only
        assertFalse(SortDirection.isValidDirection("ascending")); // integer not within 1 and 5
        assertFalse(SortDirection.isValidDirection("213421312312")); // numeric
        assertFalse(SortDirection.isValidDirection("as2c")); // digits within alphabets
        assertFalse(SortDirection.isValidDirection("as c")); // spaces within alphabets

        // valid directions
        assertTrue(SortDirection.isValidDirection("asc")); // Ascending
        assertTrue(SortDirection.isValidDirection("dsc")); // Descending
    }
}
