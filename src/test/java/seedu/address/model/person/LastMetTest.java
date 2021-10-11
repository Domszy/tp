package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class LastMetTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new LastMet(null));
    }

    @Test
    public void constructor_invalidLastMet_throwsIllegalArgumentException() {
        String invalidLastMet = "23-09-2021";
        assertThrows(IllegalArgumentException.class, () -> new LastMet(invalidLastMet));
    }

    @Test
    public void isValidLastMet() {
        // null email
        assertThrows(NullPointerException.class, () -> LastMet.isValidLastMet(null));

        // blank email
        assertFalse(LastMet.isValidLastMet(" ")); // empty string

        // missing parts
        assertFalse(LastMet.isValidLastMet("20-30")); // missing local part

        // invalid parts
        assertFalse(LastMet.isValidLastMet("20-50-5050")); // invalid domain name
        assertFalse(LastMet.isValidLastMet("603-08-20"));

        // valid email
        assertTrue(LastMet.isValidLastMet("2021-09-21"));
        assertTrue(LastMet.isValidLastMet("2021-12-30"));
        assertTrue(LastMet.isValidLastMet("2021-03-04"));
    }
}
