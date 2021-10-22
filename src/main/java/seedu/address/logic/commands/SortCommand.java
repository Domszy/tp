package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;
import seedu.address.model.client.SortByAttribute;

/**
 * Sorts all clients in address book whose according to the specified attribute in either ascending or descending
 * order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts leads according to "
            + "the specified attribute and in either an ascending or descending order\n"
            + "Parameters: <attribute>/{ASC/DESC}\n"
            + "Example: " + COMMAND_WORD + " ra/ asc";

    public static final String MESSAGE_INVALID_PREFIX = "Sorting based on %s is not supported";

    private final SortByAttribute sorter;

    /**
     * @param sorter to sort the clients list with.
     */
    public SortCommand(SortByAttribute sorter) {
        this.sorter = sorter;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.sortFilteredClientList(sorter);
        return new CommandResult(String.format(Messages.MESSAGE_SORT_SUCCESS, sorter.getPrefixName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && sorter.equals(((SortCommand) other).sorter));
    }
}
