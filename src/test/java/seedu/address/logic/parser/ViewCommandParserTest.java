package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.PersonHasId;

public class ViewCommandParserTest {

    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ViewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsViewCommand() {
        // no leading and trailing whitespaces
        ClientId expectedClientId = new ClientId("0");
        ViewCommand expectedViewCommand =
                new ViewCommand(expectedClientId, new PersonHasId(expectedClientId));
        assertParseSuccess(parser, "0", expectedViewCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, "\n \t 0 \n", expectedViewCommand);
    }
}
