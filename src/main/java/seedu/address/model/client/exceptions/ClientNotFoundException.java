package seedu.address.model.client.exceptions;

import static seedu.address.commons.util.StringUtil.COMMA_DELIMITER;
import static seedu.address.commons.util.StringUtil.joinListToString;

import java.util.List;

import seedu.address.model.client.ClientId;

/**
 * Signals that the operation is unable to find the specified client.
 */
public class ClientNotFoundException extends RuntimeException {

    public ClientNotFoundException() {
    }

    public ClientNotFoundException(List<ClientId> notFound) {
        super(joinListToString(notFound, COMMA_DELIMITER));
    }
}
