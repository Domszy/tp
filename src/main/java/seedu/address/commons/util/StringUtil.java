package seedu.address.commons.util;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.client.OptionalNonStringBasedField.IS_NULL_VALUE_ALLOWED;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

/**
 * Helper functions for handling strings.
 */
public class StringUtil {
    public static final String DATE_VALIDATION_REGEX =
            "^([1-2][0-9]|3[0-1]|0?[1-9])[-]([1][0-2]|0?[1-9])[-](\\d{4})";
    public static final String TIME_VALIDATION_REGEX =
            "([01]?[0-9]|2[0-3]):[0-5][0-9]";
    public static final String CLIENT_DELIMITER = "\n";
    public static final String CLIENTID_DELIMITER = ", ";
    public static final String JSON_FILE_PREFIX = ".json";

    /**
     * Returns true if the {@code sentence} contains the {@code word}.
     * Ignores case, but a full word match is required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("ABc def", "AB") == false //not a full word match
     *       </pre>
     *
     * @param sentence cannot be null
     * @param word     cannot be null, cannot be empty, must be a single word
     */
    public static boolean containsWordIgnoreCase(String sentence, String word) {
        requireNonNull(sentence);
        requireNonNull(word);

        String preppedWord = word.trim();
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");
        checkArgument(preppedWord.split("\\s+").length == 1, "Word parameter should be a single word");

        String preppedSentence = sentence;
        String[] wordsInPreppedSentence = preppedSentence.split("\\s+");

        return Arrays.stream(wordsInPreppedSentence)
                .anyMatch(preppedWord::equalsIgnoreCase);
    }

    /**
     * Returns true if the {@code sentence} contains the {@code string}.
     * Ignores case, a full word match is not required.
     * <br>examples:<pre>
     *       containsWordIgnoreCase("ABc def", "abc") == true
     *       containsWordIgnoreCase("ABc def", "DEF") == true
     *       containsWordIgnoreCase("A Bc def", "A B") == true
     *       </pre>
     *
     * @param sentence cannot be null
     * @param string   cannot be null, cannot be empty
     */
    public static boolean containsStringIgnoreCase(String sentence, String string) {
        requireNonNull(sentence);
        requireNonNull(string);

        String preppedWord = string;
        checkArgument(!preppedWord.isEmpty(), "Word parameter cannot be empty");

        return sentence.toLowerCase().contains(string.toLowerCase());
    }

    /**
     * Returns a detailed message of the t, including the stack trace.
     */
    public static String getDetails(Throwable t) {
        requireNonNull(t);
        StringWriter sw = new StringWriter();
        t.printStackTrace(new PrintWriter(sw));
        return t.getMessage() + "\n" + sw.toString();
    }

    /**
     * Returns true if {@code s} represents a non-zero unsigned integer
     * e.g. 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "0", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonZeroUnsignedInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value > 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if {@code s} represents a non-negative integer
     * e.g. 0, 1, 2, 3, ..., {@code Integer.MAX_VALUE} <br>
     * Will return false for any other non-null string input
     * e.g. empty string, "-1", "+1", and " 2 " (untrimmed), "3 0" (contains whitespace), "1 a" (contains letters)
     *
     * @throws NullPointerException if {@code s} is null.
     */
    public static boolean isNonNegativeInteger(String s) {
        requireNonNull(s);

        try {
            int value = Integer.parseInt(s);
            return value >= 0 && !s.startsWith("+"); // "+1" is successfully parsed by Integer#parseInt(String)
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Returns true if a {@code date} is a valid date.
     * A valid date is in the form of DD/MM/YYYY.
     */
    public static boolean isValidDate(String date) {
        return (IS_NULL_VALUE_ALLOWED && date.isEmpty())
                || date.matches(DATE_VALIDATION_REGEX);
    }

    /**
     * Returns true if a {@code time} is a valid time in the 24hr format.
     * A valid time is in the form of HH:MM.
     */
    public static boolean isValidTime(String time) {
        return (IS_NULL_VALUE_ALLOWED && time.isEmpty())
                || time.matches(TIME_VALIDATION_REGEX);
    }

    /**
     * Returns {@code LocalDate} from a valid date.
     * To use after isValidDate.
     */
    public static LocalDate parseToLocalDate(String date) {
        if (date.isEmpty()) {
            return null;
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            return LocalDate.parse(date, formatter);
        }
    }

    /**
     * Returns {@code LocalTime} from a valid time in the 24hr format.
     * To use after isValidTime.
     */
    public static LocalTime parseToLocalTime(String time) {
        if (time.isEmpty()) {
            return null;
        } else {
            return LocalTime.parse(time);
        }
    }

    /**
     * Checks if {@code s} is null.
     * Returns empty string if null, otherwise returns s.
     */
    public static String convertEmptyStringIfNull(String s) {
        if (s == null) {
            return "";
        }
        return s;
    }

    /**
     * Joins the {@code list} into a single string separated by the delimiter.
     *
     * @param list      list of object to be joined to {@code String}
     * @param delimiter the delimiter that separates each element
     */
    public static <T> String joinListToString(List<T> list, String delimiter) {
        requireNonNull(list);
        requireNonNull(delimiter);
        String[] stringArray = list.stream().map(Object::toString).toArray(String[]::new);
        return String.join(delimiter, stringArray);
    }


    /**
     * Removes the {@code suffix} from {@code word} if it exists and returns it.
     * If {@code suffix} does not exists, then {@code word} will just be returned.
     *
     * @param word the string to remove the suffix from
     * @param suffix the suffix that is to be removed
     */
    public static String getStringWithoutSuffix(String word, String suffix) {
        requireNonNull(word);
        requireNonNull(suffix);
        if (!word.endsWith(suffix)) {
            return word;
        }

        return word.substring(0, word.length() - suffix.length());
    }
}
