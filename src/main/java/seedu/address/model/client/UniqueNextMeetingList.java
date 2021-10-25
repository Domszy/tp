package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.SortedList;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.client.exceptions.DuplicateNextMeetingException;
import seedu.address.model.client.exceptions.MeetingNotFoundException;

/**
 * A list of NextMeetings that enforces uniqueness between its elements and does not allow nulls.
 * A NextMeeting is considered unique by comparing using {@code NextMeeting#equals(NextMeeting)}. As such, adding and
 * updating of NextMeetings uses NextMeeting#equals(NextMeeting) for equality so as to ensure that the NextMeeting
 * being added or updated is unique in terms of identity in the UniqueNextMeetingList.
 * <p>
 * Supports a minimal set of list operations.
 *
 */
public class UniqueNextMeetingList implements Iterable<NextMeeting> {

    private final ObservableList<NextMeeting> internalList = FXCollections.observableArrayList();
    private final ObservableList<NextMeeting> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);
    private final SortedList<NextMeeting> internalSortedList = sortNextMeetings(internalList);
    private final Comparator<NextMeeting> nextMeetingComparator = (currentMeeting, nextMeeting) -> {
        return currentMeeting.date.compareTo(nextMeeting.date) != 0
                ? currentMeeting.date.compareTo(nextMeeting.date)
                : (currentMeeting.startTime.compareTo(nextMeeting.startTime) != 0
                ? currentMeeting.startTime.compareTo(nextMeeting.startTime)
                : (currentMeeting.endTime.compareTo(nextMeeting.endTime)));
    };

    /**
     * Returns true if the list contains an equivalent meeting as the given argument.
     */
    public boolean contains(NextMeeting toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains an equivalent meeting with the same client name as the given argument.
     */
    public boolean hasClient(Name toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(nextMeeting -> nextMeeting.getWithWho().equals(toCheck));
    }

    /**
     * Returns true if the list contains an equivalent meeting with the matching client name as the given argument.
     */
    public boolean removeByName(Name toRemove) {
        requireNonNull(toRemove);
        NextMeeting tempNextMeeting = this.getNextMeeting(toRemove);
        try {
            this.remove(tempNextMeeting);
        } catch (MeetingNotFoundException e) {
            return false;
        }
        return true;
    }

    /**
     * Adds a NextMeeting to the list.
     * The NextMeeting must not already exist in the list.
     */
    public void add(NextMeeting toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateNextMeetingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Returns the person with the corresponding client name.
     */
    public NextMeeting getNextMeeting(Name withWho) {
        ObservableList<NextMeeting> meetingInQuestion = internalList
                .filtered(meeting -> meeting.getWithWho().equals(withWho));
        if (meetingInQuestion.isEmpty()) {
            throw new MeetingNotFoundException();
        }
        return meetingInQuestion.get(0);
    }

    /**
     * Removes the equivalent meeting from the list.
     * The meeting must exist in the list.
     */
    public void remove(NextMeeting toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MeetingNotFoundException();
        }
    }

    /**
     * Replaces the contents of this list with {@code meetings}.
     * {@code meetings} must not contain duplicate nextMeetings.
     * @param meetings
     */
    public void setNextMeetings(List<NextMeeting> meetings) {
        requireAllNonNull(meetings);
        if (!meetingsAreUnique(meetings)) {
            throw new DuplicateNextMeetingException();
        }
        internalList.setAll(meetings);
    }

    /**
     * Replaces the contents of this list with {@code meetings}.
     * {@code meetings} must not contain duplicate nextMeetings.
     * @param clientNames
     * @param editedClientDescriptor
     */
    public void editClientNextMeeting(List<Name> clientNames,
        EditCommand.EditClientDescriptor editedClientDescriptor) {
        requireNonNull(clientNames);
        requireNonNull(editedClientDescriptor);

        clientNames.stream().forEach(name -> {
            //Removes old meeting
            if (this.hasClient(name)) {
                this.removeByName(name);
            }
            if (editedClientDescriptor.isNextMeetingEdited()) {
                NextMeeting tempNextMeeting = editedClientDescriptor.getNextMeeting().get();
                NextMeeting copyNextMeeting = tempNextMeeting.copyNextMeeting();
                copyNextMeeting.setWithWho(name);
                this.add(copyNextMeeting);
            }
        });
        this.internalSortedList.setComparator(nextMeetingComparator);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<NextMeeting> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    /**
     * Returns the backing list as an sorted {@code ObservableList}.
     */
    public ObservableList<NextMeeting> asSortedObservableList() {
        return internalSortedList;
    }

    /**
     * Deletes all meetings whose client name matches the names of person in {@code ObservableList}.
     */
    public void deleteByClients(List<Client> toDelete) {
        toDelete.stream().forEach((client) -> {
            if (client.getNextMeeting() != null) {
                if (contains(client.getNextMeeting())) {
                    this.remove(client.getNextMeeting());
                }
            }
        });
    }

    /**
     * Sorts all meetings according to date, start time and end time.
     */
    private SortedList<NextMeeting> sortNextMeetings(ObservableList<NextMeeting> allMeetings) {

        SortedList<NextMeeting> sortedMeetings = new SortedList<>(allMeetings);

        sortedMeetings.setComparator(nextMeetingComparator);
        return sortedMeetings;
    }

    @Override
    public Iterator<NextMeeting> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueNextMeetingList // instanceof handles nulls
                && internalList.equals(((UniqueNextMeetingList) other).internalList));
    }

    /**
     * Returns true if {@code meetings} contains only unique meetings.
     */
    private boolean meetingsAreUnique(List<NextMeeting> meetings) {
        Set<NextMeeting> set = new HashSet<>(meetings);
        return set.size() == meetings.size();
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }
}
