package seedu.address.model;

import java.nio.file.Path;

import javafx.beans.value.ObservableValue;
import seedu.address.commons.core.GuiSettings;

/**
 * Unmodifiable view of user prefs.
 */
public interface ReadOnlyUserPrefs {

    GuiSettings getGuiSettings();

    ObservableValue<Path> getAddressBookFilePathObject();

    Path getAddressBookFilePath();

    Path getAddressBookDirectory();
}
