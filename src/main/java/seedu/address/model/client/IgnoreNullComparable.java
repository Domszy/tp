package seedu.address.model.client;

import org.jetbrains.annotations.NotNull;

/**
 * This interface ensures a class {@code T} to be comparable according to a {@code SortDirection}
 * where null or the class null equivalent will inherently be positioned last.
 */
public interface IgnoreNullComparable<T> extends Comparable<T> {
    @Override
    default int compareTo(@NotNull T o) {
        return this.compareWithDirection(o, SortDirection.SORT_ASCENDING);
    }

    int compareWithDirection(T o, SortDirection sortDirection);
}
