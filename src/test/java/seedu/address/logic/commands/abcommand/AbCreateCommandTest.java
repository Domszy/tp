package seedu.address.logic.commands.abcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.CREATE_ADDRESSBOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.abcommand.AbCommand.MESSAGE_CREATE_ADDRESSBOOK_SUCCESS;
import static seedu.address.logic.commands.abcommand.AbCreateCommand.MESSAGE_ADDRESSBOOK_EXISTS;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class AbCreateCommandTest {
    private static final Path TEST_DATA_FOLDER = Path.of("src", "test", "data");

    private static final String addressBookFilePathName = "typicalClientsAddressBook";
    private static final Path userPrefsFilePath = TEST_DATA_FOLDER.resolve(
        Path.of("JsonUserPrefsStorageTest", "TypicalUserPref.json"));
    private static final Path addressBookFilePath = TEST_DATA_FOLDER.resolve(
        Path.of("JsonSerializableAddressBookTest", addressBookFilePathName + ".json"));

    @TempDir
    public Path testFolder;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private final AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);

    private final Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);
    private final Logic logic = new LogicManager(model, storage);

    @Test
    public void equals() {
        String addressBook1 = "addressbook";
        String addressBook2 = "other";

        Path path1 = getPath(addressBook1);
        Path path2 = getPath(addressBook2);

        AbCreateCommand abCreateCommand1 = new AbCreateCommand(addressBook1, path1);
        AbCreateCommand abCreateCommand2 = new AbCreateCommand(addressBook2, path2);

        // same object -> returns true
        assertTrue(abCreateCommand1.equals(abCreateCommand1));

        // same values -> returns true
        AbCreateCommand abCreateCopy = new AbCreateCommand(addressBook1, path1);
        assertTrue(abCreateCommand1.equals(abCreateCopy));

        // different types -> returns false
        assertFalse(abCreateCommand1.equals(1));

        // null -> returns false
        assertFalse(abCreateCommand1.equals(null));

        // different client -> returns false
        assertFalse(abCreateCommand1.equals(abCreateCommand2));
    }

    @Test
    public void execute_success() throws CommandException {
        String newFilePathName = "testingfile";
        Path newFilePath = testFolder.resolve(newFilePathName + ".json");
        AbCreateCommand abCreateCommand1 = new AbCreateCommand(newFilePathName, newFilePath);
        expectedModel.setAddressBookFilePath(newFilePath);
        CommandResult result = new CommandResult(
            String.format(MESSAGE_CREATE_ADDRESSBOOK_SUCCESS, newFilePathName), CREATE_ADDRESSBOOK);
        assertCommandSuccess(abCreateCommand1, model, result, expectedModel);
        logic.createAddressBook();
        assertEquals(logic.getAddressBook(), new AddressBook());
        assertEquals(logic.getAddressBookFilePath(), newFilePath);
    }

    @Test
    public void execute_duplicate_failure() {
        AbCreateCommand abCreateCommand1 = new AbCreateCommand(addressBookFilePathName, addressBookFilePath);
        model.setAddressBookFilePath(addressBookFilePath);
        String result = String.format(MESSAGE_ADDRESSBOOK_EXISTS, addressBookFilePathName);
        assertCommandFailure(abCreateCommand1, model, result);
    }

    private Path getPath(String s) {
        return Path.of(FileUtil.convertToAddressBookPathString(s, this.model.getAddressBookDirectory()));
    }
}
