package seedu.address.logic.parser.abcommandparser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.abcommand.AbCreateCommand;
import seedu.address.logic.commands.abcommand.AbDeleteCommand;
import seedu.address.logic.commands.abcommand.AbListCommand;
import seedu.address.logic.commands.abcommand.AbSwitchCommand;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;

public class AbCommandParserTest {
    private final AbCommandParser abCommandParser = new AbCommandParser();
    private final Model model = new ModelManager();

    @Test
    public void parseCommand_abcreate() throws Exception {
        String input = "addressbook";
        String filePathName = FileUtil.convertToAddressBookPathString(input, model.getAddressBookDirectory());
        Path pathName = Path.of(filePathName);
        AbCreateCommand command = (AbCreateCommand) abCommandParser.parse(
                AbCreateCommand.COMMAND_WORD + " " + input, model);
        assertEquals(new AbCreateCommand(input, pathName), command);
    }

    @Test
    public void parseCommand_abdelete() throws Exception {
        String input = "addressbook";
        String filePathName = FileUtil.convertToAddressBookPathString(input, model.getAddressBookDirectory());
        Path pathName = Path.of(filePathName);
        AbDeleteCommand command = (AbDeleteCommand) abCommandParser.parse(
                AbDeleteCommand.COMMAND_WORD + " " + input, model);
        assertEquals(new AbDeleteCommand(input, pathName), command);
    }

    @Test
    public void parseCommand_abswitch() throws Exception {
        String input = "addressbook";
        String filePathName = FileUtil.convertToAddressBookPathString(input, model.getAddressBookDirectory());
        Path pathName = Path.of(filePathName);
        AbSwitchCommand command = (AbSwitchCommand) abCommandParser.parse(
                AbSwitchCommand.COMMAND_WORD + " " + input, model);
        assertEquals(new AbSwitchCommand(input, pathName), command);
    }

    @Test
    public void parseCommand_ablist() throws Exception {
        assertTrue(abCommandParser.parse(AbListCommand.COMMAND_WORD, model) instanceof AbListCommand);
    }
}
