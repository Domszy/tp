package seedu.address.model;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;

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
     * Returns the current client counter
     */
    String getClientCounter();

    /**
     * Sets the client counter to {@code s}.
     */
    void setClientCounter(String s);

    /**
     * Increments the clientCounter of the address book by 1 {@code clientCounter}.
     */
    void incrementClientCounter();

    /**
     * Returns tag list of the address book.
     */
    List<Tag> getTagList();
}
