package seedu.address.logic;

import java.nio.file.Path;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;
import seedu.address.ui.ThemeType;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult normalExecute(String commandText) throws CommandException, ParseException;

    /**
     * Executes the command in context of clearing AddressBook.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     */
    CommandResult clearExecute(String commandText) throws CommandException, ParseException;

    /**
     * Returns the AddressBook.
     *
     * @see seedu.address.model.Model#getAddressBook()
     */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns an unmodifiable view of the filtered list of clients
     */
    ObservableList<Client> getFilteredClientList();

    /**
     * Returns an unmodifiable view of the clients filtered by next meetings for current user.
     */
    ObservableList<Client> getSortedNextMeetingList();

    /**
     * Returns an unmodifiable view of the client to view
     */
    ObservableList<Client> getClientToView();

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Returns the user prefs' address book file path wrapped object.
     */
    ObservableValue<Path> getAddressBookFilePathObject();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Switches the AddressBook to that specified {@code Path filePath}
     * in {@code addressBookFilePath} of {@code UserPrefs}
     */
    void switchAddressBook();

    /**
     * Switches the AddressBook to that specified {@code Path filePath}
     * in {@code addressBookFilePath} of {@code UserPrefs}
     */
    void switchAddressBook(Path filePath);

    /**
     * Creates the AddressBook at the specified {@code Path filePath}
     *
     * @throws CommandException If an error occurs during command execution.
     */
    void createAddressBook() throws CommandException;

    /**
     * Returns the list of all address book file path.
     */
    ObservableList<Path> getAddressBookList();

    /**
     * Returns the list of all filtered list of themes.
     */
    ObservableList<ThemeType> getThemeList();

    /**
     * Returns the current theme.
     */
    ThemeType getTheme();

    /**
     * Sets the theme of the GUI in user prefs.
     */
    void setTheme(ThemeType theme);

    /**
     * Returns an unmodifiable view of the filtered list of tags
     */
    ObservableList<Tag> getFilteredTagList();
}
