package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_NONEXISTENT_CLIENT_ID;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validClientIdUnfilteredList_success() {
        Client clientToDelete = model.getFilteredClientList().get(2);
        ClientId clientId = new ClientId(clientToDelete.getClientId().value);

        DeleteCommand deleteCommand = new DeleteCommand(List.of(clientId));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_CLIENT_SUCCESS, clientToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.removeAllClients(List.of(clientId));

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIdUnfilteredList_throwsCommandException() {
        ClientId clientId = new ClientId("20");
        DeleteCommand deleteCommand = new DeleteCommand(List.of(clientId));

        assertCommandFailure(deleteCommand, model, String.format(MESSAGE_NONEXISTENT_CLIENT_ID, "20"));
    }


    @Test
    public void equals() {

        ClientId clientIdOne = new ClientId("1");
        DeleteCommand deleteFirstCommand = new DeleteCommand(List.of(clientIdOne));

        ClientId clientIdTwo = new ClientId("2");
        DeleteCommand deleteSecondCommand = new DeleteCommand(List.of(clientIdTwo));

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ClientId clientIdTest = new ClientId("1");
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(List.of(clientIdTest));
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
