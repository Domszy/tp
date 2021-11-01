package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static final String FXML = "MainWindow.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());
    private Image leadsForceLogo = new Image(this.getClass().getResourceAsStream("/images/logo.png"));
    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private ClientListPanel clientListPanel;
    private ResultDisplay resultDisplay;
    private SideBar sideBar;
    private HelpWindow helpWindow;
    private CommandBox commandBox;
    private AddressBookListMenu addressBookListMenu;
    private ThemeListMenu themeListMenu;
    private TagsPanel tagsPanel;

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuBar menuBar;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane clientListPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane sideBarPlaceHolder;

    @FXML
    private StackPane statusBarPlaceholder;

    @FXML
    private StackPane tagsPanelPlaceholder;

    @FXML
    private ImageView displayLogo;

    @FXML
    private Scene scene;

    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();

        displayLogo.setImage(leadsForceLogo);
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     *
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());

        clientListPanel = new ClientListPanel(logic.getFilteredClientList(), commandBox);
        clientListPanelPlaceholder.getChildren().add(clientListPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getAddressBookFilePathObject());
        statusBarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        sideBar = new SideBar(logic.getClientToView(), logic.getSortedNextMeetingList());
        sideBarPlaceHolder.getChildren().add(sideBar.getRoot());

        tagsPanel = new TagsPanel(logic.getFilteredTagList(), commandBox);
        tagsPanelPlaceholder.getChildren().add(tagsPanel.getRoot());


        addressBookListMenu = new AddressBookListMenu(logic, logic.getAddressBookFilePathObject());
        menuBar.getMenus().add(addressBookListMenu.getRoot());

        ObservableList<String> styleSheets = this.scene.getStylesheets();
        styleSheets.add(this.logic.getTheme().getFilePathName());
        themeListMenu = new ThemeListMenu(this.logic, this.scene.getStylesheets());
        menuBar.getMenus().add(themeListMenu.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    private void handleClear() {
        commandBox.setCommandExecutor(this::warnClearCommand);
    }

    /**
     * Executes the clear command and returns the result.
     *
     * @see seedu.address.logic.Logic#clearExecute(String)
     */
    private CommandResult warnClearCommand(String commandText) throws CommandException, ParseException {
        CommandResult commandResult;
        try {
            commandResult = logic.clearExecute(commandText);
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            commandBox.setCommandExecutor(this::executeCommand);
        } catch (CommandException | ParseException e) {
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }

        return commandResult;
    }

    /**
     * Switches the Address Book.
     */
    private void handleSwitchAddressBook() {
        // TODO: either this.logic or logic
        this.logic.switchAddressBook();
    }

    /**
     * Create a new Address Book.
     */
    private void handleCreateAddressBook() throws CommandException {
        this.logic.createAddressBook();
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#normalExecute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.normalExecute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isClearing()) {
                handleClear();
            }

            if (commandResult.isSwitchAddressBook()) {
                handleSwitchAddressBook();
            }

            if (commandResult.isCreateAddressBook()) {
                handleCreateAddressBook();
            }

            return commandResult;
        } catch (CommandException | ParseException | RuntimeException e) {

            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());

            if (e.getCause() instanceof ParseException) {
                throw (ParseException) e.getCause();
            }

            throw e;
        }
    }
}
