package seedu.address.model.client;

/**
 * Represents an optional client field whose stored value is not a string.
 */
public interface Field {
    /**
     * If set to true, the field is allowed to be blank (for string fields such as phone, name, etc)
     */
    boolean IS_BLANK_VALUE_ALLOWED = true;

    /**
     * If set to true, the field is allowed to be null (for int/Date fields such as LastMet, etc)
     */
    boolean IS_NULL_VALUE_ALLOWED = true;

    /**
     * The default value for the field. Set when user does not pass in the prefix on Client creation.
     */
    String DEFAULT_VALUE = "";

    /**
     * If set to true, the field is editable by the user through edit command.
     */
    boolean IS_EDITABLE = true;
}
