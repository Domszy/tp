package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_VIEW_INVALID_CLIENT_ID;
import static seedu.address.commons.core.Messages.MESSAGE_VIEW_SUCCESS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.PersonHasId;

class ViewCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ClientId firstClientId = new ClientId("0");
        ClientId secondClientId = new ClientId("1");

        PersonHasId firstPredicate = new PersonHasId(firstClientId);
        PersonHasId secondPredicate = new PersonHasId(secondClientId);

        ViewCommand viewFirstCommand = new ViewCommand(firstClientId, firstPredicate);
        ViewCommand viewSecondCommand = new ViewCommand(secondClientId, secondPredicate);

        // same object -> returns true
        assertTrue(viewFirstCommand.equals(viewFirstCommand));

        // same values -> returns true
        ViewCommand viewCommandCopy = new ViewCommand(firstClientId, firstPredicate);
        assertTrue(viewFirstCommand.equals(viewCommandCopy));

        // different types -> returns false
        assertFalse(viewFirstCommand.equals(1));

        // null -> returns false
        assertFalse(viewFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(viewFirstCommand.equals(viewSecondCommand));
    }

    @Test
    public void execute_validClientId_success() {
        String userInput = "0";
        String expectedMessage = String.format(MESSAGE_VIEW_SUCCESS, ALICE.getName());
        PersonHasId predicate = preparePredicate(userInput);
        ViewCommand command = new ViewCommand(new ClientId(userInput), predicate);
        expectedModel.updatePersonToView(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE), model.getPersonToView());
    }

    @Test
    public void execute_invalidClientId_failure() {
        String userInput = "10";
        String expectedMessage = String.format(MESSAGE_VIEW_INVALID_CLIENT_ID, userInput);
        PersonHasId predicate = preparePredicate(userInput);
        ViewCommand command = new ViewCommand(new ClientId(userInput), predicate);
        expectedModel.updatePersonToView(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getPersonToView());
    }

    private PersonHasId preparePredicate(String s) {
        return new PersonHasId(new ClientId(s));
    }
}
