package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ScheduleCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validLocalDate_success() {
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SCHEDULE_SUCCESS, "24 Nov 2022");
        LocalDate scheduleDate = LocalDate.of(2022, 11, 24);
        ScheduleCommand command = new ScheduleCommand(scheduleDate);
        expectedModel.filterSortedNextMeetingList(scheduleDate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getSortedNextMeetingList(), expectedModel.getSortedNextMeetingList());
    }

    @Test
    public void execute_noMeetings_success() {
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_NO_SCHEDULE_ON_DATE_SUCCESS);
        LocalDate scheduleDate = LocalDate.of(2022, 11, 20);
        ScheduleCommand command = new ScheduleCommand(scheduleDate);
        expectedModel.filterSortedNextMeetingList(scheduleDate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getSortedNextMeetingList(), expectedModel.getSortedNextMeetingList());
    }

    @Test
    public void execute_showcaseAllMeetings_success() {
        String expectedMessage = String.format(ScheduleCommand.MESSAGE_SHOW_ALL_MEETINGS_SUCCESS);
        ScheduleCommand command = new ScheduleCommand(null);
        expectedModel.filterSortedNextMeetingList(null);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(model.getSortedNextMeetingList(), expectedModel.getSortedNextMeetingList());
    }
}
