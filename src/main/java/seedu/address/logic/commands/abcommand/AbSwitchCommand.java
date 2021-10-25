package seedu.address.logic.commands.abcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.FileUtil.isFileExists;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.SWITCH_ADDRESSBOOK;

import java.nio.file.Path;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AbSwitchCommand extends AbCommand {
    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switch between different address books\n"
            + "Parameters: " + AbCommand.COMMAND_WORD + " " + COMMAND_WORD + " {name of the addressbook}\n"
            + "Example: " + AbCommand.COMMAND_WORD + " " + COMMAND_WORD + " addressbook2";

    public static final String MESSAGE_ADDRESSBOOK_NOT_FOUND = "Address Book with this name not found: %s";

    private final String addressBookName;
    private final Path filePath;

    /**
     * @param addressBookName name of the addressbook to switch to.
     * @param filePath the filepath to the address book to switch to
     */
    public AbSwitchCommand(String addressBookName, Path filePath) {
        this.addressBookName = addressBookName;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!isFileExists(filePath)) {
            throw new CommandException(String.format(MESSAGE_ADDRESSBOOK_NOT_FOUND, addressBookName));
        }

        model.setAddressBookFilePath(filePath);

        return new CommandResult(
                String.format(MESSAGE_SWITCH_ADDRESSBOOK_SUCCESS, addressBookName), SWITCH_ADDRESSBOOK);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AbSwitchCommand)) {
            return false;
        }

        AbSwitchCommand otherCommand = (AbSwitchCommand) other;
        return this.addressBookName.equals(otherCommand.addressBookName)
                && this.filePath.equals(otherCommand.filePath);
    }
}
