package seedu.address.logic.parser.abcommandparser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.logic.commands.abcommand.AbCommand;
import seedu.address.logic.commands.abcommand.AbCreateCommand;
import seedu.address.logic.commands.abcommand.AbDeleteCommand;
import seedu.address.logic.commands.abcommand.AbListCommand;
import seedu.address.logic.commands.abcommand.AbSwitchCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

public class AbCommandParser implements Parser<AbCommand> {
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");


    /**
     * Parses the given {@code String} of arguments in the context of the AbCommand
     * and returns an AbCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AbCommand parse(String args, Model model) throws ParseException {
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(args.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");
        switch (commandWord) {

        case AbSwitchCommand.COMMAND_WORD:
            return new AbSwitchCommandParser().parse(arguments, model);

        case AbCreateCommand.COMMAND_WORD:
            return new AbCreateCommandParser().parse(arguments, model);

        case AbDeleteCommand.COMMAND_WORD:
            return new AbDeleteCommandParser().parse(arguments, model);

        case AbListCommand.COMMAND_WORD:
            return new AbListCommand();

        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbCommand.MESSAGE_USAGE));
        }
    }
}
