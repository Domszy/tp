package seedu.address.testutil;

import seedu.address.model.client.ClientId;

public class TypicalClientId {
    public static final ClientId CLIENTID_ZERO_CLIENT = new ClientId(Integer.toString(0));
    public static final ClientId CLIENTID_FIRST_CLIENT = new ClientId(Integer.toString(1));
    public static final ClientId CLIENTID_SECOND_CLIENT = new ClientId(Integer.toString(2));
    public static final ClientId CLIENTID_THIRD_CLIENT = new ClientId(Integer.toString(3));
    public static final ClientId CLIENTID_OUTOFBOUND = new ClientId(Integer.toString(99));
}
