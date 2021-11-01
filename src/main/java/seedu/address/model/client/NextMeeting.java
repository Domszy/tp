package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.commons.util.StringUtil.convertEmptyStringIfNull;
import static seedu.address.commons.util.StringUtil.isValidDate;
import static seedu.address.commons.util.StringUtil.isValidTime;
import static seedu.address.commons.util.StringUtil.parseToLocalDate;
import static seedu.address.commons.util.StringUtil.parseToLocalTime;

import java.time.LocalDate;
import java.time.LocalTime;

public class NextMeeting implements OptionalNonStringBasedField, IgnoreNullComparable<NextMeeting> {

    public static final String DATE_MESSAGE_CONSTRAINTS = "Next meeting date should be in the form of Day-Month-Year, "
            + "where Day, month and year should be numerical values.";
    public static final String TIME_MESSAGE_CONSTRAINTS = "Next meeting time should be in the 24-hour format, "
            + "where Hour and Minutes should be numerical values.";
    public static final String MESSAGE_INVALID_MEETING_STRING = "String representation of Next Meeting is not correct";
    public static final String MESSAGE_INVALID_TIME_DURATION = "End Time should be after Start Time";
    public static final String MESSAGE_INVALID_MEETING_DATE_OVER = "NextMeeting should not be in the past";
    public static final String NO_NEXT_MEETING = "No meeting planned";
    public static final NextMeeting NULL_MEETING = new NextMeeting(null, null, null,
        null, null);

    public static final String VALID_MEETING_STRING =
            "([0-9]{2})-([0-9]{2})-([0-9]{4}) \\(([0-9]{2}):([0-9]{2})~([0-9]{2}):([0-9]{2})\\),(.|\\s)*\\S(.|\\s)*";

    public final LocalDate date;
    public final LocalTime startTime;
    public final LocalTime endTime;

    public final String dateInString;
    public final String startTimeInString;
    public final String endTimeInString;
    public final String location;
    private Name withWho;

    /**
     * Constructs a {@code NextMeeting}.
     *
     * @param date date agent next meets a client
     */
    public NextMeeting(String date, String startTime, String endTime, String location, String withWho) {
        if (!IS_NULL_VALUE_ALLOWED) {
            requireAllNonNull(date, startTime, endTime, location, withWho);
        }

        date = convertEmptyStringIfNull(date);
        startTime = convertEmptyStringIfNull(startTime);
        endTime = convertEmptyStringIfNull(endTime);
        this.location = convertEmptyStringIfNull(location);
        withWho = convertEmptyStringIfNull(withWho);

        checkArgument(isValidDate(date), DATE_MESSAGE_CONSTRAINTS);
        dateInString = date;

        checkArgument(isValidTime(startTime), TIME_MESSAGE_CONSTRAINTS);
        startTimeInString = startTime;

        checkArgument(isValidTime(endTime), TIME_MESSAGE_CONSTRAINTS);
        endTimeInString = endTime;

        this.withWho = withWho.isEmpty() ? null : new Name(withWho);

        this.date = parseToLocalDate(date);
        this.startTime = parseToLocalTime(startTime);
        this.endTime = parseToLocalTime(endTime);

        if (!startTime.isEmpty() && !endTime.isEmpty()) {
            checkArgument(this.endTime.isAfter(this.startTime), MESSAGE_INVALID_TIME_DURATION);
        }

        if (!date.isEmpty() && !startTime.isEmpty() && !endTime.isEmpty()) {
            checkArgument(!isMeetingOver(LocalDate.now(), LocalTime.now()), MESSAGE_INVALID_MEETING_DATE_OVER);
        }
    }

    public Name getWithWho() {
        return this.withWho;
    }

    public void setWithWho(Name withWho) {
        this.withWho = withWho;
    }

    public String getClientName() {
        return this.withWho.fullName;
    }

    public boolean isNullMeeting() {
        return this.equals(NULL_MEETING);
    }

    public static NextMeeting getNullMeeting() {
        return NULL_MEETING;
    }

    public LocalDate getDate() {
        return date;
    }

    public static boolean isValidNextMeeting(String test) {
        return test.matches(VALID_MEETING_STRING);
    }

    public boolean isSameDay(LocalDate comparison) {
        return comparison.equals(this.date);
    }

    /**
     * Returns the a boolean of whether this {@code NextMeeting} falls before
     * the given {@code checkDate} and {@code checkTime}
     */
    public boolean isMeetingOver(LocalDate checkDate, LocalTime checkTime) {
        if (date == null || endTime == null) {
            return false;
        }
        return date.isBefore(checkDate) || date.isEqual(checkDate) && endTime.isBefore(checkTime);
    }

    /**
     * Converts this {@code NextMeeting} to a {@code LastMet}
     */
    public LastMet convertToLastMet() {
        requireNonNull(date);
        return new LastMet(dateInString);
    }

    /**
     * Returns a new Next Meeting with the same details
     */
    public NextMeeting copyNextMeeting() {
        String newName = withWho != null ? withWho.fullName : "";
        return new NextMeeting(dateInString, startTimeInString, endTimeInString, location, newName);
    }

    @Override
    public String toString() {
        if (date == null) {
            return NO_NEXT_MEETING;
        }
        return String.format("%s (%s~%s), %s", dateInString, startTimeInString, endTimeInString, location);

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof NextMeeting // instanceof handles nulls
            && dateInString.equals(((NextMeeting) other).dateInString) // state check
            && startTimeInString.equals(((NextMeeting) other).startTimeInString)
            && endTimeInString.equals(((NextMeeting) other).endTimeInString)
            && location.equals(((NextMeeting) other).location)
                && ((withWho == null && ((NextMeeting) other).withWho == null)
                || withWho.equals(((NextMeeting) other).withWho)));
    }

    @Override
    public int compareWithDirection(NextMeeting o, SortDirection sortDirection) {
        if (this.equals(NULL_MEETING) && o.equals(NULL_MEETING)) {
            return 0;
        }

        if (o.equals(NULL_MEETING)) {
            return -1;
        }

        if (this.equals(NULL_MEETING)) {
            return 1;
        }

        NextMeeting x = sortDirection.isAscending() ? this : o;
        NextMeeting y = sortDirection.isAscending() ? o : this;

        int compareDate = x.date.compareTo(y.date);
        if (compareDate != 0) {
            return compareDate;
        }
        int compareStartTime = x.startTime.compareTo(y.startTime);
        int compareEndTime = x.endTime.compareTo(y.endTime);

        return compareStartTime != 0 ? compareStartTime : compareEndTime;
    }
}
