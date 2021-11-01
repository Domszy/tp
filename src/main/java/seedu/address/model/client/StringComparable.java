package seedu.address.model.client;

/**
 * An abstract class to allow the class extending it to be comparable by their String representation.
 */
public abstract class StringComparable<T> implements IgnoreNullComparable<T> {
    @Override
    public int compareWithDirection(T other, SortDirection sortDirection) {
        String a = this.toString();
        String b = other.toString();
        if (a.isEmpty() && b.isEmpty()) {
            return 0;
        }

        if (b.isEmpty()) {
            return -1;
        }

        if (a.isEmpty()) {
            return 1;
        }

        int direction = sortDirection.isAscending() ? 1 : -1;
        return direction * a.compareToIgnoreCase(b);
    }
}
