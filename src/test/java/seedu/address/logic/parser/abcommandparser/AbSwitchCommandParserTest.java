package seedu.address.logic.parser.abcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.abcommand.AbSwitchCommand;

public class AbSwitchCommandParserTest {
    private final AbSwitchCommandParser parser = new AbSwitchCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AbSwitchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArg_returnAbSwitchCommand() {
        String input = "addressbook";
        Path filePath = Path.of(FileUtil.convertToAddressBookPathString(input, Path.of("data")));
        AbSwitchCommand expectedAbSwitchCommand = new AbSwitchCommand(input, filePath);
        assertParseSuccess(parser, " " + input + "  ", expectedAbSwitchCommand);
    }
}
