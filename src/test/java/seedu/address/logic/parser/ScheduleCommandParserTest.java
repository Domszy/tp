package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ScheduleCommand;

class ScheduleCommandParserTest {

    private ScheduleCommandParser parser = new ScheduleCommandParser();

    @Test
    public void parse_validDate_returnsScheduleCommand() {
        LocalDate meetingDate = LocalDate.of(2022, 9, 10);
        assertParseSuccess(parser, " 10-09-2022", new ScheduleCommand(meetingDate));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //invalid date format
        assertParseFailure(parser, " 2022-09-31", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ScheduleCommand.MESSAGE_INVALID_DATE_FAILURE));
    }

    @Test
    public void parse_emptyString_throwsParseException() {
        //empty string input
        assertParseSuccess(parser, " ", new ScheduleCommand(null));
    }
}
