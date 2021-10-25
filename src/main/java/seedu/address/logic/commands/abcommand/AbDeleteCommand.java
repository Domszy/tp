package seedu.address.logic.commands.abcommand;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AbDeleteCommand extends AbCommand {
    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Delete an existing address books\n"
            + "Parameters: " + AbCommand.COMMAND_WORD + " " + COMMAND_WORD + " {name of the addressbook}\n"
            + "Example: " + AbCommand.COMMAND_WORD + " " + COMMAND_WORD + " addressbook2";

    public static final String MESSAGE_ADDRESSBOOK_DOES_NOT_EXISTS =
            "Address Book with this name does not exists: %s";

    public static final String MESSAGE_ADDRESSBOOK_DELETE_CURRENT =
            "Cannot delete current Address Book: %s, switch to a different Address Book first.";

    private final String addressBookName;
    private final Path filePath;

    /**
     * @param addressBookName name of the addressbook to delete.
     * @param filePath the filepath to the address book to delete
     */
    public AbDeleteCommand(String addressBookName, Path filePath) {
        this.addressBookName = addressBookName;
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (model.getAddressBookFilePath().equals(filePath)) {
            throw new CommandException(String.format(MESSAGE_ADDRESSBOOK_DELETE_CURRENT, addressBookName));
        }

        boolean isDeleted;
        try {
            isDeleted = Files.deleteIfExists(filePath);
        } catch (IOException e) {
            throw new CommandException(String.format(MESSAGE_DELETE_ADDRESSBOOK_FAILURE, addressBookName));
        }

        if (!isDeleted) {
            throw new CommandException(String.format(MESSAGE_ADDRESSBOOK_DOES_NOT_EXISTS, addressBookName));
        }

        model.deleteAddressBookList(filePath);
        return new CommandResult(String.format(MESSAGE_DELETE_ADDRESSBOOK_SUCCESS, addressBookName));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AbDeleteCommand)) {
            return false;
        }

        AbDeleteCommand otherCommand = (AbDeleteCommand) other;
        return this.addressBookName.equals(otherCommand.addressBookName)
                && this.filePath.equals(otherCommand.filePath);
    }
}
