package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_FIELDS_EMPTY;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.mapper.PrefixMapper;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.client.ClientContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SearchCommand
     * and returns a SearchCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     * @return
     */
    @Override
    public SearchCommand parse(String args, Model model) throws ParseException {
        String trimmedArgs = args.trim().replaceAll("\\s+", " ");
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        // appends " " in front as Search Command can accept arguments without a preamble
        String preparedArgs = " ".concat(trimmedArgs);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(preparedArgs, ALL_PREFIXES);

        List<String> emptyInputPrefixes = Arrays.stream(ALL_PREFIXES)
                .filter(prefix -> argMultimap.getValue(prefix)
                        .map(String::isBlank)
                        .orElse(false))
                .map(PrefixMapper::getName)
                .collect(Collectors.toList());

        if (!emptyInputPrefixes.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_FIELDS_EMPTY,
                    StringUtil.joinListToString(emptyInputPrefixes, StringUtil.COMMA_DELIMITER)));
        }

        return new SearchCommand(new ClientContainsKeywordsPredicate(argMultimap));
    }

}
