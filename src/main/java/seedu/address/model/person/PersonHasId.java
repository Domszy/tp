package seedu.address.model.person;

import java.util.function.Predicate;

/**
 * Tests that a {@code Person}'s attributes matches any of the keywords given.
 */
public class PersonHasId implements Predicate<Person> {
    private final ClientId clientId;

    public PersonHasId(ClientId clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean test(Person person) {
        return person.getClientId().equals(clientId);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof PersonHasId // instanceof handles nulls
            && clientId.equals(((PersonHasId) other).clientId)); // state check
    }
}
