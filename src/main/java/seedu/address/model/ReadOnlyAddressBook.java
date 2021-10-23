package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.NextMeeting;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the clients list.
     * This list will not contain any duplicate clients.
     */
    ObservableList<Client> getClientList();

    /**
     * Returns an unmodifiable view of the meetings list.
     * This list will not contain any duplicate NextMeetings.
     */
    ObservableList<NextMeeting> getNextMeetingsList();

    /**
     * Returns the current client counter
     */
    String getClientCounter();

    /**
     * Increments the clientCounter of the address book by 1 {@code clientCounter}.
     */
    void incrementClientCounter();

    void setClientCounter(String s);

    ObservableList<NextMeeting> getSortedNextMeetingsList();
}
