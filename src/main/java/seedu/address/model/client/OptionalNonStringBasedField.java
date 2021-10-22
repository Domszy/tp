package seedu.address.model.client;

/**
 * Represents a optional client field whose stored value is not a string.
 */
public interface OptionalNonStringBasedField extends Field {

    boolean IS_BLANK_VALUE_ALLOWED = true;
    boolean IS_NULL_VALUE_ALLOWED = true;
    String DEFAULT_VALUE = "";
    boolean IS_EDITABLE = true;
}
