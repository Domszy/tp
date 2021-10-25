package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ClientBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullClient_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_clientAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingClientAdded modelStub = new ModelStubAcceptingClientAdded();
        Function<ClientId, Client> validClientFunction = new ClientBuilder().buildFunction();
        Client validClient = new ClientBuilder().build();

        CommandResult commandResult = new AddCommand(validClientFunction).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validClient), commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validClient), modelStub.clientsAdded);
    }

    @Test
    public void execute_duplicateClient_throwsCommandException() {
        Function<ClientId, Client> validClientFunction = new ClientBuilder().buildFunction();
        Client validClient = new ClientBuilder().build();
        AddCommand addCommand = new AddCommand(validClientFunction);
        ModelStub modelStub = new ModelStubWithClient(validClient);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_CLIENT, () -> addCommand.execute(modelStub));
    }

    @Test
    public void equals() {
        Function<ClientId, Client> alice = new ClientBuilder().withName("Alice").buildFunction();
        Function<ClientId, Client> bob = new ClientBuilder().withName("Bob").buildFunction();
        AddCommand addAliceCommand = new AddCommand(alice);
        AddCommand addBobCommand = new AddCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        AddCommand addAliceCommandCopy = new AddCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different client -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));


    }

    /**
     * A default model stub that have all of the methods failing.
     */
    public static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableValue<Path> getAddressBookFilePathObject() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookDirectory() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Path> getAddressBookList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAddressBookList(Path filePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteAddressBookList(Path filePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getAddressBookListString() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClient(Client client) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasClientId(ClientId clientId) {
            return true;
        }

        @Override
        public List<Client> deleteClientByClientIds(List<ClientId> clientIds) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteMeetingsByClients(List<Client> toDelete) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Client> setClientByClientIds(List<ClientId> clientIds,
            EditClientDescriptor editedClientDescriptor) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Client getClient(ClientId clientId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getFilteredClientList() {
            throw new AssertionError("This method should not be called.");
        }

        public void addNextMeeting(NextMeeting nextMeeting) {

        }

        @Override
        public ObservableList<NextMeeting> getFilteredNextMeetingList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void filterSortedNextMeeting(LocalDate scheduleDate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void filterFilteredClientList(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void sortFilteredClientList(Comparator<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Client> getClientToView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isClientExistToView(ClientId clientId) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getNameOfClientToView() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Client> retrieveSchedule(LocalDate date) {
            return null;
        }

        @Override
        public void updateClientToView(Predicate<Client> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTagName(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addTag(Tag tag) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Tag getTag(String tagName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredTagList(Predicate<Tag> predicate) {
            throw new AssertionError("This method should not be called.");
        }

    }

    /**
     * A Model stub that contains a single client.
     */
    private class ModelStubWithClient extends ModelStub {
        private final Client client;

        ModelStubWithClient(Client client) {
            requireNonNull(client);
            this.client = client;
        }

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return this.client.isSameClient(client);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the client being added.
     */
    private class ModelStubAcceptingClientAdded extends ModelStub {
        final ArrayList<Client> clientsAdded = new ArrayList<>();

        @Override
        public boolean hasClient(Client client) {
            requireNonNull(client);
            return clientsAdded.stream().anyMatch(client::isSameClient);
        }

        @Override
        public void addClient(Client client) {
            requireNonNull(client);
            clientsAdded.add(client);
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
