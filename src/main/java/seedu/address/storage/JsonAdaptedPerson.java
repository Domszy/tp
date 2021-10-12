package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.CurrentPlan;
import seedu.address.model.person.DisposableIncome;
import seedu.address.model.person.Email;
import seedu.address.model.person.LastMet;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RiskAppetite;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";

    private final String clientId;
    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final String riskAppetite;
    private final String disposableIncome;
    private final String lastMet;
    private final String currentPlan;
    private final List<JsonAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("clientId") String clientId, @JsonProperty("name") String name,
                             @JsonProperty("phone") String phone, @JsonProperty("email") String email,
                             @JsonProperty("address") String address,
                             @JsonProperty("riskAppetite") String riskAppetite,
                             @JsonProperty("disposableIncome") String disposableIncome,
                             @JsonProperty("current-plan") String currentPlan,
                             @JsonProperty("last-met") String lastMet,
                             @JsonProperty("tagged") List<JsonAdaptedTag> tagged) {

        this.clientId = clientId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.riskAppetite = riskAppetite;
        this.disposableIncome = disposableIncome;
        this.lastMet = lastMet;
        this.currentPlan = currentPlan;
        if (tagged != null) {
            this.tagged.addAll(tagged);
        }
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        clientId = source.getClientId().value;
        name = source.getName().fullName;
        email = source.getEmail().value;
        phone = source.getPhone().value;
        address = source.getAddress().value;
        disposableIncome = source.getDisposableIncome().value;
        riskAppetite = source.getRiskAppetite().value;
        currentPlan = source.getCurrentPlan().value;
        lastMet = source.getLastMet().dateInString;
        tagged.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (clientId == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }

        final ClientId modelClientId = new ClientId(clientId);

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);


        if (lastMet == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, LastMet.class.getSimpleName()));
        }
        if (!LastMet.isValidLastMet(lastMet)) {
            throw new IllegalValueException(LastMet.MESSAGE_CONSTRAINTS);
        }
        final LastMet modelLastMet = new LastMet(lastMet);

        if (currentPlan == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    CurrentPlan.class.getSimpleName()));
        }
        final CurrentPlan modelCurrentPlan = new CurrentPlan(currentPlan);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }

        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }

        final Phone modelPhone = new Phone(phone);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }

        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }

        final Address modelAddress = new Address(address);



        if (riskAppetite == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    RiskAppetite.class.getSimpleName()));
        }

        if (!RiskAppetite.isValidRiskAppetite(riskAppetite)) {
            throw new IllegalValueException(RiskAppetite.MESSAGE_CONSTRAINTS);
        }

        final RiskAppetite modelRiskAppetite = new RiskAppetite(riskAppetite);

        if (disposableIncome == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    DisposableIncome.class.getSimpleName()));
        }

        if (!DisposableIncome.isValidDisposableIncome(disposableIncome)) {
            throw new IllegalValueException(DisposableIncome.MESSAGE_CONSTRAINTS);
        }

        final DisposableIncome modelDisposableIncome = new DisposableIncome(disposableIncome);

        final Set<Tag> modelTags = new HashSet<>(personTags);
        return new Person(modelClientId, modelName, modelPhone, modelEmail, modelAddress, modelRiskAppetite,
                modelDisposableIncome, modelCurrentPlan, modelLastMet, modelTags);
    }

}
