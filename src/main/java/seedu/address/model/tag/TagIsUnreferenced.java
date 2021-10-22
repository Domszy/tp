package seedu.address.model.tag;

import java.util.function.Predicate;

/**
 * Tests if {@code Tag} is unreferenced by clients.
 */
public class TagIsUnreferenced implements Predicate<Tag> {

    @Override
    public boolean test(Tag tag) {
        return tag.getClients().size() == 0;
    }
}

