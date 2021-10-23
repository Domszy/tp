package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_CLEARED;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_CONFIRMATION_REQUEST;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_NOT_CLEARED;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.CLEAR;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class ClearCommandTest {
    @TempDir
    public Path testFolder;

    private final Model model1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel1 = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model model2 = new ModelManager();

    private Logic logic1;
    private Logic logic2;

    @BeforeEach
    public void testHelper() {
        UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(testFolder.resolve("TempAddressBook.json"));
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(testFolder.resolve("TempUserPrefs.json"));

        logic1 = new LogicManager(model1, new StorageManager(addressBookStorage, userPrefsStorage));
        logic2 = new LogicManager(model2, new StorageManager(addressBookStorage, userPrefsStorage));
    }

    @Test
    public void execute_nonEmptyAddressBook_success() throws CommandException, ParseException {
        CommandResult commandResult1 = new CommandResult(MESSAGE_CONFIRMATION_REQUEST, CLEAR);
        assertCommandSuccess(new ClearCommand(), model1, commandResult1, expectedModel1);
        CommandResult commandResult2 = new CommandResult(MESSAGE_CLEARED);
        assertEquals(commandResult2, logic1.clearExecute("yes"));
        assertEquals(model1, new ModelManager());
    }

    @Test
    public void execute_emptyAddressBook_success() throws CommandException, ParseException {
        CommandResult commandResult1 = new CommandResult(MESSAGE_CONFIRMATION_REQUEST, CLEAR);
        assertCommandSuccess(new ClearCommand(), model2, commandResult1, new ModelManager());
        CommandResult commandResult2 = new CommandResult(MESSAGE_CLEARED);
        assertEquals(commandResult2, logic2.clearExecute("yes"));
        assertEquals(model2, new ModelManager());
    }

    @Test
    public void execute_nonEmptyAddressBook_successNoClear() throws CommandException, ParseException {
        CommandResult commandResult1 = new CommandResult(MESSAGE_CONFIRMATION_REQUEST, CLEAR);
        assertCommandSuccess(new ClearCommand(), model1, commandResult1, expectedModel1);
        CommandResult commandResult2 = new CommandResult(MESSAGE_NOT_CLEARED);
        assertEquals(commandResult2, logic1.clearExecute("no"));
        assertEquals(model1, expectedModel1);
    }
}
