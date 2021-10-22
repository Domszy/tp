package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class NextMeetingTest {

    @Test
    public void constructor_invalidNextMeeting_throwsIllegalArgumentException() {
        String validDate = "12-02-2021";
        String validStartTime = "08:00";
        String validEndTime = "13:00";
        String location = "NUS";

        String invalidDate = "2021-02-19";
        String invalidTime = "2302";

        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(invalidDate, validStartTime,
            validEndTime, location));
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(validDate, invalidTime,
            validEndTime, location));
    }

    @Test
    public void isValidNextMeetingString() {
        // null
        assertThrows(NullPointerException.class, () -> NextMeeting.isValidNextMeeting(null));

        // blank
        assertFalse(NextMeeting.isValidNextMeeting(" ")); // empty string

        // missing parts
        assertFalse(NextMeeting.isValidNextMeeting("20-30")); // missing local part
        assertFalse(NextMeeting.isValidNextMeeting("24-09-2021, Starbucks @ UTown"));
        assertFalse(NextMeeting.isValidNextMeeting("24-09-2021 (10:00~12:00),    "));

        // valid next meeting
        assertTrue(NextMeeting.isValidNextMeeting("24-09-2021 (10:00~12:00), Starbucks @ UTown"));
        assertTrue(NextMeeting.isValidNextMeeting("25-12-2021 (14:00~17:00), null"));
    }

    @Test
    public void getNullMeeting_returnsNextMeetingWithNullFields() {
        NextMeeting expectedNextMeeting = new NextMeeting(null, null, null, null);
        assertEquals(NextMeeting.getNullMeeting(), expectedNextMeeting);
    }

    @Test
    public void toString_returnsCorrectRepresentation() {
        // null meeting
        NextMeeting nullMeeting = new NextMeeting(null, null, null, null);
        assertEquals(nullMeeting.toString(), NextMeeting.NO_NEXT_MEETING);

        // non-empty meeting
        NextMeeting nextMeeting = new NextMeeting("18-10-2021", "18:00", "19:00", "Zoom");
        assertEquals(nextMeeting.toString(), "18-10-2021 (18:00~19:00), Zoom");
    }

    @Test
    public void isEqual() {
        String date1 = "18-10-2021";
        String date2 = "19-10-2021";
        String time1 = "18:00";
        String time2 = "19:00";
        String location1 = "Zoom";
        String location2 = "NUS";

        // same value
        assertTrue(new NextMeeting(date1, time1, time1, location1).equals(
            new NextMeeting(date1, time1, time1, location1))
        );

        // different date
        assertFalse(new NextMeeting(date1, time1, time1, location1).equals(
            new NextMeeting(date2, time1, time1, location1))
        );

        // different startTime
        assertFalse(new NextMeeting(date1, time1, time2, location1).equals(
            new NextMeeting(date1, time2, time2, location1))
        );

        // different endTime
        assertFalse(new NextMeeting(date1, time1, time1, location1).equals(
            new NextMeeting(date1, time1, time2, location1))
        );

        // different location
        assertFalse(new NextMeeting(date1, time1, time2, location1).equals(
            new NextMeeting(date1, time1, time2, location2))
        );
    }
}
