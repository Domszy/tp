package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Person {

    // Identity fields
    private final ClientId clientId;
    private final Name name;
    private final Email email;

    // Data fields
    private final Phone phone;
    private final Address address;
    private final RiskAppetite riskAppetite;
    private final DisposableIncome disposableIncome;
    private final CurrentPlan currentPlan;
    private final LastMet lastMet;
    private final Set<Tag> tags = new HashSet<>();

    /**
     * Every field must be present and not null.
     */

    public Person(ClientId clientId, Name name, Phone phone, Email email, Address address, RiskAppetite riskAppetite,
        DisposableIncome disposableIncome, CurrentPlan currentPlan, LastMet lastMet, Set<Tag> tags) {
        requireAllNonNull(name, phone, email, address, tags);
        this.clientId = clientId;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.riskAppetite = riskAppetite;
        this.disposableIncome = disposableIncome;
        this.currentPlan = currentPlan;
        this.lastMet = lastMet;
        this.tags.addAll(tags);
    }

    public ClientId getClientId() {
        return clientId;
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {

        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    public LastMet getLastMet() {
        return lastMet;
    }

    public CurrentPlan getCurrentPlan() {
        return currentPlan;
    }

    public RiskAppetite getRiskAppetite() {
        return riskAppetite;
    }

    public DisposableIncome getDisposableIncome() {
        return disposableIncome;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == null) {
            return false;

        }

        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName())
                    || otherPerson.getEmail().equals(getEmail());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return otherPerson.getClientId().equals(getClientId())
                && otherPerson.getName().equals(getName())
                && otherPerson.getPhone().equals(getPhone())
                && otherPerson.getEmail().equals(getEmail())
                && otherPerson.getAddress().equals(getAddress())
                && otherPerson.getRiskAppetite().equals(getRiskAppetite())
                && otherPerson.getDisposableIncome().equals(getDisposableIncome())
                && otherPerson.getCurrentPlan().equals(getCurrentPlan())
                && otherPerson.getLastMet().equals(getLastMet())
                && otherPerson.getTags().equals(getTags());

    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, currentPlan, lastMet, tags);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Client ID: ")
                .append(getClientId())
                .append("; Name: ")
                .append(getName())
                .append("; Email: ")
                .append(getEmail())
                .append("; Phone: ")
                .append(getPhone())
                .append("; Address: ")
                .append(getAddress())
                .append("; Risk Appetite: ")
                .append(getRiskAppetite())
                .append("; Disposable Income: ")
                .append(getDisposableIncome())
                .append("; current plans: ")
                .append(getCurrentPlan())
                .append("; Last Met: ")
                .append(getLastMet());

        Set<Tag> tags = getTags();
        if (!tags.isEmpty()) {
            builder.append("; Tags: ");
            tags.forEach(builder::append);
        }
        return builder.toString();
    }

}
