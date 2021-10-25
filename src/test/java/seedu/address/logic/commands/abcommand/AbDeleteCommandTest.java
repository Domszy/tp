package seedu.address.logic.commands.abcommand;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.abcommand.AbCommand.MESSAGE_DELETE_ADDRESSBOOK_SUCCESS;
import static seedu.address.logic.commands.abcommand.AbDeleteCommand.MESSAGE_ADDRESSBOOK_DELETE_CURRENT;
import static seedu.address.logic.commands.abcommand.AbDeleteCommand.MESSAGE_ADDRESSBOOK_DOES_NOT_EXISTS;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class AbDeleteCommandTest {
    @TempDir
    public Path testFolder;

    private final Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        String addressBook1 = "addressbook";
        String addressBook2 = "other";

        Path path1 = getPath(addressBook1);
        Path path2 = getPath(addressBook2);

        AbDeleteCommand abDeleteCommand1 = new AbDeleteCommand(addressBook1, path1);
        AbDeleteCommand abDeleteCommand2 = new AbDeleteCommand(addressBook2, path2);

        // same object -> returns true
        assertTrue(abDeleteCommand1.equals(abDeleteCommand1));

        // same values -> returns true
        AbDeleteCommand abDeleteCopy = new AbDeleteCommand(addressBook1, path1);
        assertTrue(abDeleteCommand1.equals(abDeleteCopy));

        // different types -> returns false
        assertFalse(abDeleteCommand1.equals(1));

        // null -> returns false
        assertFalse(abDeleteCommand1.equals(null));

        // different client -> returns false
        assertFalse(abDeleteCommand1.equals(abDeleteCommand2));
    }

    @Test
    public void execute_success() throws IOException {
        String newFilePathName = "testingfile";
        Path newFilePath = testFolder.resolve(newFilePathName + ".json");
        File file = newFilePath.toFile();
        file.createNewFile();
        AbDeleteCommand abDeleteCommand = new AbDeleteCommand(newFilePathName, newFilePath);
        CommandResult result = new CommandResult(String.format(MESSAGE_DELETE_ADDRESSBOOK_SUCCESS, newFilePathName));
        assertCommandSuccess(abDeleteCommand, model, result, expectedModel);
        assertFalse(file.exists());
    }

    @Test
    public void execute_failure_doesNotExist() {
        String newFilePathName = "testingfile";
        Path newFilePath = testFolder.resolve(newFilePathName + ".json");
        AbDeleteCommand abDeleteCommand1 = new AbDeleteCommand(newFilePathName, newFilePath);
        String result = String.format(MESSAGE_ADDRESSBOOK_DOES_NOT_EXISTS, newFilePathName);
        assertCommandFailure(abDeleteCommand1, model, result);
    }

    @Test
    public void execute_failure_currentAddressBook() throws IOException {
        String newFilePathName = "testingfile";
        Path newFilePath = testFolder.resolve(newFilePathName + ".json");
        newFilePath.toFile().createNewFile();
        model.setAddressBookFilePath(newFilePath);
        AbDeleteCommand abDeleteCommand1 = new AbDeleteCommand(newFilePathName, newFilePath);
        String result = String.format(MESSAGE_ADDRESSBOOK_DELETE_CURRENT, newFilePathName);
        assertCommandFailure(abDeleteCommand1, model, result);
    }

    private Path getPath(String s) {
        return Path.of(FileUtil.convertToAddressBookPathString(s, this.model.getAddressBookDirectory()));
    }
}
