package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.CLEAR;

import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_CLEARED = "Address book has been cleared!";
    public static final String MESSAGE_NOT_CLEARED = "Address book has not been cleared!";
    public static final String MESSAGE_CONFIRMATION_REQUEST = "Are you sure that you wish to clear the"
            + " Address Book (yes/no)";
    public static final String MESSAGE_CONFIRMATION_FAIL = "Please input yes or no";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_CONFIRMATION_REQUEST, CLEAR);
    }
}
