package seedu.address.commons.mapper;

import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CLIENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENTPLAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPOSABLEINCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LASTMET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTMEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISKAPPETITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.client.Address;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.model.client.CurrentPlan;
import seedu.address.model.client.DisposableIncome;
import seedu.address.model.client.Email;
import seedu.address.model.client.IgnoreNullComparable;
import seedu.address.model.client.LastMet;
import seedu.address.model.client.Name;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.client.Phone;
import seedu.address.model.client.RiskAppetite;
import seedu.address.model.client.SortDirection;
import seedu.address.model.tag.Tag;

/**
 * Contains {@code Map} mapping {@code Prefix} to relevant properties
 */
public class PrefixMapper {
    // Client getter methods
    private static final Function<Client, ClientId> GET_CLIENTID = Client::getClientId;
    private static final Function<Client, Name> GET_NAME = Client::getName;
    private static final Function<Client, Phone> GET_PHONE = Client::getPhone;
    private static final Function<Client, Email> GET_EMAIL = Client::getEmail;
    private static final Function<Client, Address> GET_ADDRESS = Client::getAddress;
    private static final Function<Client, RiskAppetite> GET_RISKAPPETITE = Client::getRiskAppetite;
    private static final Function<Client, DisposableIncome> GET_DISPOSABLEINCOME = Client::getDisposableIncome;
    private static final Function<Client, LastMet> GET_LASTMET = Client::getLastMet;
    private static final Function<Client, NextMeeting> GET_NEXTMEETING = Client::getNextMeeting;
    private static final Function<Client, CurrentPlan> GET_CURRENTPLAN = Client::getCurrentPlan;

    // EditClientDescriptor setter methods
    private static final BiConsumer<EditClientDescriptor, Name> EDIT_SET_NAME = EditClientDescriptor::setName;
    private static final BiConsumer<EditClientDescriptor, Phone> EDIT_SET_PHONE = EditClientDescriptor::setPhone;
    private static final BiConsumer<EditClientDescriptor, Email> EDIT_SET_EMAIL = EditClientDescriptor::setEmail;
    private static final BiConsumer<EditClientDescriptor, Address> EDIT_SET_ADDRESS = EditClientDescriptor::setAddress;
    private static final BiConsumer<EditClientDescriptor, RiskAppetite> EDIT_SET_RISKAPPETITE =
            EditClientDescriptor::setRiskAppetite;
    private static final BiConsumer<EditClientDescriptor, DisposableIncome> EDIT_SET_DISPOSABLEINCOME =
            EditClientDescriptor::setDisposableIncome;
    private static final BiConsumer<EditClientDescriptor, LastMet> EDIT_SET_LASTMET =
            EditClientDescriptor::setLastMet;
    private static final BiConsumer<EditClientDescriptor, NextMeeting> EDIT_SET_NEXTMEETING =
            EditClientDescriptor::setNextMeeting;
    private static final BiConsumer<EditClientDescriptor, CurrentPlan> EDIT_SET_CURRENTPLAN =
            EditClientDescriptor::setCurrentPlan;

    // ParserUtil parser method
    private static final Function<String, ClientId> PARSE_CLIENTID =
            throwableFunctionWrapper(ParserUtil::parseClientId);
    private static final Function<String, Name> PARSE_NAME =
            throwableFunctionWrapper(ParserUtil::parseName);
    private static final Function<String, Phone> PARSE_PHONE =
            throwableFunctionWrapper(ParserUtil::parsePhone);
    private static final Function<String, Email> PARSE_EMAIL =
            throwableFunctionWrapper(ParserUtil::parseEmail);
    private static final Function<String, Address> PARSE_ADDRESS =
            throwableFunctionWrapper(ParserUtil::parseAddress);
    private static final Function<String, RiskAppetite> PARSE_RISKAPPETITE =
            throwableFunctionWrapper(ParserUtil::parseRiskAppetite);
    private static final Function<String, DisposableIncome> PARSE_DISPOSABLEINCOME =
            throwableFunctionWrapper(ParserUtil::parseDisposableIncome);
    private static final Function<String, LastMet> PARSE_LASTMET =
            throwableFunctionWrapper(ParserUtil::parseLastMet);
    private static final Function<String, NextMeeting> PARSE_NEXTMEETING =
            throwableFunctionWrapper(ParserUtil::parseNextMeeting);
    private static final Function<String, CurrentPlan> PARSE_CURRENTPLAN =
            throwableFunctionWrapper(ParserUtil::parseCurrentPlan);

