package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTMEETING;

import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.SortByAttribute;
import seedu.address.model.tag.Tag;
import seedu.address.storage.AddressBookList;
import seedu.address.storage.ThemeList;
import seedu.address.ui.ThemeType;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final SortedList<Client> sortedClients;
    private final FilteredList<Client> filteredClients;
    private final FilteredList<Client> clientToView;
    private final SortedList<Client> sortedNextMeetings;
    private final FilteredList<Client> shownNextMeetings;
    private final FilteredList<Tag> filteredTags;
    private final AddressBookList addressBookList;
    private final ThemeList themeList;

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        this.addressBookList = new AddressBookList(userPrefs.getAddressBookDirectory());
        this.themeList = new ThemeList(userPrefs.getThemeType());

        ObservableList<Client> clientList = this.addressBook.getClientList();
        sortedClients = new SortedList<>(clientList);
        filteredClients = new FilteredList<>(sortedClients);

        sortedNextMeetings = new SortedList<>(checkAllNextMeetings(this.addressBook.getClientList()));
        shownNextMeetings = new FilteredList<>(sortedNextMeetings);


        // TODO: filter by colors, etc
        filteredTags = new FilteredList<>(this.addressBook.getTagList());

        clientToView = new FilteredList<>(this.addressBook.getClientList());
        clientToView.setPredicate(PREDICATE_SHOW_ALL_CLIENTS.negate());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public ObservableList<Path> getAddressBookList() {
        return this.addressBookList.getAddressBookList();
    }

    @Override
    public ObservableList<ThemeType> getThemeList() {
        return this.themeList.getThemeList();
    }

    @Override
    public ThemeType getTheme() {
        return this.themeList.getTheme();
    }

    @Override
    public void setTheme(ThemeType theme) {
        this.userPrefs.setTheme(theme);
        this.themeList.setTheme(theme);
    }

    @Override
    public ObservableValue<Path> getAddressBookFilePathObject() {
        return userPrefs.getAddressBookFilePathObject();
    }

    @Override
    public Path getAddressBookDirectory() {
        return userPrefs.getAddressBookDirectory();
    }

    @Override
    public void addAddressBookList(Path filePath) {
        this.addressBookList.addAddressBookPath(filePath);
    }

    @Override
    public void deleteAddressBookList(Path filePath) {
        this.addressBookList.deleteAddressBookPath(filePath);
    }

    @Override
    public String getAddressBookListString() {
        return this.addressBookList.toString();
    }

    //=========== AddressBook ================================================================================

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
        this.clientToView.setPredicate(PREDICATE_SHOW_ALL_CLIENTS.negate());
    }

    @Override
    public boolean hasClient(Client client) {
        requireNonNull(client);
        return addressBook.hasClient(client);
    }

    @Override
    public boolean hasClientId(ClientId clientId) {
        requireNonNull(clientId);
        return addressBook.hasClientId(clientId);
    }

    @Override
    public List<Client> removeAllClients(List<ClientId> clientIds) {
        return addressBook.removeAllClients(clientIds);
    }

    @Override
    public void addClient(Client client) {
        addressBook.addClient(client);
        updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);
    }

    @Override
    public List<Client> setAllClients(List<ClientId> clientIds, EditClientDescriptor editedClientDescriptor) {
        requireAllNonNull(clientIds, editedClientDescriptor);
        return addressBook.setAllClients(clientIds, editedClientDescriptor);
    }

    @Override
    public Client getClient(ClientId clientId) {
        requireNonNull(clientId);
        return addressBook.getClient(clientId);
    }

    @Override
    public boolean hasTagName(String tagName) {
        requireNonNull(tagName);
        return addressBook.hasTagName(tagName);
    }

    @Override
    public void addTag(Tag tag) {
        addressBook.addTag(tag);
    }

    @Override
    public Tag getTag(String tagName) {
        requireNonNull(tagName);
        return addressBook.getTag(tagName);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getFilteredClientList() {
        return filteredClients;
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return filteredTags;
    }
    //=========== Filtered Client List Accessors =============================================================

    @Override
    public ObservableList<Client> getSortedNextMeetingList() {
        shownNextMeetings.setPredicate(Client::hasNextMeeting);
        return shownNextMeetings;
    }

    @Override
    public void filterSortedNextMeetingList(LocalDate date) {
        if (date == null) {
            shownNextMeetings.setPredicate(PREDICATE_SHOW_ALL_CLIENTS);
        } else {
            shownNextMeetings.setPredicate((client) -> client.isSameDayMeeting(date));
        }
    }

    @Override
    public void updateFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        filteredClients.setPredicate(predicate);
    }

    @Override
    public void filterFilteredClientList(Predicate<Client> predicate) {
        requireNonNull(predicate);
        Predicate<? super Client> currentPredicate = filteredClients.getPredicate();
        if (currentPredicate == null) {
            currentPredicate = PREDICATE_SHOW_ALL_CLIENTS;
        }
        filteredClients.setPredicate(predicate.and(currentPredicate));
    }

    @Override
    public void sortFilteredClientList(Comparator<Client> sorter) {
        sortedClients.setComparator(sorter);
    }

    /**
     * Returns an unmodifiable view of the list of {@code Client} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Client> getClientToView() {
        return clientToView;
    }

    @Override
    public boolean isClientExistToView(ClientId clientId) {
        return this.addressBook.hasClientId(clientId);
    }

    //=========== Client To View List Accessors =============================================================

    @Override
    public String getNameOfClientToView() {
        return clientToView.get(0).getName().toString();
    }

    @Override
    public List<Client> retrieveSchedule(LocalDate date) {
        return addressBook.retrieveLastMeetings(date);
    }

    @Override
    public void updateClientToView(Predicate<Client> predicate) {
        requireNonNull(predicate);
        clientToView.setPredicate(predicate);
    }

    private SortedList<Client> checkAllNextMeetings(ObservableList<Client> filteredClients) {
        SortedList<Client> sortedMeetings = new SortedList<>(filteredClients);
        Comparator<Client> nextMeetingComparator = new SortByAttribute(PREFIX_NEXTMEETING)
            .thenCompareByAttribute(PREFIX_CLIENTID);
        sortedMeetings.setComparator(nextMeetingComparator);

        return sortedMeetings;
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
            && userPrefs.equals(other.userPrefs)
            && filteredClients.equals(other.filteredClients)
            && filteredTags.equals(other.filteredTags)
            && clientToView.equals(other.clientToView);
    }
}
