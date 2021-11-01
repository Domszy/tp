package seedu.address.logic.commands;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
    public static final String MESSAGE_SHOW_ALL_MEETINGS_SUCCESS = "Showing all meetings.";

    private final LocalDate scheduleDate;

    public ScheduleCommand(LocalDate scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (scheduleDate == null) {
            model.filterSortedNextMeetingList(null);
            return new CommandResult(MESSAGE_SHOW_ALL_MEETINGS_SUCCESS);
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        List<Client> clientsWithMeetings = model.retrieveSchedule(scheduleDate);

        model.filterSortedNextMeetingList(scheduleDate);
        // there is no meetings schedule for the day
        if (clientsWithMeetings.size() == 0) {
            return new CommandResult(String.format(MESSAGE_NO_SCHEDULE_ON_DATE_SUCCESS));
        }

        return new CommandResult(String.format(MESSAGE_SCHEDULE_SUCCESS, formatter.format(scheduleDate)));
    }

    public LocalDate getScheduleDate() {
        return scheduleDate;
    }

    @Override
    public boolean equals(Object other) {
        if (scheduleDate == null) {
            ScheduleCommand comparison = (ScheduleCommand) other;
            LocalDate comparisonDate = comparison.getScheduleDate();
            return (comparisonDate == scheduleDate);
        }
        return other == this // short circuit if same object
            || (other instanceof ScheduleCommand // instanceof handles nulls
            && scheduleDate.equals(((ScheduleCommand) other).scheduleDate)); // state check
    }
}