    // PrefixMapperElement wrapping the different function together
    private static final PrefixMapperElement<ClientId> PME_CLIENTID = new PrefixMapperElement<>(GET_CLIENTID,
            null, PARSE_CLIENTID, "Client Id");
    private static final PrefixMapperElement<Name> PME_NAME = new PrefixMapperElement<>(GET_NAME,
            EDIT_SET_NAME, PARSE_NAME, "Name");
    private static final PrefixMapperElement<Phone> PME_PHONE = new PrefixMapperElement<>(GET_PHONE,
            EDIT_SET_PHONE, PARSE_PHONE, "Phone");
    private static final PrefixMapperElement<Email> PME_EMAIL = new PrefixMapperElement<>(GET_EMAIL,
            EDIT_SET_EMAIL, PARSE_EMAIL, "Email");
    private static final PrefixMapperElement<Address> PME_ADDRESS = new PrefixMapperElement<>(GET_ADDRESS,
            EDIT_SET_ADDRESS, PARSE_ADDRESS, "Address");
    private static final PrefixMapperElement<RiskAppetite> PME_RISKAPPETITE = new PrefixMapperElement<>(
            GET_RISKAPPETITE, EDIT_SET_RISKAPPETITE, PARSE_RISKAPPETITE, "Risk Appetite");
    private static final PrefixMapperElement<DisposableIncome> PME_DISPOSABLEINCOME = new PrefixMapperElement<>(
            GET_DISPOSABLEINCOME, EDIT_SET_DISPOSABLEINCOME,
            PARSE_DISPOSABLEINCOME, "Disposable Income");
    private static final PrefixMapperElement<LastMet> PME_LASTMET = new PrefixMapperElement<>(GET_LASTMET,
            EDIT_SET_LASTMET, PARSE_LASTMET, "Last Met");
    private static final PrefixMapperElement<NextMeeting> PME_NEXTMEETING = new PrefixMapperElement<>(GET_NEXTMEETING,
            EDIT_SET_NEXTMEETING, PARSE_NEXTMEETING, "Next Meeting");
    private static final PrefixMapperElement<CurrentPlan> PME_CURRENTPLAN = new PrefixMapperElement<>(GET_CURRENTPLAN,
            EDIT_SET_CURRENTPLAN, PARSE_CURRENTPLAN, "Current Plan");
    private static final PrefixMapperElement<Tag> PME_TAG = new PrefixMapperElement<>(null, null, null, "Tag");

    // Maps prefix with their respective functions
    private static final Map<Prefix, PrefixMapperElement<? extends IgnoreNullComparable<?>>> PREFIX_MAP = Map.ofEntries(
            Map.entry(PREFIX_CLIENTID, PME_CLIENTID),
            Map.entry(PREFIX_NAME, PME_NAME),
            Map.entry(PREFIX_PHONE, PME_PHONE),
            Map.entry(PREFIX_EMAIL, PME_EMAIL),
            Map.entry(PREFIX_ADDRESS, PME_ADDRESS),
            Map.entry(PREFIX_RISKAPPETITE, PME_RISKAPPETITE),
            Map.entry(PREFIX_DISPOSABLEINCOME, PME_DISPOSABLEINCOME),
            Map.entry(PREFIX_LASTMET, PME_LASTMET),
            Map.entry(PREFIX_NEXTMEETING, PME_NEXTMEETING),
            Map.entry(PREFIX_CURRENTPLAN, PME_CURRENTPLAN),
            Map.entry(PREFIX_TAG, PME_TAG)
    );

    public static String getName(Prefix prefix) {
        return PREFIX_MAP.get(prefix).name;
    }

    public static Function<Client, ? extends IgnoreNullComparable<?>> getAttributeFunction(Prefix prefix) {
        return PREFIX_MAP.get(prefix).getAttributeFunction;
    }

    public static BiConsumer<EditClientDescriptor, String> parseAndEditSetFunction(Prefix prefix) {
        return PREFIX_MAP.get(prefix).parseAndEditSet();
    }

    public static BiFunction<Client, Client, Integer> compareFunction(Prefix prefix, SortDirection sortDirection) {
        return PREFIX_MAP.get(prefix).compareFunction(sortDirection);
    }


    /**
     * Wrapper class to wrap the different function related to an attribute
     */
    private static class PrefixMapperElement<T extends IgnoreNullComparable<T>> {
        private final Function<Client, T> getAttributeFunction;
        private final BiConsumer<EditClientDescriptor, T> editSetFunction;
        private final Function<String, T> parseFunction;
        private final String name;

        private PrefixMapperElement(Function<Client, T> getAttributeFunction,
                                    BiConsumer<EditClientDescriptor, T> editSetFunction,
                                    Function<String, T> parseFunction,
                                    String name) {
            this.getAttributeFunction = getAttributeFunction;
            this.editSetFunction = editSetFunction;
            this.parseFunction = parseFunction;
            this.name = name;
        }

        /**
         * Returns a {@code BiConsumer<EditClientDescriptor, String} that takes a {@code EditClientDescriptor}
         * to set with the result parsed from {@code parse_attribute} with the given {@code String}
         */
        private BiConsumer<EditClientDescriptor, String> parseAndEditSet() {
            return (editClientDescriptor, s) ->
                    editSetFunction.accept(editClientDescriptor, parseFunction.apply(s));
        }

        /**
         * Returns an integer determined by the compareTo method based on the attribute of the two {@code Client}
         */
        private BiFunction<Client, Client, Integer> compareFunction(SortDirection direction) {
            return (x, y) -> getAttributeFunction.apply(x)
                    .compareWithDirection(getAttributeFunction.apply(y), direction);
        }

    }

    private static <T, R> Function<T, R> throwableFunctionWrapper(
            ThrowableFunction<T, R, ? extends Exception> throwableFunction) {
        return t -> {
            try {
                return throwableFunction.apply(t);
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        };
    }
}
