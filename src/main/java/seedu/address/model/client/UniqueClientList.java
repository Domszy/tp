package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditCommand.createEditedClient;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.exceptions.ClientNotFoundException;
import seedu.address.model.client.exceptions.DuplicateClientException;

/**
 * A list of clients that enforces uniqueness between its elements and does not allow nulls.
 * A client is considered unique by comparing using {@code Client#isSameClient(Client)}. As such, adding and updating of
 * clients uses Client#isSameClient(Client) for equality so as to ensure that the client being added or updated is
 * unique in terms of identity in the UniqueClientList. However, the removal of a client uses Client#equals(Object) so
 * as to ensure that the client with exactly the same fields will be removed.
 * <p>
 * Supports a minimal set of list operations.
 *
 * @see Client#isSameClient(Client)
 */
public class UniqueClientList implements Iterable<Client> {

    private final ObservableList<Client> internalList = FXCollections.observableArrayList();
    private final ObservableList<Client> internalUnmodifiableList =
        FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent client as the given argument.
     */
    public boolean contains(Client toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameClient);
    }

    /**
     * Replaces the client {@code target} in the list with {@code editedClient}.
     * {@code target} must exist in the list.
     * The client identity of {@code editedClient} must not be the same as another existing client in the list.
     */
    public List<Client> setClientByClientIds(List<ClientId> clientIds, EditClientDescriptor editClientDescriptor) {
        requireAllNonNull(clientIds, editClientDescriptor);
        List<Client> duplicateList = new ArrayList<>(internalList);
        // Check for duplicate
        List<Client> editedClientList = clientIds.stream().map(clientId -> {
            Client client = getClient(clientId);
            Client editedClient = createEditedClient(client, editClientDescriptor);
            int index = duplicateList.indexOf(client);
            duplicateList.set(index, editedClient);
            return editedClient;
        }).collect(Collectors.toList());

        if (!clientsAreUnique(duplicateList)) {
            throw new DuplicateClientException();
        }

        // set if no duplicate
        clientIds.forEach(clientId -> {
            Client client = getClient(clientId);
            Client editedClient = createEditedClient(client, editClientDescriptor);
            int index = internalList.indexOf(client);
            internalList.set(index, editedClient);
        });

        return editedClientList;
    }

    /**
     * Adds a client to the list.
     * The client must not already exist in the list.
     */
    public void add(Client toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateClientException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the client with the corresponding {@code clientId}.
     */
    public Client getClient(ClientId clientId) {
        ObservableList<Client> clientInQuestion = internalList
            .filtered(client -> client.getClientId().equals(clientId));
        if (clientInQuestion.isEmpty()) {
            throw new ClientNotFoundException();
        }
        return clientInQuestion.get(0);
    }

    /**
     * Returns true if a client with the given {@code clientId} exists.
     *
     * @param clientId Id of the client
     * @return true if a client with the clientId exists
     */
    public boolean hasClientId(ClientId clientId) {
        ObservableList<Client> clientInQuestion = internalList
            .filtered(client -> client.getClientId().equals(clientId));
        return !clientInQuestion.isEmpty();
    }

    /**
     * Removes the equivalent client from the list.
     * The client must exist in the list.
     */
    // XXX: when is this used?
    public void remove(Client toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ClientNotFoundException();
        } else {
            toRemove.delete();
        }
    }

    /**
     * Removes the equivalent client with matching client id and/or email from the list.
     * The client must exist in the list.
     */
    public List<Client> deleteClientByClientIds(List<ClientId> clientIds) {
        requireAllNonNull(clientIds);
        List<Client> clientFound = new ArrayList<>();
        List<ClientId> clientIdNotFound = new ArrayList<>();
        for (ClientId clientId : clientIds) {
            ObservableList<Client> clientInQuestion = internalList
                .filtered(client -> client.getClientId().equals(clientId));
            if (clientInQuestion.isEmpty()) {
                clientIdNotFound.add(clientId);
            } else {
                clientFound.add(clientInQuestion.get(0));
            }
        }

        if (!clientIdNotFound.isEmpty()) {
            throw new ClientNotFoundException(clientIdNotFound);
        }

        clientFound.forEach(internalList::remove);
        return clientFound;
    }

    public void setClients(UniqueClientList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code clients}.
     * {@code clients} must not contain duplicate clients.
     */
    public void setClients(List<Client> clients) {
        requireAllNonNull(clients);
        if (!clientsAreUnique(clients)) {
            throw new DuplicateClientException();
        }

        internalList.setAll(clients);
    }

    /**
     * Finds Clients with meetings on the given {@code date}.
     *
     * @param date inputted by user for the "schedule" command.
     * @return list of clients with meetings on the given {@code date}.
     */

    public List<Client> retrieveNextMeetings(LocalDate date) {
        Predicate<Client> clientsWithSameDateNextMeeting = (client) -> {
            LocalDate meetingDate = client.getNextMeetingDate();
            if (meetingDate != null) {
                return meetingDate.equals(date);
            } else {
                return false;
            }
        };
        return internalList.filtered(clientsWithSameDateNextMeeting);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Client> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Client> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueClientList // instanceof handles nulls
            && internalList.equals(((UniqueClientList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code clients} contains only unique clients.
     */
    private boolean clientsAreUnique(List<Client> clients) {
        for (int i = 0; i < clients.size() - 1; i++) {
            for (int j = i + 1; j < clients.size(); j++) {
                if (clients.get(i).isSameClient(clients.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
