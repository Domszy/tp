package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasEmail;
import seedu.address.model.person.PersonHasId;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validClientIdUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(2);
        ArrayList<Predicate> predicates = new ArrayList<>();
        ClientId clientId = new ClientId(personToDelete.getClientId().value);
        predicates.add(new PersonHasId(clientId));

        DeleteCommand deleteCommand = new DeleteCommand(predicates);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEmailUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(3);
        ArrayList<Predicate> predicates = new ArrayList<>();
        Email email = new Email(personToDelete.getEmail().value);
        predicates.add(new PersonHasEmail(email));

        DeleteCommand deleteCommand = new DeleteCommand(predicates);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validEmailAndClientIdUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(3);
        ArrayList<Predicate> predicates = new ArrayList<>();
        Email email = new Email(personToDelete.getEmail().value);
        predicates.add(new PersonHasEmail(email));
        ClientId clientId = new ClientId(personToDelete.getClientId().value);
        predicates.add(new PersonHasId(clientId));

        DeleteCommand deleteCommand = new DeleteCommand(predicates);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS, personToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidClientIdUnfilteredList_throwsCommandException() {
        ArrayList<Predicate> predicates = new ArrayList<>();
        ClientId clientId = new ClientId("20");
        predicates.add(new PersonHasId(clientId));
        DeleteCommand deleteCommand = new DeleteCommand(predicates);

        assertCommandFailure(deleteCommand, model, DeleteCommand.MESSAGE_DELETE_PERSON_FAILURE);
    }


    @Test
    public void equals() {
        ArrayList<Predicate> predicatesOne = new ArrayList<>();
        ClientId clientIdOne = new ClientId("1");
        predicatesOne.add(new PersonHasId(clientIdOne));
        DeleteCommand deleteFirstCommand = new DeleteCommand(predicatesOne);
        ArrayList<Predicate> predicatesTwo = new ArrayList<>();
        ClientId clientIdTwo = new ClientId("2");
        predicatesTwo.add(new PersonHasId(clientIdTwo));
        DeleteCommand deleteSecondCommand = new DeleteCommand(predicatesTwo);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        ArrayList<Predicate> predicatesTest = new ArrayList<>();
        ClientId clientIdTest = new ClientId("1");
        predicatesTest.add(new PersonHasId(clientIdTest));
        DeleteCommand deleteFirstCommandCopy = new DeleteCommand(predicatesTest);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

}
