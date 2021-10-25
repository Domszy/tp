package seedu.address.model.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

public class NextMeetingTest {

    @Test
    public void constructor_invalidNextMeeting_throwsIllegalArgumentException() {
        String validDate = "12-02-2021";
        String validStartTime = "08:00";
        String validEndTime = "13:00";
        String location = "NUS";
        String name = "keith";

        String invalidDate = "2021-02-19";
        String invalidTime = "2302";

        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(invalidDate, validStartTime,
            validEndTime, location, name));
        assertThrows(IllegalArgumentException.class, () -> new NextMeeting(validDate, invalidTime,
            validEndTime, location, name));
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
    public void isMeetingOver() {
        String date1 = "09-10-2021";
        String date2 = "10-10-2021";
        String date3 = "11-10-2021";
        String time1 = "11:00";
        String time2 = "13:00";
        String location = "Zoom";
        String name = "ben";

        LocalDate checkDate = LocalDate.of(2021, 10, 10);
        LocalTime checkTime = LocalTime.of(12, 0);

        assertTrue(new NextMeeting(date1, time1, time1, location, name).isMeetingOver(checkDate, checkTime));
        assertTrue(new NextMeeting(date2, time1, time1, location, name).isMeetingOver(checkDate, checkTime));
        assertFalse(new NextMeeting(date2, time2, time2, location, name).isMeetingOver(checkDate, checkTime));
        assertFalse(new NextMeeting(date3, time1, time1, location, name).isMeetingOver(checkDate, checkTime));
    }

    @Test
    public void convertToLastMet() {
        String date = "09-10-2021";
        String time = "11:00";
        String location = "Zoom";
        String name = "ben";

        LastMet expectedLastMet = new LastMet(date);
        NextMeeting nextMeeting = new NextMeeting(date, time, time, location, name);

        assertEquals(expectedLastMet, nextMeeting.convertToLastMet());
    }

    @Test
    public void toString_returnsCorrectRepresentation() {
        // null meeting
        NextMeeting nullMeeting = new NextMeeting(null, null, null, null, null);
        assertEquals(nullMeeting.toString(), NextMeeting.NO_NEXT_MEETING);

        // non-empty meeting
        NextMeeting nextMeeting = new NextMeeting("18-10-2021", "18:00", "19:00", "Zoom", "alice");
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
        String name1 = "ben";
        String name2 = "dom";

        // same value
        assertTrue(new NextMeeting(date1, time1, time1, location1, name1).equals(
            new NextMeeting(date1, time1, time1, location1, name1))
        );

        // different date
        assertFalse(new NextMeeting(date1, time1, time1, location1, name1).equals(
            new NextMeeting(date2, time1, time1, location1, name1))
        );

        // different startTime
        assertFalse(new NextMeeting(date1, time1, time2, location1, name1).equals(
            new NextMeeting(date1, time2, time2, location1, name1))
        );

        // different endTime
        assertFalse(new NextMeeting(date1, time1, time1, location1, name1).equals(
            new NextMeeting(date1, time1, time2, location1, name1))
        );

        // different location
        assertFalse(new NextMeeting(date1, time1, time2, location1, name1).equals(
            new NextMeeting(date1, time1, time2, location2, name1))
        );

        // different name
        assertFalse(new NextMeeting(date1, time1, time2, location1, name1).equals(
                new NextMeeting(date1, time1, time2, location1, name2))
        );
    }
}
