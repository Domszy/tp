package seedu.address.model.client.exceptions;

/**
 * Signals that the operation will result in duplicate next meeting (NextMeetings are considered duplicates if they
 * have the same field values).
 */
public class DuplicateNextMeetingException extends RuntimeException {
    public DuplicateNextMeetingException() {}
}
