package seedu.address.storage;

import static seedu.address.model.UserPrefs.DEFAULT_ADDRESSBOOK_FILE;

import java.nio.file.Path;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.UserPrefs;

/**
 * An Immutable UserPrefs that is serializable to JSON format.
 */
@JsonRootName(value = "userprefs")
class JsonSerializableUserPrefs {
    private final Path filepath;
    private final GuiSettings guiSettings;

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given clients.
     */
    @JsonCreator
    public JsonSerializableUserPrefs(@JsonProperty("guiSettings") GuiSettings guiSettings,
                                     @JsonProperty("addressBookFilePath") Path filepath) {
        this.filepath = filepath;
        this.guiSettings = guiSettings;
    }

    /**
     * Converts a given {@code ReadOnlyUserPrefs} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableUserPrefs}.
     */
    public JsonSerializableUserPrefs(ReadOnlyUserPrefs source) {
        this.filepath = source.getAddressBookFilePath();
        this.guiSettings = source.getGuiSettings();
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public UserPrefs toModelType() {
        UserPrefs userPrefs = new UserPrefs();
        userPrefs.setAddressBookFilePath(Optional.ofNullable(this.filepath).orElse(DEFAULT_ADDRESSBOOK_FILE));
        userPrefs.setGuiSettings(Optional.ofNullable(this.guiSettings).orElseGet(GuiSettings::new));
        return userPrefs;
    }

}
