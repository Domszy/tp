package seedu.address.storage;

import static seedu.address.model.UserPrefs.DEFAULT_ADDRESSBOOK_DIRECTORY;
import static seedu.address.model.UserPrefs.DEFAULT_ADDRESSBOOK_FILE;

import java.nio.file.Path;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.GuiSettings;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;
import seedu.address.ui.ThemeType;

/**
 * An Immutable UserPrefs that is serializable to JSON format.
 */
@JsonRootName(value = "userPrefs")
class JsonSerializableUserPrefs {
    private final GuiSettings guiSettings;
    private final Path filepath;
    private final Path fileDirectory;
    private final String themeName;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given clients.
     */
    @JsonCreator
    public JsonSerializableUserPrefs(@JsonProperty("guiSettings") GuiSettings guiSettings,
                                     @JsonProperty("addressBookFilePath") Path filepath,
                                     @JsonProperty("addressBookFileDirectory") Path fileDirectory,
                                     @JsonProperty("theme") String themeName) {
        this.guiSettings = guiSettings;
        this.filepath = filepath;
        this.fileDirectory = fileDirectory;
        this.themeName = themeName;
    }

    /**
     * Converts a given {@code ReadOnlyUserPrefs} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserPrefs}.
     */
    public JsonSerializableUserPrefs(ReadOnlyUserPrefs source) {
        this.filepath = source.getAddressBookFilePath();
        this.guiSettings = source.getGuiSettings();
        this.fileDirectory = source.getAddressBookDirectory();
        this.themeName = source.getThemeType().toString();
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     */
    public UserPrefs toModelType() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Optional.ofNullable(this.filepath).orElse(DEFAULT_ADDRESSBOOK_FILE));
        userPrefs.setGuiSettings(Optional.ofNullable(this.guiSettings).orElseGet(GuiSettings::new));
        userPrefs.setAddressBookDirectory(
                Optional.ofNullable(this.fileDirectory).orElse(DEFAULT_ADDRESSBOOK_DIRECTORY));
        userPrefs.setTheme(Optional.ofNullable(this.themeName).flatMap(ThemeType::of).orElse(ThemeList.DEFAULT_THEME));
        return userPrefs;
    }

}
