package seedu.address.ui;

import static seedu.address.commons.util.StringUtil.transformEmptyRepresentation;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.client.Client;

/**
 * Panel that displays the full information of a {@code Client}.
 */
public class ClientViewPanel extends UiPart<Region> {

    private static final String FXML = "ClientViewPanel.fxml";

    @FXML
    private VBox cardPane;

    @FXML
    private Label clientName;

    @FXML
    private Label clientId;

    @FXML
    private Label clientCreationDate;

    @FXML
    private Label clientLastMet;

    @FXML
    private Label clientRiskAppetite;

    @FXML
    private Label clientCurrentPlans;

    @FXML
    private Label clientDisposableIncome;

    @FXML
    private Label clientNextMeeting;

    @FXML
    private Label clientEmail;

    @FXML
    private Label clientPhoneNumber;

    @FXML
    private Label clientAddress;

    @FXML
    private Label textOverlay;

    /**
     * Creates a {@code ClientViewPanel} with listeners initiated to listen to updates
     * for the client to view.
     */
    public ClientViewPanel(ObservableList<Client> clientToView) {
        super(FXML);

        GaussianBlur gaussianBlur = new GaussianBlur();
        gaussianBlur.setRadius(10);
        cardPane.setEffect(gaussianBlur);

        clientToView.addListener((ListChangeListener<Client>) c -> updateView(clientToView));
    }

    /**
     * Unblurs the client info section.
     */
    private void unlock() {
        assert textOverlay.isVisible();

        textOverlay.setVisible(false);
        cardPane.setEffect(null);
    }

    /**
     * Updates the {@code ClientViewPanel} with information from the client to be viewed.
     */
    private void updateView(ObservableList<Client> clientToView) {
        if (clientToView.size() == 1 && clientToView.get(0) != null) {
            if (textOverlay.isVisible()) {
                unlock();
            }

            Client client = clientToView.get(0); // non-empty field
            clientName.setText(client.getName().toString()); // non-empty field
            clientId.setText(transformEmptyRepresentation(client.getClientId().toString()));
            clientLastMet.setText(transformEmptyRepresentation(client.getLastMet().toString()));
            clientAddress.setText(transformEmptyRepresentation(client.getAddress().toString()));
            clientRiskAppetite.setText(transformEmptyRepresentation(client.getRiskAppetite().toString()));
            clientCurrentPlans.setText(transformEmptyRepresentation(client.getCurrentPlan().toString()));
            clientDisposableIncome.setText(transformEmptyRepresentation(client.getDisposableIncome().toString()));
            clientNextMeeting.setText(client.getNextMeeting().toString()); // has empty representation
            clientEmail.setText(client.getEmail().toString()); //non-empty field
            clientPhoneNumber.setText(transformEmptyRepresentation(client.getPhone().toString()));
        } else {
            clientName.setText(null);
            clientId.setText(null);
            clientLastMet.setText(null);
            clientAddress.setText(null);
            clientRiskAppetite.setText(null);
            clientCurrentPlans.setText(null);
            clientDisposableIncome.setText(null);
            clientNextMeeting.setText(null);
            clientEmail.setText(null);
            clientPhoneNumber.setText(null);
        }
    }
}

