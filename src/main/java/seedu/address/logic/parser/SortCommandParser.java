package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_INVALID_PREFIX;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.anyPrefixesPresent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.mapper.PrefixMapper;
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
     */
    @Override
    public SortCommand parse(String args, Model model) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, ALL_PREFIXES);
        if (!anyPrefixesPresent(argMultimap, ALL_PREFIXES) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        List<Prefix> prefixes = new ArrayList<>();
        List<SortDirection> sortDirections = new ArrayList<>();

        for (Prefix prefix: argMultimap.getPrefixOrdering()) {
            Optional<String> value = argMultimap.getValue(prefix);
            if (value.isEmpty()) {
                continue;
            }

            if (prefix.equals(PREFIX_TAG)) {
                throw new ParseException(String.format(MESSAGE_INVALID_PREFIX, PrefixMapper.getName(PREFIX_TAG)));
            }

            String sortDirectionString = value.get();
            SortDirection sortDirection = ParserUtil.parseSortDirection(sortDirectionString);

            prefixes.add(prefix);
            sortDirections.add(sortDirection);
        }

        SortByAttribute sorter = new SortByAttribute(prefixes, sortDirections);

        return new SortCommand(sorter);
    }
}
