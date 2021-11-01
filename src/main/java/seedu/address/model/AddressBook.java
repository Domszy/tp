package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagIsUnreferenced;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final UniqueClientList clients;
    private final UniqueTagList tags;

    private String clientCounter;

    {
        /*
         * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
         * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
         *
         * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
         *   among constructors.
         */
        clients = new UniqueClientList();
        tags = new UniqueTagList();

        clients.asUnmodifiableObservableList().addListener((ListChangeListener<Client>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    Client client = change.getRemoved().get(0);
                    logger.fine(client.getName() + " was replaced/removed from UniqueClientList");
                    client.delete();
                    removeUnreferencedTags();
                }
            }
        });
    }

    /**
     * Creates an AddressBook using the Clients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    public AddressBook() {
    }

    //// list overwrite operations

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setClients(newData.getClientList());
        setTags(newData.getTagList());
        setClientCounter(newData.getClientCounter());
    }

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
    }

    public void setTags(List<Tag> tags) {
        this.tags.setTags(tags);
    }

    /**
     * /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return clients.contains(client);
    }

    /**
     * Returns true if a client with the given clientId exists.
     *
     * @param clientId of client
     * @return true if a client with the given clientId exists
     */
    public boolean hasClientId(ClientId clientId) {
        return clients.hasClientId(clientId);
    }

    /**
     * Adds a client to the address book.
     * The client must not already exist in the address book.
     */
    public void addClient(Client p) {
        clients.add(p);
    }

    /**
     * Replaces the given client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     * If the given client's next meeting is edited, {@code meetings} will be updated with the new NextMeeting
     *
     * @return the given client {@code target} in the list with {@code editedClient}.
     */
    public List<Client> setAllClients(List<ClientId> clientIds, EditClientDescriptor editedClientDescriptor) {
        requireNonNull(clientIds);
        requireNonNull(editedClientDescriptor);
        return clients.setAll(clientIds, editedClientDescriptor);
    }

    /**
     * Retrieve the NextMeeting from the given LocalDate by the user {@code date}.
     * Returns client with corresponding clientId.
     *
     * @return a list of NextMeetings that's on the same date as {@code date}
     */
    public List<Client> retrieveLastMeetings(LocalDate date) {
        requireNonNull(date);
        return clients.retrieveNextMeetings(date);
    }

    /**
     * @param clientId clientId of client
     * @return client with given clientId
     */
    public Client getClient(ClientId clientId) {
        requireNonNull(clientId);
        return clients.getClient(clientId);
    }

    //// person-level operations

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes tags that are unreferenced from the list.
     * This method should be invoked when
     */
    public void removeUnreferencedTags() {
        Predicate<Tag> predicate = new TagIsUnreferenced();
        ArrayList<Predicate<Tag>> predicatesToDelete = new ArrayList<>();
        predicatesToDelete.add(predicate);

        FilteredList<Tag> filteredList = getTagList().filtered(predicate);

        if (filteredList.size() < 1) {
            return;
        }

        logger.info("Cleaning unreferenced tags...");
        FilteredList<Tag> removedTags = removeTagByFields(predicatesToDelete);
        logger.info(removedTags.size() + " unreferenced tags are cleared.");
    }

    /**
     * Removes client with matching {@code clientId} and {@code email} from this {@code AddressBook}.
     * Client with {@code clientId} and {@code email} must exist in the address book.
     */
    public FilteredList<Tag> removeTagByFields(List<Predicate<Tag>> predicates) {
        return tags.removeByFields(predicates);
    }

    /**
     * Returns true if a tag with the given {@code tagName} exists.
     *
     * @param tagName name of the tag
     * @return true if a tag with the given tagName exists
     */
    public boolean hasTagName(String tagName) {
        return tags.hasTagName(tagName);
    }

    /**
     * Returns tag with the corresponding {@code tagName}.
     *
     * @param tagName name of the tag
     * @return tag with given tagName
     */
    public Tag getTag(String tagName) {
        requireNonNull(tagName);
        return tags.getTag(tagName);
    }

    /**
     * Removes client with matching {@code clientId} and {@code email} from this {@code AddressBook}.
     * Client with {@code clientId} and {@code email} must exist in the address book.
     */
    public List<Client> removeAllClients(List<ClientId> clientIds) {
        return clients.removeAll(clientIds);
    }

    //// tag-level operations

    public void updateLastMetDate() {
        clients.updateLastMetDate();
    }

    @Override
    public ObservableList<Client> getClientList() {
        updateLastMetDate();
        return clients.asUnmodifiableObservableList();
    }

    /**
     * Gets the clientCounter of the address book.
     */
    @Override
    public String getClientCounter() {
        return this.clientCounter;
    }

    /**
     * Replaces the clientCounter of the address book with {@code clientCounter}.
     */
    @Override
    public void setClientCounter(String clientCounter) {
        this.clientCounter = clientCounter;
    }


    //// util methods

    /**
     * Increments the clientCounter of the address book by 1 {@code clientCounter}.
     */
    @Override
    public void incrementClientCounter() {
        try {
            int clientCounterInt = Integer.parseInt(this.clientCounter) + 1;
            this.clientCounter = String.valueOf(clientCounterInt);
        } catch (NumberFormatException e) {
            this.clientCounter = "1";
        }
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    @Override
    public int hashCode() {
        return clients.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressBook // instanceof handles nulls
            && clients.equals(((AddressBook) other).clients));
    }

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
        // TODO: refine later
    }

}
