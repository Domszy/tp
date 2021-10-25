package seedu.address.logic.commands.abcommand;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.SWITCH_ADDRESSBOOK;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.abcommand.AbCommand.MESSAGE_SWITCH_ADDRESSBOOK_SUCCESS;
import static seedu.address.logic.commands.abcommand.AbSwitchCommand.MESSAGE_ADDRESSBOOK_NOT_FOUND;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.Logic;
import seedu.address.logic.LogicManager;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.Storage;
import seedu.address.storage.StorageManager;
import seedu.address.storage.UserPrefsStorage;

public class AbSwitchCommandTest {
    private static final Path TEST_DATA_FOLDER = Path.of("src", "test", "data");

    private static final Path userPrefsFilePath = TEST_DATA_FOLDER.resolve(
        Path.of("JsonUserPrefsStorageTest", "TypicalUserPref.json"));

    private static final String addressBookFilePathName = "typicalClientsAddressBook";
    private static final String anotherAddressBookFilePathName = "anotherTypicalClientsAddressBook";

    private static final Path addressBookFilePath = TEST_DATA_FOLDER.resolve(
        Path.of("JsonSerializableAddressBookTest", addressBookFilePathName + ".json"));
    private static final Path anotherAddressBookFilePath = TEST_DATA_FOLDER.resolve(
        Path.of("JsonSerializableAddressBookTest", anotherAddressBookFilePathName + ".json"));

    private final UserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private final AddressBookStorage addressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
    private final Storage storage = new StorageManager(addressBookStorage, userPrefsStorage);
    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Logic logic = new LogicManager(model, storage);

    private final UserPrefsStorage expectedUserPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private final AddressBookStorage expectedAddressBookStorage = new JsonAddressBookStorage(addressBookFilePath);
    private final Storage expectedStorage = new StorageManager(expectedAddressBookStorage, expectedUserPrefsStorage);
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Logic expectedLogic = new LogicManager(expectedModel, expectedStorage);

    private final UserPrefsStorage anotherUserPrefsStorage = new JsonUserPrefsStorage(userPrefsFilePath);
    private final AddressBookStorage anotherAddressBookStorage = new JsonAddressBookStorage(anotherAddressBookFilePath);
    private final Storage anotherStorage = new StorageManager(anotherAddressBookStorage, anotherUserPrefsStorage);
    private final Model anotherModel = new ModelManager(getAddressBookWrapper(anotherStorage), new UserPrefs());
    private final Logic anotherLogic = new LogicManager(anotherModel, anotherStorage);

    @Test
    public void equals() {
        String addressBook1 = "addressbook";
        String addressBook2 = "other";

        Path path1 = getPath(addressBook1);
        Path path2 = getPath(addressBook2);

        AbSwitchCommand abSwitchCommand1 = new AbSwitchCommand(addressBook1, path1);
        AbSwitchCommand abSwitchCommand2 = new AbSwitchCommand(addressBook2, path2);

        // same object -> returns true
        assertTrue(abSwitchCommand1.equals(abSwitchCommand1));

        // same values -> returns true
        AbSwitchCommand abSwitchCommandCopy = new AbSwitchCommand(addressBook1, path1);
        assertTrue(abSwitchCommand1.equals(abSwitchCommandCopy));

        // different types -> returns false
        assertFalse(abSwitchCommand1.equals(1));

        // null -> returns false
        assertFalse(abSwitchCommand1.equals(null));

        // different client -> returns false
        assertFalse(abSwitchCommand1.equals(abSwitchCommand2));
    }

    @Test
    public void execute_success() {
        AbSwitchCommand abSwitchCommand1 = new AbSwitchCommand(
            anotherAddressBookFilePathName, anotherAddressBookFilePath);
        expectedModel.setAddressBookFilePath(anotherAddressBookFilePath);
        CommandResult result = new CommandResult(
            String.format(MESSAGE_SWITCH_ADDRESSBOOK_SUCCESS, anotherAddressBookFilePathName), SWITCH_ADDRESSBOOK);
        assertCommandSuccess(abSwitchCommand1, model, result, expectedModel);
        logic.switchAddressBook();
        assertEquals(logic.getAddressBook(), anotherLogic.getAddressBook());
    }

    @Test
    public void execute_failure() {
        String invalidFilePathName = "jjjjj";
        Path invalidFilePath = TEST_DATA_FOLDER.resolve(invalidFilePathName + ".json");
        AbSwitchCommand abSwitchCommand1 = new AbSwitchCommand(invalidFilePathName, invalidFilePath);
        model.setAddressBookFilePath(addressBookFilePath);
        String result = String.format(MESSAGE_ADDRESSBOOK_NOT_FOUND, invalidFilePathName);
        assertCommandFailure(abSwitchCommand1, model, result);
        logic.switchAddressBook();
        assertEquals(logic.getAddressBook(), expectedLogic.getAddressBook());
    }

    private Path getPath(String s) {
        return Path.of(FileUtil.convertToAddressBookPathString(s, this.model.getAddressBookDirectory()));
    }

    private ReadOnlyAddressBook getAddressBookWrapper(Storage storage) {
        try {
            return storage.readAddressBook().get();
        } catch (DataConversionException | IOException e) {
            e.printStackTrace();
        }
        return getTypicalAddressBook();
    }
}
