package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.Client;
import seedu.address.model.tag.Tag;

/**
 * An UI component that displays the important information of a {@code Client}.
 */
public class ClientCard extends UiPart<Region> {

    private static final String FXML = "ClientListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Client client;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private Label riskAppetite;
    @FXML
    private Label disposableIncome;
    @FXML
    private Label lastMet;
    @FXML
    private Label currentPlan;
    @FXML
    private Label nextMeeting;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code ClientCode}.
     */
    public ClientCard(Client client) {
        super(FXML);
        this.client = client;
        id.setText(client.getClientId().value + ". ");
        name.setText(client.getName().fullName);
        email.setText(client.getEmail().value);
        phone.setText(client.getPhone().value);
        address.setText(client.getAddress().value);
        riskAppetite.setText(client.getRiskAppetite().value);
        disposableIncome.setText(client.getDisposableIncome().value);
        currentPlan.setText(client.getCurrentPlan().toString());
        lastMet.setText(client.getLastMet().toString());
        nextMeeting.setText(client.getNextMeeting().toString());
        client.getTags().stream()
                .sorted(Comparator.comparing(Tag::getName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.getName())));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ClientCard)) {
            return false;
        }

        // state check
        ClientCard card = (ClientCard) other;
        return id.getText().equals(card.id.getText())
                && client.equals(card.client);
    }
}
