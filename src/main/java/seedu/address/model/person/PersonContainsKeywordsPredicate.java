package seedu.address.model.person;

import static seedu.address.commons.util.StringUtil.containsIgnoreCase;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

import seedu.address.logic.parser.ArgumentMultimap;

/**
 * Tests that a {@code Person}'s attributes matches any of the keywords given.
 */
public class PersonContainsKeywordsPredicate implements Predicate<Person> {
    private final ArgumentMultimap keywords;

    public PersonContainsKeywordsPredicate(ArgumentMultimap keywords) {
        this.keywords = keywords;
    }

    @Override
    public boolean test(Person person) {
        String[] generalKeywords = keywords.getPreamble().split(" ");
        boolean checkGeneral = generalKeywords[0].isBlank() || Arrays.stream(generalKeywords).anyMatch(x -> {
                boolean checkAttribute = Stream.of(person.getName().fullName, person.getPhone().value,
                    person.getEmail().value, person.getAddress().value).anyMatch(y -> containsIgnoreCase(y, x));
                boolean checkAttributeTag = person.getTags().stream()
                    .anyMatch(y -> containsIgnoreCase(y.tagName, x));
                return checkAttribute || checkAttributeTag;
            }
        );

        boolean checkName = keywords.getValue(PREFIX_NAME)
                .map(x -> containsIgnoreCase(person.getName().fullName, x)).orElse(true);
        boolean checkPhone = keywords.getValue(PREFIX_PHONE)
                .map(x -> containsIgnoreCase(person.getPhone().value, x)).orElse(true);
        boolean checkEmail = keywords.getValue(PREFIX_EMAIL)
                .map(x -> containsIgnoreCase(person.getEmail().value, x)).orElse(true);
        boolean checkAddress = keywords.getValue(PREFIX_ADDRESS)
                .map(x -> containsIgnoreCase(person.getAddress().value, x)).orElse(true);
        boolean checkTags = keywords.getValue(PREFIX_TAG)
                .map(x -> person.getTags().stream().anyMatch(y -> containsIgnoreCase(y.tagName, x))).orElse(true);
        return checkGeneral && checkName && checkPhone && checkEmail && checkAddress && checkTags;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonContainsKeywordsPredicate // instanceof handles nulls
                && keywords.equals(((PersonContainsKeywordsPredicate) other).keywords)); // state check
    }

}

