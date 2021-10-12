package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s attributes matches any of the keywords given.
 */
public class PersonHasEmail implements Predicate<Person> {
    private final Email email;

    public PersonHasEmail(Email email) {
        this.email = email;
    }

    @Override
    public boolean test(Person person) {
        return person.getEmail().equals(email);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonHasEmail // instanceof handles nulls
                && email.equals(((PersonHasEmail) other).email)); // state check
    }

}
