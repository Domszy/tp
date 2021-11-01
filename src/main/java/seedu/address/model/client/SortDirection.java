package seedu.address.model.client;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import seedu.address.logic.parser.exceptions.ParseException;

public class SortDirection {
    public static final String MESSAGE_CONSTRAINTS = "Sort direction should either be asc or dsc";
    public static final SortDirection SORT_ASCENDING = new SortDirection("asc");
    public static final SortDirection SORT_DESCENDING = new SortDirection("dsc");

    private final String direction;

    /**
     * @param direction to sort by. Either asc or dsc.
     */
    private SortDirection(String direction) {
        requireNonNull(direction);
        this.direction = direction;
    }

    /**
     * Returns a SortDirection if the given {@code direction} is valid
     *
     * @param direction a string representing the direction for sorting
     * @return SortDirection based on the {@code direction}
     */
    public static SortDirection of(String direction) throws ParseException {
        SortDirection result = null;
        if (direction.equals(SORT_ASCENDING.direction)) {
            result = SORT_ASCENDING;
        }

        if (direction.equals(SORT_DESCENDING.direction)) {
            result = SORT_DESCENDING;
        }

        checkArgument(result != null);
        return result;
    }

    /**
     * Returns true if a given string is a valid sorting direction.
     */
    public static boolean isValidDirection(String test) {
        return test.equalsIgnoreCase(SORT_ASCENDING.direction) || test.equalsIgnoreCase(SORT_DESCENDING.direction);
    }

    public boolean isAscending() {
        return this.equals(SORT_ASCENDING);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortDirection // instanceof handles nulls
                && direction.equals(((SortDirection) other).direction)); // state check
    }

    @Override
    public String toString() {
        switch (this.direction) {
        case "asc":
            return "ascending";

        case "dsc":
            return "descending";

        default:
            return "";
        }
    }
}
