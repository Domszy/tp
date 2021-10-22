package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class ClientIdTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new ClientId(null));
    }

    @Test
    public void constructor_invalidCurrentPlan_throwsIllegalArgumentException() {
        String invalidClientId = " ";
        assertThrows(IllegalArgumentException.class, () -> new ClientId(invalidClientId));
    }

    @Test
    public void isClientId() {
        // null input
        assertThrows(NullPointerException.class, () -> ClientId.isValidClientId(null));

        // invalid client id
        assertFalse(ClientId.isValidClientId(" ")); // spaces only
        assertFalse(ClientId.isValidClientId("-1")); // negative int

        // valid client id
        assertTrue(ClientId.isValidClientId("0")); // zero
        assertTrue(ClientId.isValidClientId("12345")); // any positive int
    }

    @Test
    public void isEqual() {
        String input1 = "1";
        String input2 = "2";
        ClientId clientA = new ClientId(input1);
        ClientId clientB = new ClientId(input2);
        ClientId clientC = new ClientId(input1);

        // same value
        assertTrue(clientA.equals(clientA));
        assertTrue(clientA.equals(clientC));

        // different value
        assertFalse(clientA.equals(clientB));
    }
}
