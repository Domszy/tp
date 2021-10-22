package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientHasIdTest {

    @Test
    public void equals() {
        String input1 = "1";
        String input2 = "2";
        ClientId clientA = new ClientId(input1);
        ClientId clientB = new ClientId(input2);

        ClientHasId firstPredicate = new ClientHasId(clientA);
        ClientHasId secondPredicate = new ClientHasId(clientB);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        ClientHasId firstPredicateCopy = new ClientHasId(clientA);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different client -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_clientIdMatches_returnsTrue() {
        // Same client id
        ClientHasId predicate = new ClientHasId(new ClientId("1"));
        assertTrue(predicate.test(new ClientBuilder().withClientId("1").build()));
    }

    @Test
    public void test_clientIdMatches_returnsFalse() {
        // different client id
        ClientHasId predicate = new ClientHasId(new ClientId("0"));
        assertFalse(predicate.test(new ClientBuilder().withClientId("1").build()));
    }
}
