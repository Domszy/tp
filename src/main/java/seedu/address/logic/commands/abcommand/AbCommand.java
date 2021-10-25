package seedu.address.logic.commands.abcommand;

import seedu.address.logic.commands.Command;

public abstract class AbCommand extends Command {
    public static final String COMMAND_WORD = "ab";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Commands related to usage of Address Book\n"
            + "- " + COMMAND_WORD + " create\n"
            + "- " + COMMAND_WORD + " switch\n"
            + "- " + COMMAND_WORD + " list\n";

    public static final String MESSAGE_SWITCH_ADDRESSBOOK_SUCCESS = "Address Book switch to: %1$s";
    public static final String MESSAGE_CREATE_ADDRESSBOOK_SUCCESS = "Created Address Book with name: %1$s";
    public static final String MESSAGE_DELETE_ADDRESSBOOK_SUCCESS = "Deleted Address Book with name: %1$s";
    public static final String MESSAGE_DELETE_ADDRESSBOOK_FAILURE = "Failed to delete Address Book with name: %1$s";
    public static final String MESSAGE_ADDRESSBOOK_NOT_VALID = "This is not a valid Address Book name: %1$s";


}
