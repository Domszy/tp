package seedu.address.model.client;

import java.util.function.Predicate;

/**
 * Tests that a {@code Client}'s attributes matches any of the keywords given.
 */
public class ClientHasId implements Predicate<Client> {
    private final ClientId clientId;

    public ClientHasId(ClientId clientId) {
        this.clientId = clientId;
    }

    @Override
    public boolean test(Client client) {
        return clientId.equals(client.getClientId());
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ClientHasId // instanceof handles nulls
                && clientId.equals(((ClientHasId) other).clientId)); // state check
    }
}
