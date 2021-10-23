package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.Name;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.client.UniqueClientList;
import seedu.address.model.client.UniqueNextMeetingList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagIsUnreferenced;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameClient comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    private final UniqueClientList clients;
    private final UniqueNextMeetingList meetings;
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
        meetings = new UniqueNextMeetingList();
        tags = new UniqueTagList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Clients in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the client list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        this.clients.setClients(clients);
        removeUnreferencedTags();
    }

    /**
     * Replaces the contents of the meetings list with {@code meetings}.
     * {@code meetings} must not contain duplicate NextMeetings.
     */
    public void setMeetings(List<NextMeeting> meetings) {
        this.meetings.setNextMeetings(meetings);
    }

    /**
     * Replaces the clientCounter of the address book with {@code clientCounter}.
     */
    @Override
    public void setClientCounter(String clientCounter) {
        this.clientCounter = clientCounter;
    }

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

    /**
     * Gets the clientCounter of the address book.
     */
    @Override
    public String getClientCounter() {
        return this.clientCounter;
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        setClients(newData.getClientList());
        setMeetings(newData.getNextMeetingsList());
        setClientCounter(newData.getClientCounter());
    }

    //// meeting-level operations

    /**
     * Returns true if a nextMeeting with the same identity as {@code nextMeeting} exists in the address book.
     */
    public boolean hasNextMeeting(NextMeeting nextMeeting) {
        requireNonNull(nextMeeting);
        return meetings.contains(nextMeeting);
    }

    /**
     * Adds a NextMeeting to the address book.
     * The meeting must not already exist in the address book.
     */
    public void addNextMeeting(NextMeeting nextMeeting) {
        meetings.add(nextMeeting);
    }

    /**
     * Returns NextMeeting with corresponding withWho.
     *
     * @param withWho name of client
     * @return meeting with matching client
     */
    public NextMeeting getNextMeeting(Name withWho) {
        requireNonNull(withWho);
        return meetings.getNextMeeting(withWho);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeNextMeeting(NextMeeting key) {
        meetings.remove(key);
        removeUnreferencedTags();
    }

    //// person-level operations

    /**
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
     *
     * @return
     */
    public List<Client> setClientByClientIds(List<ClientId> clientIds, EditClientDescriptor editedClientDescriptor) {
        requireNonNull(clientIds);
        requireNonNull(editedClientDescriptor);
        return clients.setClientByClientIds(clientIds, editedClientDescriptor);
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

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeClient(Client key) {
        clients.remove(key);
        removeUnreferencedTags();
    }

    //// tag-level operations

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    /**
     * Removes tags that are unreferenced from the list.
     */
    public void removeUnreferencedTags() {
        logger.info("Cleaning unreferenced tags...");
        ArrayList<Predicate<Tag>> predicatesToDelete = new ArrayList<>();
        predicatesToDelete.add(new TagIsUnreferenced());
        try {
            FilteredList<Tag> removedTags = removeTagByFields(predicatesToDelete);
            logger.info(removedTags.size() + " unreferenced tags are cleared.");
        } catch (TagNotFoundException ignored) {
            logger.info("0 unreferenced tags are cleared.");
        }
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


    //// util methods

    /**
     * Removes client with matching {@code clientId} and {@code email} from this {@code AddressBook}.
     * Client with {@code clientId} and {@code email} must exist in the address book.
     */
    public List<Client> deleteClientByClientIds(List<ClientId> clientIds) {
        return clients.deleteClientByClientIds(clientIds);
    }

    public void deleteMeetingsByClients(List<Client> toDelete) {
        meetings.deleteByClients(toDelete);
    }

    /**
     * Removes client with matching {@code clientId} and {@code email} from this {@code AddressBook}.
     * Client with {@code clientId} and {@code email} must exist in the address book.
     */
    public FilteredList<Tag> removeTagByFields(List<Predicate<Tag>> predicates) {
        return tags.removeByFields(predicates);
    }

    @Override
    public ObservableList<Client> getClientList() {
        return clients.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<NextMeeting> getNextMeetingsList() {
        return meetings.asUnmodifiableObservableList();
    };

    @Override
    public ObservableList<NextMeeting> getSortedNextMeetingsList() {
        return meetings.asSortedObservableList();
    };

    public ObservableList<Tag> getTagList() {
        return tags.asUnmodifiableObservableList();
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addMeeting(NextMeeting nextMeeting) {
        meetings.add(nextMeeting);
    }

    @Override
    public String toString() {
        return clients.asUnmodifiableObservableList().size() + " clients";
        // TODO: refine later
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof AddressBook // instanceof handles nulls
            && clients.equals(((AddressBook) other).clients));
    }


    @Override
    public int hashCode() {
        return clients.hashCode();
    }
}
