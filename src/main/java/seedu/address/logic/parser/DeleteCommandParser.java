package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.anyPrefixesPresent;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.client.ClientId;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {


    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     * @return
     */
    @Override
    public DeleteCommand parse(String args, Model model) throws ParseException {

        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, ALL_PREFIXES);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        //Throws error if other fields are inputted
        if (anyPrefixesPresent(argMultimap, ALL_PREFIXES)) {
            throw new ParseException(String.format(Messages.MESSAGE_TOO_MANY_FIELDS, DeleteCommand.MESSAGE_USAGE));
        }

        List<ClientId> clientIdList = new ArrayList<>();
        for (String s: argMultimap.getPreamble().split(" ")) {
            ClientId clientId = ParserUtil.parseClientId(s);
            clientIdList.add(clientId);
        }

        return new DeleteCommand(clientIdList);
    }

}
