package seedu.address.model.client;

/**
 * An abstract class to allow the class extending it to be comparable
 * by the parsed number of the string representation.
 */
public abstract class NumberComparable<T> implements IgnoreNullComparable<T> {
    @Override
    public int compareWithDirection(T other, SortDirection sortDirection) {
        int a;
        int b;

        if (this.toString().isEmpty() && other.toString().isEmpty()) {
            return 0;
        }

        try {
            b = Integer.parseInt(other.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return -1;
        }

        try {
            a = Integer.parseInt(this.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return 1;
        }

        int direction = sortDirection.isAscending() ? 1 : -1;
        return direction * Integer.compare(a, b);
    }
}
