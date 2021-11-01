package seedu.address.model.tag;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.client.Client;
import seedu.address.model.client.StringComparable;

/**
 * Represents a Tag in the address book.
 * Guarantees: immutable; name is valid as declared in {@link #isValidTagName(String)}
 */
public class Tag extends StringComparable<Tag> {

    public static final String MESSAGE_CONSTRAINTS = "Tags names should be alphanumeric";
    public static final String VALIDATION_REGEX = "\\p{Alnum}+";

    // TODO: tag color + FXML
    private final String tagName;
    private final ObservableList<Client> clients = FXCollections.observableArrayList();
    private final IntegerProperty version = new SimpleIntegerProperty();

    /**
     * Constructs a {@code Tag}.
     *
     * @param tagName A valid tag name.
     */
    public Tag(String tagName) {
        requireNonNull(tagName);
        checkArgument(isValidTagName(tagName), MESSAGE_CONSTRAINTS);
        this.tagName = tagName;
    }

    /**
     * Returns true if a given string is a valid tag name.
     */
    public static boolean isValidTagName(String test) {
        return test.matches(VALIDATION_REGEX);
    }

    /**
     * Adds {@code Client} to the list of clients this tag is assigned to.
     *
     * @param client The client to be added to the list.
     */
    public void addClient(Client client) {
        requireNonNull(client);
        clients.add(client);
        forceUpdate();
    }

    /**
     * Returns the version property of the tag.
     */
    public final IntegerProperty versionProperty() {
        return version;
    }

    /**
     * Returns the version of the tag.
     */
    public final int getVersion() {
        return versionProperty().get();
    }

    /**
     * Sets the version of the tag.
     */
    public final void setVersion(int value) {
        versionProperty().set(value);
    }

    /**
     * Forces the enclosing list to fire an update.
     */
    public void forceUpdate() {
        int nextVersion = getVersion();
        setVersion(++nextVersion);
    }

    /**
     * Removes {@code Client} from the list of clients this tag is assigned to.
     *
     * @param client The client to be removed from the list.
     */
    public void removeClient(Client client) {
        clients.remove(client);
        forceUpdate();
    }

    /**
     * Returns true if both tags have the same name.
     * This defines a weaker notion of equality between two tags.
     */
    public boolean isSameTag(Tag otherTag) {
        if (otherTag == this) {
            return true;
        }

        return otherTag != null && otherTag.getName().equals(getName());
    }

    /**
     * Returns the name of this tag.
     *
     * @return The name of this tag.
     */
    public String getName() {
        return tagName;
    }

    /**
     * Returns a list of clients that have this tag.
     *
     * @return A list of clients that have this tag.
     */
    public ObservableList<Client> getClients() {
        return FXCollections.unmodifiableObservableList(clients);
    }

    @Override
    public int hashCode() {
        return tagName.hashCode();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof Tag // instanceof handles nulls
            && tagName.equals(((Tag) other).tagName)); // state check
    }

    /**
     * Format state as text for viewing.
     */
    @Override
    public String toString() {
        return tagName;
        // TODO: fix client count not accurate
        // + " (" + clients.size() + ")";
    }
}
