package seedu.address.model.client;

import static seedu.address.commons.mapper.PrefixMapper.compareFunction;
import static seedu.address.commons.mapper.PrefixMapper.getName;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.function.BiFunction;

import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.Prefix;

/**
 * Compare two {@code Client} based on {@code Prefix} and {@code SortDirection}
 * to determine their ordering
 */
public class SortByAttribute implements Comparator<Client> {

    private final List<Prefix> prefixList;
    private final List<SortDirection> sortDirectionList;

    /**
     * Returns a SortByAttribute object based on the list of {@code Prefix} and {@code SortDirection}
     */
    public SortByAttribute(List<Prefix> prefixList, List<SortDirection> sortDirectionList) {
        this.prefixList = prefixList;
        this.sortDirectionList = sortDirectionList;
    }

    public SortByAttribute(Prefix prefix, SortDirection sortDirection) {
        this(List.of(prefix), List.of(sortDirection));
    }

    public SortByAttribute(Prefix prefix) {
        this(prefix, SortDirection.SORT_ASCENDING);
    }

    @Override
    public String toString() {
        Iterator<Prefix> prefixIterator = this.prefixList.listIterator();
        Iterator<SortDirection> sortDirectionIterator = this.sortDirectionList.iterator();
        List<String> resultList = new ArrayList<>();
        while (prefixIterator.hasNext() && sortDirectionIterator.hasNext()) {
            resultList.add(getName(prefixIterator.next()) + " in " + sortDirectionIterator.next().toString());
        }

        return StringUtil.joinListToString(resultList, StringUtil.COMMA_DELIMITER);
    }

    /**
     * {@see thenCompareByAttribute}
     */
    public SortByAttribute thenCompareByAttribute(Prefix prefix) {
        return this.thenCompareByAttribute(prefix, SortDirection.SORT_ASCENDING);
    }

    /**
     * Returns a new SortByAttribute object that will compare by {@code Prefix} and {@code SortDirection} next.
     */
    public SortByAttribute thenCompareByAttribute(Prefix prefix, SortDirection sortDirection) {
        List<Prefix> newPrefixList = new ArrayList<>(this.prefixList);
        List<SortDirection> newSortDirectionList = new ArrayList<>(this.sortDirectionList);

        newPrefixList.add(prefix);
        newSortDirectionList.add(sortDirection);

        return new SortByAttribute(newPrefixList, newSortDirectionList);
    }

    @Override
    public int compare(Client a, Client b) {
        int result = 0;
        Iterator<Prefix> prefixIterator = this.prefixList.listIterator();
        Iterator<SortDirection> sortDirectionIterator = this.sortDirectionList.iterator();
        while (result == 0 && prefixIterator.hasNext() && sortDirectionIterator.hasNext()) {
            Prefix prefix = prefixIterator.next();
            SortDirection sortDirection = sortDirectionIterator.next();

            BiFunction<Client, Client, Integer> compareFunction = compareFunction(prefix, sortDirection);
            result = compareFunction.apply(a, b);
        }
        return result;
    }
}
