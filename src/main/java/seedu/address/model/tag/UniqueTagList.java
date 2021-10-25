package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.model.tag.exceptions.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

public class UniqueTagList implements Iterable<Tag> {

    private final ObservableList<Tag> internalList = FXCollections.observableArrayList();
    private final ObservableList<Tag> internalUnmodifiableList = FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains {@code toCheck}.
     *
     * @param toCheck Tag to be checked.
     * @return true if the list contains {@code toCheck}.
     */
    public boolean contains(Tag toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTag);
    }

    /**
     * Returns true if a tag with the given tagName exists.
     *
     * @param tagName name of the tag
     * @return true if a tag with the tagName exists
     */
    public boolean hasTagName(String tagName) {
        ObservableList<Tag> tagInQuestion = internalList.filtered(tag -> tag.getName().equals(tagName));
        return !tagInQuestion.isEmpty();
    }

    /**
     * Adds {@code toAdd} to the list.
     *
     * @param toAdd Tag to be added.
     */
    public void add(Tag toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTagException();
        }
        internalList.add(toAdd);
    }

    /**
     * Removes the tags based on predicates from the list.
     * The tags must exist in the list.
     */
    public FilteredList<Tag> removeByFields(List<Predicate<Tag>> predicates) {
        requireAllNonNull(predicates);
        Predicate<Tag> predicate = predicates.stream().reduce(x -> true, Predicate::and);
        FilteredList<Tag> filteredList = internalList.filtered(predicate);
        if (filteredList.size() < 1) {
            throw new TagNotFoundException();
        } else {
            filteredList.forEach(internalList::remove);
            return filteredList;
        }
    }

    /**
     * Returns the tag with the corresponding {@code tagName}
     */
    public Tag getTag(String tagName) {
        ObservableList<Tag> tagInQuestion = internalList.filtered(tag -> tag.getName().equals(tagName));
        if (tagInQuestion.isEmpty()) {
            throw new TagNotFoundException();
        }
        return tagInQuestion.get(0);
    }

    public ObservableList<Tag> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Tag> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof UniqueTagList // instanceof handles nulls
            && internalList.equals(((UniqueTagList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

}
