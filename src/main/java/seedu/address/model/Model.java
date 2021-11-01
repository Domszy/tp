package seedu.address.model;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.tag.Tag;
import seedu.address.ui.ThemeType;

/**
 * The API of the Model component.
 */
public interface Model {
    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Client> PREDICATE_SHOW_ALL_CLIENTS = unused -> true;

    /**
     * {@code Predicate} that always evaluate to true
     */
    Predicate<Tag> PREDICATE_SHOW_ALL_TAGS = unused -> true;

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Returns the list of all address book file path.
     */
    ObservableList<Path> getAddressBookList();

    /**
     * Returns the current filtered list of theme.
     */
    ObservableList<ThemeType> getThemeList();

    /**
     * Returns the current theme for the GUI.
     */
    ThemeType getTheme();

    /**
     * Sets the theme of the GUI.
     */
    void setTheme(ThemeType theme);

    /**
     * Returns the user prefs' address book file path wrapped object.
     */
    ObservableValue<Path> getAddressBookFilePathObject();

    /**
     * Returns the user prefs' address book directory.
     */
    Path getAddressBookDirectory();

    /**
     * Adds {@code filePath} to address books list
     */
    void addAddressBookList(Path filePath);

    /**
     * Deletes {@code filePath} from address books list
     */
    void deleteAddressBookList(Path filePath);

    /**
     * Returns string representation of all address book list
     */
    String getAddressBookListString();

    /**
     * Returns the AddressBook
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /**
     * Returns true if a client with the same identity as {@code client} exists in the address book.
     */
    boolean hasClient(Client client);

    /**
     * Returns true if a client with the same identity as {@code clientId} exists in the address book.
     */
    boolean hasClientId(ClientId clientId);

    /**
     * Deletes the client with the matching Client ID and Email and returns the deleted client
     */
    List<Client> removeAllClients(List<ClientId> clientIds);

    /**
     * Adds the given client.
     * {@code client} must not already exist in the address book.
     */
    void addClient(Client client);

    /**
     * Replaces the given client {@code target} with {@code editedClient}.
     * {@code target} must exist in the address book.
     * The client identity of {@code editedClient} must not be the same as another existing client in the address book.
     */
    List<Client> setAllClients(List<ClientId> clientIds, EditClientDescriptor editedClientDescriptor);

    /**
     * Returns client with corresponding clientId.
     *
     * @param clientId of client
     * @return client with given clientId
     */
    Client getClient(ClientId clientId);

    /**
     * Returns true if a tag with the same identity as {@code tagName} exists in the address book.
     */
    boolean hasTagName(String tagName);

    /**
     * Adds the given Tag.
     * {@code Tag} must not already exist in the address book.
     */
    void addTag(Tag tag);

    /**
     * Returns Tag with corresponding tagName.
     *
     * @param tagName tagName of client
     * @return client with given tagName
     */
    Tag getTag(String tagName);

    /**
     * Returns an unmodifiable view of the filtered client list
     */
    ObservableList<Client> getFilteredClientList();

    /**
     * Returns an unmodifiable view of the filtered tag list.
     *
     * @return
     */
    ObservableList<Tag> getFilteredTagList();

    /**
     * Returns an unmodifiable view of the clients filtered by next meetings for current user.
     */
    ObservableList<Client> getSortedNextMeetingList();

    void filterSortedNextMeetingList(LocalDate date);

    /**
     * Updates the filter of the filtered client list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredClientList(Predicate<Client> predicate);

    /**
     * Updates the filter of the filtered client list to filter by current predicate and the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void filterFilteredClientList(Predicate<Client> predicate);

    /**
     * Sorts the filtered client list to sort by the given {@code sorter}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void sortFilteredClientList(Comparator<Client> sorter);

    /**
     * Returns an unmodifiable view of the client to view
     */
    ObservableList<Client> getClientToView();

    /**
     * Checks and returns if there is client with {@code clientId} to view
     *
     * @param clientId
     */
    boolean isClientExistToView(ClientId clientId);

    /**
     * Returns the name of the client to view
     */
    String getNameOfClientToView();

    /**
     * returns a list of NextMeeting that are on the given {@code date}.
     * Updates the filter of the filtered client to view list to filter by the given {@code predicate}.
     *
     * @param date of the schedule
     * @return the list of NextMeetings with the given {@code date}.
     */
    List<Client> retrieveSchedule(LocalDate date);

    /**
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateClientToView(Predicate<Client> predicate);
}
