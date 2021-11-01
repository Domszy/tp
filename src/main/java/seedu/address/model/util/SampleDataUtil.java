package seedu.address.model.util;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.CurrentPlan;
import seedu.address.model.client.DisposableIncome;
import seedu.address.model.client.Email;
import seedu.address.model.client.LastMet;
import seedu.address.model.client.Name;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.client.Phone;
import seedu.address.model.client.RiskAppetite;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public static Client[] getSampleClients(AddressBook addressBook) throws ParseException {
        return new Client[] {
            new Client(new ClientId("0"), new Name("Alex Yeoh"), new Phone("87438807"),
                new Email("alexyeoh@example.com"), new Address("Blk 30 Geylang Street 29, #06-40"),
                new RiskAppetite("1"), new DisposableIncome("500"),
                new CurrentPlan(
                    "Prudential PRUwealth, AIA Pro Achiever 2.0, Syfe Select, a lot more plans......."
                        + ", Even more plans................................."),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "Alex Yeoh"),
                getTagSet(addressBook, "friends")),
            new Client(new ClientId("1"), new Name("Bernice Yu"), new Phone("99272758"),
                new Email("berniceyu@example.com"), new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                new RiskAppetite("1"), new DisposableIncome("500"), new CurrentPlan("Prudential PRUwealth"),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "Bernice Yu"),
                getTagSet(addressBook, "colleagues", "friends")),
            new Client(new ClientId("2"), new Name("Charlotte Oliveiro"), new Phone("93210283"),
                new Email("charlotte@example.com"), new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                new RiskAppetite("1"), new DisposableIncome("500"), new CurrentPlan("Prudential PRUwealth"),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "Charlotte Oliveiro"),
                getTagSet(addressBook, "neighbours")),
            new Client(new ClientId("3"), new Name("David Li"), new Phone("91031282"),
                new Email("lidavid@example.com"), new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                new RiskAppetite("1"), new DisposableIncome("500"), new CurrentPlan("Prudential PRUwealth"),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "David Li"),
                getTagSet(addressBook, "family")),
            new Client(new ClientId("4"), new Name("Irfan Ibrahim"), new Phone("92492021"),
                new Email("irfan@example.com"), new Address("Blk 47 Tampines Street 20, #17-35"),
                new RiskAppetite("1"), new DisposableIncome("500"), new CurrentPlan("Prudential PRUwealth"),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "Irfan Ibrahim"),
                getTagSet(addressBook, "classmates")),
            new Client(new ClientId("5"), new Name("Roy Balakrishnan"), new Phone("92624417"),
                new Email("royb@example.com"), new Address("Blk 45 Aljunied Street 85, #11-31"),
                new RiskAppetite("1"),
                new DisposableIncome("500"), new CurrentPlan("Prudential PRUwealth"),
                new LastMet("24-01-2021"),
                new NextMeeting("25-12-2021", "08:00", "09:00", "Starbucks @ UTown", "Roy Balakrishnan"),
                getTagSet(addressBook, "colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        try {
            for (Client sampleClient : getSampleClients(sampleAb)) {
                sampleAb.addClient(sampleClient);
            }
        } catch (ParseException e) {
            logger.warning("Sample data file not in the correct format. Will be starting with an empty AddressBook");
            return new AddressBook();
        }

        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
            .map(Tag::new)
            .collect(Collectors.toSet());
    }

    /**
     * Returns and stores a tag set containing the list of strings given into the {@code addressBook}
     */
    public static Set<Tag> getTagSet(AddressBook addressBook, String... strings) throws ParseException {
        return parseAndStoreTags(Arrays.asList(strings), addressBook);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    private static Tag parseTag(String tag) throws ParseException {
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
    private static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }

        return tagSet;
    }

    /**
     * Parses a {@code String tag} into a {@code Tag} and store it into {@code addressBook}.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    private static Tag parseAndStoreTag(String tag, AddressBook addressBook) throws ParseException {
        if (addressBook.hasTagName(tag)) {
            return addressBook.getTag(tag);
        } else {
            Tag newTag = parseTag(tag);
            addressBook.addTag(newTag);
            return newTag;
        }
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}
     * and stores them into the given {@code addressBook}.
     */
    private static Set<Tag> parseAndStoreTags(Collection<String> tags, AddressBook addressBook) throws ParseException {
        requireNonNull(tags);

        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseAndStoreTag(tagName, addressBook));
        }

        return tagSet;
    }
}
