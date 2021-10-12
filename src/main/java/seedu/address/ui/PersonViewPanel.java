package seedu.address.ui;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;

/**
 * Panel that displays the full information of a {@code Person}.
 */
public class PersonViewPanel extends UiPart<Region> {

    private static final String FXML = "PersonViewPanel.fxml";

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
     * Creates a {@code PersonViewPanel} with listeners initiated to listen to updates
     * for the person to view.
     */
    public PersonViewPanel(ObservableList<Person> personToView) {
        super(FXML);

        personToView.addListener(new ListChangeListener<Person>() {
            @Override
            public void onChanged(Change<? extends Person> c) {
                updateView(personToView);
            }
        });
    }

    /**
     * Updates the {@code PersonViewPanel} with information from the person to be viewed.
     */
    public void updateView(ObservableList<Person> personToView) {
        if (personToView.size() == 1 && personToView.get(0) != null) {
            Person person = personToView.get(0);
            clientName.setText(person.getName().toString());
            clientId.setText(person.getClientId().toString());
            clientCreatedAt.setText("-");
            clientLastMet.setText(person.getLastMet().toString());
            clientRiskAppetite.setText(person.getRiskAppetite().toString());
            clientCurrentPlans.setText(person.getCurrentPlan().toString());
            clientDisposableIncome.setText(person.getDisposableIncome().toString());
            nextMeeting.setText("-");
            clientEmail.setText(person.getEmail().toString());
            clientPhoneNumber.setText(person.getPhone().toString());
        }
    }
}

