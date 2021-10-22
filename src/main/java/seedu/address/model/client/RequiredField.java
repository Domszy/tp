package seedu.address.model.client;

/**
 * Represents a required client field.
 */
public interface RequiredField extends Field {
    boolean IS_BLANK_VALUE_ALLOWED = false;
    boolean IS_NULL_VALUE_ALLOWED = false;
    String DEFAULT_VALUE = "";
    boolean IS_EDITABLE = true;
}
