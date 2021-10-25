package seedu.address.logic.parser.abcommandparser;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.util.FileUtil.convertToAddressBookPathString;
import static seedu.address.commons.util.FileUtil.isValidPath;
import static seedu.address.logic.commands.abcommand.AbCommand.MESSAGE_ADDRESSBOOK_NOT_VALID;

import java.nio.file.Path;

import seedu.address.logic.commands.abcommand.AbDeleteCommand;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;

/**
 * Parses input arguments and create a new AbDeleteCommand object.
 */
public class AbDeleteCommandParser implements Parser<AbDeleteCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AbDeleteCommand
     * and returns an AbDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public AbDeleteCommand parse(String args, Model model) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        String lowercaseArgs = trimmedArgs.toLowerCase();

        if (lowercaseArgs.isBlank()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AbDeleteCommand.MESSAGE_USAGE));
        }

        String filePathName = convertToAddressBookPathString(lowercaseArgs, model.getAddressBookDirectory());

        if (!isValidPath(filePathName)) {
            throw new ParseException(String.format(MESSAGE_ADDRESSBOOK_NOT_VALID, trimmedArgs));
        }

        Path filePath = Path.of(filePathName);

        return new AbDeleteCommand(trimmedArgs, filePath);
    }
}

