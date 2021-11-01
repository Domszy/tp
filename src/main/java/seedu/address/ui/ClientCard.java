package seedu.address.ui;

import static seedu.address.commons.util.StringUtil.transformEmptyRepresentation;

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

    // Taken from https://unicode-table.com/en/emoji/
    private static final String EMAIL_ICON = "✉ ";
    private static final String PHONE_ICON = "☎ ";
    private static final String ADDRESS_ICON = "\uD83C\uDFE0 ";
    private static final String RA_ICON = "\uD83D\uDCC8 ";
    private static final String INCOME_ICON = "\uD83D\uDCB0 ";
    private static final String PLAN_ICON = "\uD83C\uDFE6 ";
    private static final String LASTMET_ICON = "\uD83E\uDD1D ";
    private static final String NEXTMEETING_ICON = "\uD83D\uDC4B ";

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
    public ClientCard(Client client, CommandBox commandBox) {
        super(FXML);

        this.client = client;
        id.setText(client.getClientId().value + ". "); // non-empty field
        name.setText(client.getName().fullName); // non-empty field
        email.setText(EMAIL_ICON + client.getEmail().value); // non-empty field
        phone.setText(PHONE_ICON + transformEmptyRepresentation(client.getPhone().value));
        address.setText(ADDRESS_ICON + transformEmptyRepresentation(client.getAddress().value));
        riskAppetite.setText(RA_ICON + transformEmptyRepresentation(client.getRiskAppetite().value));
        disposableIncome.setText(INCOME_ICON + transformEmptyRepresentation(client.getDisposableIncome().value));
        currentPlan.setText(PLAN_ICON + transformEmptyRepresentation(client.getCurrentPlan().toString()));
        lastMet.setText(LASTMET_ICON + transformEmptyRepresentation(client.getLastMet().toString()));
        nextMeeting.setText(NEXTMEETING_ICON + client.getNextMeeting().toString()); // has empty representation
        client.getTags().stream()
                .sorted(Comparator.comparing(Tag::getName))
                .forEach(tag -> tags.getChildren().add(new TagLabel(tag.getName(), tag, commandBox).getRoot()));
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
