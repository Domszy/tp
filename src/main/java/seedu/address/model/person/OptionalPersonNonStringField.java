package seedu.address.model.person;

/**
 * Represents a optional client field whose stored value is not a string.
 */
public interface OptionalPersonNonStringField {

    /**
     * Person field is allowed to be blank
     */
    public static final boolean IS_NULL_VALUE_ALLOWED = true;

    /**
     * Default string value of optional client field
     */
    public static final String DEFAULT_VALUE = "";

}
