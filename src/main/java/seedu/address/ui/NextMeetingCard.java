package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.client.NextMeeting;

/**
 * An UI component that displays the information of a {@code NextMeeting}.
 */
public class NextMeetingCard extends UiPart<Region> {
    private static final String FXML = "NextMeetingListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final NextMeeting nextMeeting;

    @FXML
    private HBox cardPane;
    @FXML
    private Label date;
    @FXML
    private Label startTime;
    @FXML
    private Label endTime;
    @FXML
    private Label meetingLocation;
    @FXML
    private Label withWho;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code NextMeetingCode}.
     */
    public NextMeetingCard(NextMeeting nextMeeting) {
        super(FXML);
        this.nextMeeting = nextMeeting;
        date.setText(nextMeeting.dateInString);
        startTime.setText(nextMeeting.startTimeInString);
        endTime.setText(nextMeeting.endTimeInString);
        meetingLocation.setText(nextMeeting.location);
        withWho.setText(nextMeeting.getClientName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof NextMeetingCard)) {
            return false;
        }

        // state check
        NextMeetingCard card = (NextMeetingCard) other;
        return nextMeeting.equals(card.nextMeeting);
    }
}
