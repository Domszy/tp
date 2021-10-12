package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

public class DisposableIncome implements OptionalPersonStringField {
    public static final String MESSAGE_CONSTRAINTS =
            "Disposable Income numbers should be a positive integer only";
    public static final String VALIDATION_REGEX = "\\d{1,}";
    public final String value;

    /**
     * Constructs a {@code Risk Appetite}.
     *
     * @param disposableIncome A valid risk appetite number.
     */
    public DisposableIncome(String disposableIncome) {
        requireNonNull(disposableIncome);
        checkArgument(isValidDisposableIncome(disposableIncome), MESSAGE_CONSTRAINTS);
        value = disposableIncome;
    }

    /**
     * Returns true if a given string is a valid phone number.
     */
    public static boolean isValidDisposableIncome(String test) {

        return (IS_BLANK_VALUE_ALLOWED && test.isEmpty()) || test.matches(VALIDATION_REGEX);

    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DisposableIncome // instanceof handles nulls
                && value.equals(((DisposableIncome) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
