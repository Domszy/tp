package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClients.ALICE;
import static seedu.address.testutil.TypicalClients.BOB;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.client.Client;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.client.UniqueNextMeetingList;
import seedu.address.model.client.exceptions.DuplicateNextMeetingException;
import seedu.address.model.client.exceptions.MeetingNotFoundException;

public class UniqueNextMeetingListTest {

    private final UniqueNextMeetingList uniqueNextMeetingList = new UniqueNextMeetingList();

    @Test
    public void contains_nullNextMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNextMeetingList.contains(null));
    }

    @Test
    public void contains_nextMeetingNotInList_returnsFalse() {
        assertFalse(uniqueNextMeetingList.contains(ALICE.getNextMeeting()));
    }

    @Test
    public void contains_duplicateMeetingInList_returnsTrue() {
        uniqueNextMeetingList.add(ALICE.getNextMeeting());
        NextMeeting aliceNextMeeting = ALICE.getNextMeeting();
        assertTrue(uniqueNextMeetingList.contains(aliceNextMeeting));
    }

    @Test
    public void add_nullNextMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNextMeetingList.add(null));
    }

    @Test
    public void remove_existingNextMeeting_removesNextMeeting() {
        uniqueNextMeetingList.add(ALICE.getNextMeeting());
        uniqueNextMeetingList.remove(ALICE.getNextMeeting());
        UniqueNextMeetingList expectedUniqueNextMeetingList = new UniqueNextMeetingList();
        assertEquals(uniqueNextMeetingList, expectedUniqueNextMeetingList);
    }

    @Test
    public void setPersons_nullUniqueNextMeetingList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNextMeetingList.setNextMeetings((List<NextMeeting>) null));
    }

    @Test
    public void remove_nullNextMeeting_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNextMeetingList.remove(null));
    }

    @Test
    public void remove_nextMeetingDoesNotExist_throwsPersonNotFoundException() {
        assertThrows(MeetingNotFoundException.class, () -> uniqueNextMeetingList.remove(ALICE.getNextMeeting()));
    }

    @Test
    public void deleteByPersons_listWithClientsInMeetingList_deletesMatchingMeetings() {
        uniqueNextMeetingList.add(ALICE.getNextMeeting());
        List<Client> personList = Collections.singletonList(ALICE);
        uniqueNextMeetingList.deleteByClients(personList);
        UniqueNextMeetingList expectedUniqueNextMeetingsList = new UniqueNextMeetingList();
        assertEquals(expectedUniqueNextMeetingsList, uniqueNextMeetingList);
    }

    @Test
    public void deleteByPersons_listWithNoClientsInMeetingList_noChangesToMeetingsList() {
        uniqueNextMeetingList.add(ALICE.getNextMeeting());
        List<Client> personList = Collections.singletonList(BOB);
        uniqueNextMeetingList.deleteByClients(personList);
        UniqueNextMeetingList expectedUniqueNextMeetingsList = new UniqueNextMeetingList();
        expectedUniqueNextMeetingsList.add(ALICE.getNextMeeting());
        assertEquals(expectedUniqueNextMeetingsList, uniqueNextMeetingList);
    }

    @Test
    public void setNextMeetings_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueNextMeetingList.setNextMeetings((List<NextMeeting>) null));
    }

    @Test
    public void setNextMeetings_list_replacesOwnListWithProvidedList() {
        uniqueNextMeetingList.add(ALICE.getNextMeeting());
        List<NextMeeting> personList = Collections.singletonList(BOB.getNextMeeting());
        uniqueNextMeetingList.setNextMeetings(personList);
        UniqueNextMeetingList expectedUniqueNextMeetingsList = new UniqueNextMeetingList();
        expectedUniqueNextMeetingsList.add(BOB.getNextMeeting());
        assertEquals(expectedUniqueNextMeetingsList, uniqueNextMeetingList);
    }

    @Test
    public void setNextMeetings_listWithDuplicateNextMeetings_throwsDuplicateNextMeetingException() {
        List<NextMeeting> listWithDuplicateNextMeetings = Arrays.asList(ALICE.getNextMeeting(), ALICE.getNextMeeting());
        assertThrows(DuplicateNextMeetingException.class, ()
            -> uniqueNextMeetingList.setNextMeetings(listWithDuplicateNextMeetings));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, ()
            -> uniqueNextMeetingList.asUnmodifiableObservableList().remove(0));
    }

}
