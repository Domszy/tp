package seedu.address.model.person;

/**
 * Represents a optional client field whose value is stored as a string.
 */
public interface OptionalPersonStringField {

    /**
     * String field is allowed to be blank
     */
    public static final boolean IS_BLANK_VALUE_ALLOWED = true;

    /**
     * Default String value of optional client field
     */
    public static final String DEFAULT_VALUE = "";
}
