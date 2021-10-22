package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;

/**
 * Deletes a client identified using it's displayed index from the address book.
 */
public class ScheduleCommand extends Command {

    public static final String COMMAND_WORD = "schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": showcases the meetings that the financial advisor has on the day. \n"
        + "Parameters: Day-Month-Year \n"
        + "Example: " + COMMAND_WORD + " 01-01-2021";

    public static final String MESSAGE_SCHEDULE_SUCCESS = "Found Schedule for %1$s";
    public static final String MESSAGE_INVALID_DATE_FAILURE = "Please input a date in the format of Day-Month-Year.";
    public static final String MESSAGE_NO_SCHEDULE_ON_DATE_SUCCESS = "No meetings on the day!";

    private final LocalDate scheduleDate;

    public ScheduleCommand(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // there is no events schedule for the day
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        List<Client> clientsWithMeetings = model.retrieveSchedule(scheduleDate);

        if (clientsWithMeetings.size() == 0) {
            return new CommandResult(String.format(MESSAGE_NO_SCHEDULE_ON_DATE_SUCCESS));
        }

        return new CommandResult(String.format(Messages.MESSAGE_SCHEDULE_SUCCESS, formatter.format(scheduleDate)));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof ScheduleCommand // instanceof handles nulls
            && scheduleDate.equals(((ScheduleCommand) other).scheduleDate)); // state check
    }
}
