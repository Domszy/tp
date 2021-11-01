package seedu.address.model.client;

import static seedu.address.commons.mapper.PrefixMapper.getAttributeFunction;
import static seedu.address.commons.util.StringUtil.containsStringIgnoreCase;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.allPrefixLess;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;

import seedu.address.commons.mapper.PrefixMapper;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.model.tag.Tag;

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
        boolean checkGeneral = generalKeywords[0].isBlank() || Arrays.stream(generalKeywords).anyMatch(keyword -> {
            boolean checkAttributesLessTag = Arrays.stream(allPrefixLess(PREFIX_TAG))
                    .map(PrefixMapper::getAttributeFunction)
                    .map(x -> x.apply(client))
                    .map(Object::toString)
                    .anyMatch(clientAttribute -> containsStringIgnoreCase(clientAttribute, keyword));

            boolean checkTags = client.getTags().stream()
                    .map(Tag::getName)
                    .anyMatch(tagName -> containsStringIgnoreCase(tagName, keyword));

            return checkAttributesLessTag || checkTags;
        });

        boolean checkAttributes = Arrays.stream(allPrefixLess(PREFIX_TAG))
                .map(prefix -> {
                    Function<Client, String> getAttribute = getAttributeFunction(prefix).andThen(Object::toString);
                    String clientAttribute = getAttribute.apply(client);
                    Optional<String> keyword = keywords.getValue(prefix);
                    return keyword.map(x -> containsStringIgnoreCase(clientAttribute, x)).orElse(true);
                })
                .reduce(true, Boolean::logicalAnd);

        boolean checkTags = keywords.getValue(PREFIX_TAG)
                .map(tagKeyword -> client.getTags().stream()
                        .map(Tag::getName)
                        .anyMatch(tagName -> containsStringIgnoreCase(tagName, tagKeyword)))
                .orElse(true);

        return checkGeneral && checkAttributes && checkTags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((ClientContainsKeywordsPredicate) other).keywords)); // state check
    }

}

