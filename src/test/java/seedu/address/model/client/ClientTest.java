package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENTPLAN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DISPOSABLEINCOME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LASTMET_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_RISKAPPETITE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.ClientBuilder;

public class ClientTest {

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Client client = new ClientBuilder().build();
        assertThrows(UnsupportedOperationException.class, () -> client.getTags().remove(0));
    }

    @Test
    public void isSameClient() {
        // same object -> returns true
        assertTrue(ALICE.isSameClient(ALICE));

        // null -> returns false
        assertFalse(ALICE.isSameClient(null));

        // same email and client ID, all other attributes different -> returns true
        Client editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB)
                .withAddress(VALID_ADDRESS_BOB).withRiskAppetite(VALID_RISKAPPETITE_BOB)
                .withDisposableIncome(VALID_DISPOSABLEINCOME_BOB).withCurrentPlan(VALID_CURRENTPLAN_BOB)
                .withLastMet(VALID_LASTMET_BOB).withTags(VALID_TAG_HUSBAND).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // different client id, all other attributes same -> returns true
        editedAlice = new ClientBuilder(ALICE).withClientId(VALID_CLIENTID_BOB).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // different email, all other attributes same -> returns true
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertTrue(ALICE.isSameClient(editedAlice));

        // email and name differs in case, all other attributes same -> returns false
        Client editedBob = new ClientBuilder(BOB).withEmail(VALID_EMAIL_BOB.toUpperCase()).withName("Not Bob").build();
        assertFalse(BOB.isSameClient(editedBob));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Client aliceCopy = new ClientBuilder(ALICE).build();
        assertTrue(ALICE.equals(aliceCopy));

        // same object -> returns true
        assertTrue(ALICE.equals(ALICE));

        // null -> returns false
        assertFalse(ALICE.equals(null));

        // different type -> returns false
        assertFalse(ALICE.equals(5));

        // different client -> returns false
        assertFalse(ALICE.equals(BOB));

        // different name -> returns false
        Client editedAlice = new ClientBuilder(ALICE).withName(VALID_NAME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new ClientBuilder(ALICE).withPhone(VALID_PHONE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different email -> returns false
        editedAlice = new ClientBuilder(ALICE).withEmail(VALID_EMAIL_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different address -> returns false
        editedAlice = new ClientBuilder(ALICE).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different risk appetite -> returns false
        editedAlice = new ClientBuilder(ALICE).withRiskAppetite(VALID_RISKAPPETITE_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different disposable income -> returns false
        editedAlice = new ClientBuilder(ALICE).withDisposableIncome(VALID_DISPOSABLEINCOME_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different current plan -> returns false
        editedAlice = new ClientBuilder(ALICE).withCurrentPlan(VALID_CURRENTPLAN_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different last met-> returns false
        editedAlice = new ClientBuilder(ALICE).withLastMet(VALID_LASTMET_BOB).build();
        assertFalse(ALICE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ClientBuilder(ALICE).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(ALICE.equals(editedAlice));
    }
}
