package seedu.address.logic;

import static seedu.address.logic.commands.ClearCommand.MESSAGE_CLEARED;
import static seedu.address.logic.commands.ClearCommand.MESSAGE_NOT_CLEARED;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.AddressBookParser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;
import seedu.address.storage.AddressBookStorage;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.Storage;
import seedu.address.ui.ThemeType;

/**
 * The main LogicManager of the app.
 */
public class LogicManager implements Logic {
    public static final String FILE_OPS_ERROR_MESSAGE = "Could not save data to file: ";
    private final Logger logger = LogsCenter.getLogger(LogicManager.class);

    private final Model model;
    private final Storage storage;
    private final AddressBookParser addressBookParser;

    /**
     * Constructs a {@code LogicManager} with the given {@code Model} and {@code Storage}.
     */
    public LogicManager(Model model, Storage storage) {
        this.model = model;
        this.storage = storage;
        addressBookParser = new AddressBookParser(model);
    }

    @Override
    public CommandResult normalExecute(String commandText) throws CommandException, ParseException {
        logger.info("----------------[USER COMMAND][" + commandText + "]");

        CommandResult commandResult;
        Command command = addressBookParser.parseCommand(commandText);

        commandResult = command.execute(model);
        saveAddressBook();

        return commandResult;
    }

    @Override
    public CommandResult clearExecute(String commandText) throws CommandException, ParseException {
        boolean confirmedClear = ParserUtil.parseConfirmation(commandText);
        if (!confirmedClear) {
            return new CommandResult(MESSAGE_NOT_CLEARED);
        }

        model.setAddressBook(new AddressBook());
        saveAddressBook();
        return new CommandResult(MESSAGE_CLEARED);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return model.getAddressBook();
    }

    @Override
    public ObservableList<Client> getFilteredClientList() {
        return model.getFilteredClientList();
    }

    @Override
    public ObservableList<Client> getSortedNextMeetingList() {
        return model.getSortedNextMeetingList();
    }

    @Override
    public ObservableList<Client> getClientToView() {
        return model.getClientToView();
    }

    @Override
    public Path getAddressBookFilePath() {
        return model.getAddressBookFilePath();
    }

    @Override
    public ObservableValue<Path> getAddressBookFilePathObject() {
        return model.getAddressBookFilePathObject();
    }

    @Override
    public GuiSettings getGuiSettings() {
        return model.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        model.setGuiSettings(guiSettings);
    }

    @Override
    public void switchAddressBook() {
        Path filePath = getAddressBookFilePath();
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(filePath);
        ReadOnlyAddressBook addressBook;
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional = this.storage.readAddressBook(filePath);
            addressBook = addressBookOptional.orElseGet(AddressBook::new);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. Will be starting with an empty AddressBook");
            addressBook = new AddressBook();
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. Will be starting with an empty AddressBook");
            addressBook = new AddressBook();
        }

        this.model.setAddressBook(addressBook);
        this.storage.switchAddressBookStorage(addressBookStorage);
    }

    @Override
    public void switchAddressBook(Path filePath) {
        this.model.setAddressBookFilePath(filePath);
        this.switchAddressBook();
    }

    @Override
    public void createAddressBook() throws CommandException {
        Path filePath = getAddressBookFilePath();
        AddressBookStorage addressBookStorage = new JsonAddressBookStorage(filePath);
        ReadOnlyAddressBook addressBook = new AddressBook();

        this.model.setAddressBook(addressBook);
        this.storage.switchAddressBookStorage(addressBookStorage);
        this.model.addAddressBookList(filePath);
        saveAddressBook();
    }

    @Override
    public ObservableList<Path> getAddressBookList() {
        return model.getAddressBookList();
    }

    @Override
    public ObservableList<ThemeType> getThemeList() {
        return model.getThemeList();
    }

    @Override
    public ThemeType getTheme() {
        return model.getTheme();
    }

    @Override
    public void setTheme(ThemeType theme) {
        model.setTheme(theme);
    }

    @Override
    public ObservableList<Tag> getFilteredTagList() {
        return model.getFilteredTagList();
    }

    /**
     * Saves the current Address Book.
     */
    private void saveAddressBook() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook());
        } catch (IOException ioe) {
            throw new CommandException(FILE_OPS_ERROR_MESSAGE + ioe, ioe);
        }
    }
}
