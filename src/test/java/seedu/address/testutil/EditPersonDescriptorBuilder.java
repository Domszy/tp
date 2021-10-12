package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
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
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setClientId(person.getClientId());
        descriptor.setName(person.getName());
        descriptor.setEmail(person.getEmail());
        descriptor.setLastMet(person.getLastMet());
        descriptor.setCurrentPlan(person.getCurrentPlan());
        descriptor.setPhone(person.getPhone());
        descriptor.setAddress(person.getAddress());
        descriptor.setRiskAppetite(person.getRiskAppetite());
        descriptor.setDisposableIncome(person.getDisposableIncome());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withClientId(String clientId) {
        descriptor.setClientId(new ClientId(clientId));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Current Plan} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withCurrentPlan(String currentPlan) {
        descriptor.setCurrentPlan(new CurrentPlan(currentPlan));
        return this;
    }

    /**
     * Sets the {@code RiskAppetite} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRiskAppetite(String riskAppetite) {
        descriptor.setRiskAppetite(new RiskAppetite(riskAppetite));
        return this;
    }

    /**
     * Sets the {@code LastMet} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withLastMet(String lastMetDate) {
        descriptor.setLastMet(new LastMet(lastMetDate));
        return this;
    }

    /**
     * Sets the {@code DisposableIncome} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDisposableIncome(String disposableIncome) {
        descriptor.setDisposableIncome(new DisposableIncome(disposableIncome));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
