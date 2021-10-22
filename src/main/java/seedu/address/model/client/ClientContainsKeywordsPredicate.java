package seedu.address.model.client;

import static seedu.address.commons.mapper.PrefixMapper.getAttributeFunction;
import static seedu.address.commons.util.StringUtil.containsStringIgnoreCase;
import static seedu.address.logic.parser.CliSyntax.ALL_PREFIXES;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code Client}'s attributes matches any of the keywords given.
 */
public class ClientContainsKeywordsPredicate implements Predicate<Client> {
    private final ArgumentMultimap keywords;

    public ClientContainsKeywordsPredicate(ArgumentMultimap keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Client client) {
        String[] generalKeywords = keywords.getPreamble().split(" ");
        boolean checkGeneral = generalKeywords[0].isBlank() || Arrays.stream(generalKeywords).anyMatch(x ->
                Arrays.stream(ALL_PREFIXES).anyMatch(prefix -> {
                    Function<Client, String> getAttribute = getAttributeFunction(prefix).andThen(Object::toString);
                    String clientAttribute = getAttribute.apply(client);
                    return containsStringIgnoreCase(clientAttribute, x);
                })
        );

        boolean checkAttributes = Arrays
                .stream(ALL_PREFIXES)
                .map(prefix -> {
                    Function<Client, String> getAttribute = getAttributeFunction(prefix).andThen(Object::toString);
                    String clientAttribute = getAttribute.apply(client);
                    Optional<String> keyword = keywords.getValue(prefix);
                    return keyword.map(x -> containsStringIgnoreCase(clientAttribute, x)).orElse(true);
                })
                .reduce(true, Boolean::logicalAnd);

        return checkGeneral && checkAttributes;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ClientContainsKeywordsPredicate) other).keywords)); // state check
    }

}

