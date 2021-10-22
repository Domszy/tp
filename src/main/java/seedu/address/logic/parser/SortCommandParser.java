package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_TOO_MANY_FIELDS;
import static seedu.address.commons.mapper.PrefixMapper.getName;
import static seedu.address.logic.commands.SortCommand.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.anyPrefixesPresent;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.client.SortByAttribute;
import seedu.address.model.client.SortDirection;

public class SortCommandParser implements Parser<SortCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a SortCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     * @return
     */
    @Override
    public SortCommand parse(String args, Model model) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        if (!anyPrefixesPresent(argMultimap, ALL_PREFIXES) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        Prefix p = getPrefix(argMultimap, ALL_PREFIXES);

        SortDirection sortDirection = ParserUtil.parseSortDirection(argMultimap.getValue(p).get());
        SortByAttribute sorter = new SortByAttribute(p, sortDirection);

        return new SortCommand(sorter);
    }

    /**
     * Returns the prefix from the given {@code ArgumentMultiMap} and throws {@code ParseException}
     * if there is more than one prefix is present.
     */
    private static Prefix getPrefix(ArgumentMultimap argumentMultimap, Prefix... prefixes) throws ParseException {
        List<Prefix> resultList = Stream.of(prefixes).filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
            .collect(Collectors.toList());

        if (resultList.size() > 1) {
            throw new ParseException(String.format(MESSAGE_TOO_MANY_FIELDS, SortCommand.MESSAGE_USAGE));
        }

        Prefix result = resultList.get(0);
        if (result.equals(PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_PREFIX, getName(result)));
        }

        return result;
    }
}
