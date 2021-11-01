package seedu.address.model.client;

/**
 * Represents an optional client field whose value is stored as a string.
 */
public interface OptionalStringBasedField extends Field {

    boolean IS_BLANK_VALUE_ALLOWED = true;
    boolean IS_NULL_VALUE_ALLOWED = false;
    String DEFAULT_VALUE = "";
    boolean IS_EDITABLE = true;
}
