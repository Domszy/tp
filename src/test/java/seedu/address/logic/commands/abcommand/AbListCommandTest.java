package seedu.address.logic.commands.abcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class AbListCommandTest {
    @Test
    public void equals() {
        AbListCommand abListCommand1 = new AbListCommand();
        AbListCommand abListCommand2 = new AbListCommand();

        // same object -> returns true
        assertTrue(abListCommand1.equals(abListCommand1));

        // different types -> returns false
        assertFalse(abListCommand1.equals(1));

        // null -> returns false
        assertFalse(abListCommand1.equals(null));

        // different client -> returns false
        assertFalse(abListCommand1.equals(abListCommand2));
    }
}
