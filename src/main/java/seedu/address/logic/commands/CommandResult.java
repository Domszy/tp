package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.CLEAR;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.CREATE_ADDRESSBOOK;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.EXIT;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.NORMAL;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.SHOW_HELP;
import static seedu.address.logic.commands.CommandResult.SpecialCommandResult.SWITCH_ADDRESSBOOK;

import java.util.Objects;

/**
 * Represents the result of a command execution.
 */
public class CommandResult {

    private final String feedbackToUser;

    private final SpecialCommandResult type;
    /**
     * Constructs a {@code CommandResult} with the specified fields.
     */
    public CommandResult(String feedbackToUser, SpecialCommandResult type) {
        this.feedbackToUser = requireNonNull(feedbackToUser);
        this.type = type;
    }

    /**
     * Constructs a {@code CommandResult} with the specified {@code feedbackToUser},
     * and other fields set to their default value.
     */
    public CommandResult(String feedbackToUser) {
        this(feedbackToUser, NORMAL);
    }

    public String getFeedbackToUser() {
        return feedbackToUser;
    }

    public boolean isShowHelp() {
        return type.equals(SHOW_HELP);
    }

    public boolean isExit() {
        return type.equals(EXIT);
    }

    public boolean isSwitchAddressBook() {
        return type.equals(SWITCH_ADDRESSBOOK);
    }

    public boolean isCreateAddressBook() {
        return type.equals(CREATE_ADDRESSBOOK);
    }

    public boolean isClearing() {
        return type.equals(CLEAR);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof CommandResult)) {
            return false;
        }

        CommandResult otherCommandResult = (CommandResult) other;
        return feedbackToUser.equals(otherCommandResult.feedbackToUser)
                && type == otherCommandResult.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(feedbackToUser, type);
    }

    /**
     * Encapsulates the type of CommandResult
     */
    public enum SpecialCommandResult {
        /** Normal command **/
        NORMAL,
        /** Help information should be shown to the user. */
        SHOW_HELP,
        /** The application should exit. */
        EXIT,
        /** The current AddressBook is started to clear */
        CLEAR,
        /** The application is switching AddressBook **/
        SWITCH_ADDRESSBOOK,
        /** The application is creating AddressBook **/
        CREATE_ADDRESSBOOK,
    }
}
