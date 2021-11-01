package seedu.address.ui;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import seedu.address.model.client.Client;

/**
 * An UI that acts as the side bar of the application, housing panels for easy viewing.
 */
public class SideBar extends UiPart<Region> {

    private static final String FXML = "SideBar.fxml";

    private ClientViewPanel clientViewPanel;
    private MeetingsListPanel meetingsListPanel;

    @FXML
    private StackPane clientViewPanelPlaceholder;

    @FXML
    private StackPane nextMeetingListPanelPlaceholder;

    /**
     * Creates a {@code SideBar} with panels initiated.
     */
    public SideBar(ObservableList<Client> clientToView, ObservableList<Client> nextMeetings) {
        super(FXML);

        clientViewPanel = new ClientViewPanel(clientToView);
        clientViewPanelPlaceholder.getChildren().add(clientViewPanel.getRoot());

        meetingsListPanel = new MeetingsListPanel(nextMeetings);
        nextMeetingListPanelPlaceholder.getChildren().add(meetingsListPanel.getRoot());
    }

}
