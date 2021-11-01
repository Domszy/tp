package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.Objects;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ObservableValue;
import seedu.address.commons.core.GuiSettings;
import seedu.address.storage.ThemeList;
import seedu.address.ui.ThemeType;

/**
 * Represents User's preferences.
 */
public class UserPrefs implements ReadOnlyUserPrefs {
    public static final Path DEFAULT_ADDRESSBOOK_DIRECTORY = Path.of("data");
    public static final Path DEFAULT_ADDRESSBOOK_FILE = DEFAULT_ADDRESSBOOK_DIRECTORY.resolve("addressbook.json");

    private GuiSettings guiSettings = new GuiSettings();
    private Path addressBookDirectory = DEFAULT_ADDRESSBOOK_DIRECTORY;
    private final SimpleObjectProperty<Path> addressBookFilePath =
            new SimpleObjectProperty<>(DEFAULT_ADDRESSBOOK_FILE);
    private final SimpleObjectProperty<ThemeType> theme = new SimpleObjectProperty<>(ThemeList.DEFAULT_THEME);

    /**
     * Creates a {@code UserPrefs} with default values.
     */
    public UserPrefs() {}

    /**
     * Creates a {@code UserPrefs} with the prefs in {@code userPrefs}.
     */
    public UserPrefs(ReadOnlyUserPrefs userPrefs) {
        this();
        resetData(userPrefs);
    }

    /**
     * Resets the existing data of this {@code UserPrefs} with {@code newUserPrefs}.
     */
    public void resetData(ReadOnlyUserPrefs newUserPrefs) {
        requireNonNull(newUserPrefs);
        setGuiSettings(newUserPrefs.getGuiSettings());
        setAddressBookFilePath(newUserPrefs.getAddressBookFilePath());
        setAddressBookDirectory(newUserPrefs.getAddressBookDirectory());
        setTheme(newUserPrefs.getThemeType());
    }

    @Override
    public GuiSettings getGuiSettings() {
        return guiSettings;
    }

    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        this.guiSettings = guiSettings;
    }

    public ObservableValue<Path> getAddressBookFilePathObject() {
        return addressBookFilePath;
    }

    @Override
    public Path getAddressBookFilePath() {
        return addressBookFilePath.get();
    }

    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        this.addressBookFilePath.set(addressBookFilePath);
    }

    @Override
    public Path getAddressBookDirectory() {
        return addressBookDirectory;
    }

    public void setAddressBookDirectory(Path addressBookDirectory) {
        requireNonNull(addressBookDirectory);
        this.addressBookDirectory = addressBookDirectory;
    }

    @Override
    public ThemeType getThemeType() {
        return this.theme.get();
    }

    public void setTheme(ThemeType theme) {
        this.theme.set(theme);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UserPrefs)) { //this handles null as well.
            return false;
        }

        UserPrefs o = (UserPrefs) other;

        return guiSettings.equals(o.guiSettings)
                && addressBookFilePath.get().equals(o.addressBookFilePath.get());
    }

    @Override
    public int hashCode() {
        return Objects.hash(guiSettings, addressBookFilePath);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Gui Settings : " + guiSettings);
        sb.append("\nLocal data file location : " + addressBookFilePath);
        return sb.toString();
    }

}
