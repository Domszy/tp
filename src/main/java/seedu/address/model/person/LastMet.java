package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LastMet {
    public static final String MESSAGE_CONSTRAINTS = "LastMet should be in the form of Year-Month-Day, "
        + "where Day, month and year should be numerical values.";
    public static final String VALIDATION_REGEX = "\\d{4}-(0[1-9]|1[0-2])-(0[1-9]|[12][0-9]|3[01])";

    public static final boolean IS_BLANK_VALUE_ALLOWED = true;
    public static final String DEFAULT_VALUE = "";

    public final LocalDate value;
    public final String dateInString;

    /**
     * Constructs an {@code Email}.
     *
     * @param lastMetDate date agent last meets a client
     */
    public LastMet(String lastMetDate) {

        requireNonNull(lastMetDate);
        checkArgument(isValidLastMet(lastMetDate), MESSAGE_CONSTRAINTS);
        dateInString = lastMetDate;

        if (lastMetDate.isEmpty()) {
            value = null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            value = LocalDate.parse(lastMetDate, formatter);
        }
    }

    /**
     * Returns if a given string is a valid LastMet.
     */
    public static boolean isValidLastMet(String test) {
        return IS_BLANK_VALUE_ALLOWED && test.isEmpty() || test.matches(VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return this.dateInString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof LastMet // instanceof handles nulls
            && value.equals(((LastMet) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
