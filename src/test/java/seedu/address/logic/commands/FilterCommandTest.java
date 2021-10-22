package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CLIENTS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.ELLE;
import static seedu.address.testutil.TypicalClients.FIONA;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.ClientContainsKeywordsPredicate;

public class FilterCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        ClientContainsKeywordsPredicate firstPredicate =
                new ClientContainsKeywordsPredicate(
                        ArgumentTokenizer.tokenize("abc def a/Blk 30 e/example.com"));
        ClientContainsKeywordsPredicate secondPredicate =
                new ClientContainsKeywordsPredicate(ArgumentTokenizer.tokenize("abc a/Blk 30"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand findFilterCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFilterCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different client -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noClientFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 0);
        ClientContainsKeywordsPredicate predicate = preparePredicate("* e/not_example.com");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.filterFilteredClientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredClientList());
    }

    @Test
    public void execute_multipleKeywords_multipleClientsFound() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 3);
        ClientContainsKeywordsPredicate predicate = preparePredicate("Pauline Kurz Elle Kunz p/94 e/example.com");
        FilterCommand command = new FilterCommand(predicate);
        expectedModel.filterFilteredClientList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE, FIONA), model.getFilteredClientList());
    }

    @Test
    public void execute_multipleFilter() {
        String expectedMessage = String.format(MESSAGE_CLIENTS_LISTED_OVERVIEW, 2);
        ClientContainsKeywordsPredicate predicate1 = preparePredicate(" Pauline Kurz Elle Kunz p/94 e/example.com");
        ClientContainsKeywordsPredicate predicate2 = preparePredicate(" a/ave");
        FilterCommand command1 = new FilterCommand(predicate1);
        FilterCommand command2 = new FilterCommand(predicate2);
        expectedModel.filterFilteredClientList(predicate1);
        expectedModel.filterFilteredClientList(predicate2);
        command1.execute(model);
        assertCommandSuccess(command2, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ALICE, ELLE), model.getFilteredClientList());
    }

    private ClientContainsKeywordsPredicate preparePredicate(String s) {
        ArgumentMultimap aMM = ArgumentTokenizer.tokenize(s,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        return new ClientContainsKeywordsPredicate(aMM);
    }
}
