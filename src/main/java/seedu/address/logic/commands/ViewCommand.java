package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.PersonHasId;

public class ViewCommand extends Command {
    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": shows information of the client "
            + "by his/her client id. \n"
            + "Parameters: view {client id of the customer} \n"
            + "Example: " + COMMAND_WORD + " 1 ";

    private final PersonHasId predicate;
    private final ClientId clientId;

    /**
     * @param predicate checking the ClientId of the person to view
     */
    public ViewCommand(ClientId clientId, PersonHasId predicate) {
        requireNonNull(clientId);
        this.clientId = clientId;
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updatePersonToView(predicate);
        if (!model.isPersonExistToView()) {
            return new CommandResult(String.format(Messages.MESSAGE_VIEW_INVALID_CLIENT_ID, clientId.toString()));
        }
        return new CommandResult(String.format(Messages.MESSAGE_VIEW_SUCCESS, model.getNameOfPersonToView()));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewCommand)) {
            return false;
        }

        // state check
        ViewCommand e = (ViewCommand) other;
        return predicate.equals(e.predicate);
    }
}
