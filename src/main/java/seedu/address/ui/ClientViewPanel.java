package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.client.Client;

/**
 * Panel that displays the full information of a {@code Client}.
 */
public class ClientViewPanel extends UiPart<Region> {

    private static final String FXML = "ClientViewPanel.fxml";

    @FXML
    private VBox clientInfoContainer;

    @FXML
    private Label clientName;

    @FXML
    private Label clientId;

    @FXML
    private Label clientCreationDate;

    @FXML
    private Label clientCreatedAt;

    @FXML
    private Label clientLastMet;

    @FXML
    private Label clientRiskAppetite;

    @FXML
    private Label clientCurrentPlans;

    @FXML
    private Label clientDisposableIncome;

    @FXML
    private Label nextMeeting;

    @FXML
    private VBox contactInfoContainer;

    @FXML
    private Label clientEmail;

    @FXML
    private Label clientPhoneNumber;

    /**
     * Creates a {@code ClientViewPanel} with listeners initiated to listen to updates
     * for the client to view.
     */
    public ClientViewPanel(ObservableList<Client> clientToView) {
        super(FXML);

        clientToView.addListener(new ListChangeListener<Client>() {
            @Override
            public void onChanged(Change<? extends Client> c) {
                updateView(clientToView);
            }
        });
    }

    /**
     * Updates the {@code ClientViewPanel} with information from the client to be viewed.
     */
    public void updateView(ObservableList<Client> clientToView) {
        if (clientToView.size() == 1 && clientToView.get(0) != null) {
            Client client = clientToView.get(0);
            clientName.setText(client.getName().toString());
            clientId.setText(client.getClientId().toString());
            clientCreatedAt.setText("-");
            clientLastMet.setText(client.getLastMet().toString());
            clientRiskAppetite.setText(client.getRiskAppetite().toString());
            clientCurrentPlans.setText(client.getCurrentPlan().toString());
            clientDisposableIncome.setText(client.getDisposableIncome().toString());
            nextMeeting.setText(client.getNextMeeting().toString());
            clientEmail.setText(client.getEmail().toString());
            clientPhoneNumber.setText(client.getPhone().toString());
        }
    }
}

