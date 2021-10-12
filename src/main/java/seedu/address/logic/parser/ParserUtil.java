package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.CurrentPlan;
import seedu.address.model.person.DisposableIncome;
import seedu.address.model.person.Email;
import seedu.address.model.person.LastMet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RiskAppetite;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_CLIENT_ID = "Client ID is not a non-negative unsigned integer.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses {@code clientId} into an {@code ClientId} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-negative integer).
     */
    public static ClientId parseClientId(String clientId) throws ParseException {
        String trimmedId = clientId.trim();
        if (!StringUtil.isNonNegativeInteger(trimmedId)) {
            throw new ParseException(MESSAGE_INVALID_CLIENT_ID);
        }
        return new ClientId(trimmedId);
    }
    /**
     * Parses {@code clientId} into an {@code Index} and returns it. Leading and trailing whitespaces will be
     * trimmed.
     *
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseClientIndex(String clientId) throws ParseException {
        String trimmedIndex = clientId.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromZeroBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.trim();
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String address} into an {@code Address}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code address} is invalid.
     */
    public static Address parseAddress(String address) throws ParseException {
        requireNonNull(address);
        String trimmedAddress = address.trim();
        if (!Address.isValidAddress(trimmedAddress)) {
            throw new ParseException(Address.MESSAGE_CONSTRAINTS);
        }
        return new Address(trimmedAddress);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.trim();
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String current plan} into an {@code CurrentPlan}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code currentPlan} is invalid.
     */
    public static CurrentPlan parseCurrentPlan(String currentPlan) throws ParseException {
        requireNonNull(currentPlan);
        String trimmedCurrentPlan = currentPlan.trim();
        if (!CurrentPlan.isValidCurrentPlan(trimmedCurrentPlan)) {
            throw new ParseException(CurrentPlan.MESSAGE_CONSTRAINTS);
        }
        return new CurrentPlan(trimmedCurrentPlan);
    }

    /**
     * Parses a {@code String LastMet} into an {@code LastMet}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code LastMet} is invalid.
     */
    public static LastMet parseLastMet(String lastMet) throws ParseException {
        requireNonNull(lastMet);
        String trimmedLastMet = lastMet.trim();
        if (!LastMet.isValidLastMet(trimmedLastMet)) {
            throw new ParseException(LastMet.MESSAGE_CONSTRAINTS);
        }
        return new LastMet(trimmedLastMet);
    }

    /** Parses a {@code String RiskAppetite} into an {@code RiskAppetite}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code RiskAppetite} is invalid.
     */
    public static RiskAppetite parseRiskAppetite(String riskAppetite) throws ParseException {
        requireNonNull(riskAppetite);
        String trimmedRiskAppetite = riskAppetite.trim();
        if (!RiskAppetite.isValidRiskAppetite(trimmedRiskAppetite)) {
            throw new ParseException(RiskAppetite.MESSAGE_CONSTRAINTS);
        }
        return new RiskAppetite(trimmedRiskAppetite);
    }

    /**
     * Parses a {@code String disposableIncome} into an {@code disposableIncome}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code DisposableIncome} is invalid.
     */
    public static DisposableIncome parseDisposableIncome(String disposableIncome) throws ParseException {
        requireNonNull(disposableIncome);
        String trimmedDisposableIncome = disposableIncome.trim();
        if (!DisposableIncome.isValidDisposableIncome(trimmedDisposableIncome)) {
            throw new ParseException(DisposableIncome.MESSAGE_CONSTRAINTS);
        }
        return new DisposableIncome(trimmedDisposableIncome);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }
}
