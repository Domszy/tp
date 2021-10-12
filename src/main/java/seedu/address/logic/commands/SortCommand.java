package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.model.Model;

/**
 * Sorts all persons in address book whose according to the specified attribute in either ascending or descending
 * order.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts leads according to "
            + "the specified attribute and in either an ascending or descending order\n"
            + "Parameters: <attribute>/{ASC/DESC}\n"
            + "Example: " + COMMAND_WORD + " ra/ ASC";



    public SortCommand() {

    }

    @Override
    public CommandResult execute(Model model) {


        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || other instanceof SortCommand; // instanceof handles nulls
                //&& predicate.equals(((SortCommand) other).predicate)); // state check
    }
}
