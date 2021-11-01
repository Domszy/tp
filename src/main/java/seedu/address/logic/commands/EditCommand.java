package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.CLIENT_DELIMITER;
import static seedu.address.commons.util.StringUtil.COMMA_DELIMITER;
import static seedu.address.commons.util.StringUtil.joinListToString;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENTPLAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LASTMET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEXTMEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_CLIENTS;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
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
import seedu.address.model.client.exceptions.DuplicateClientException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing client in the address book.
 */
public class EditCommand extends Command {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the client identified "
        + "by the client's ID. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: " + " CLIENT ID (must be a positive integer) "
        + PREFIX_NAME + "NAME "
        + PREFIX_PHONE + "PHONE "
        + PREFIX_EMAIL + "EMAIL "
        + PREFIX_ADDRESS + "ADDRESS "
        + PREFIX_LASTMET + "LAST MET "
        + PREFIX_NEXTMEETING + "NEXT MEETING "
        + PREFIX_CURRENTPLAN + "CURRENTPLAN "
        + "[" + PREFIX_TAG + "TAG]...\n"
        + "Example: " + COMMAND_WORD + " " + " 1 "
        + PREFIX_PHONE + "91234567 "
        + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_CLIENT_SUCCESS = "Edited Client: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This operation will result in a"
        + " duplicate in the address book.";
    public static final String MESSAGE_CHANGE_CLIENTID = "Client's ID cannot be changed.";

    private final List<ClientId> clientIds;
    private final EditClientDescriptor editClientDescriptor;

    /**
     * @param clientIds            of the client in the filtered client list to edit
     * @param editClientDescriptor details to edit the client with
     */
    public EditCommand(List<ClientId> clientIds, EditClientDescriptor editClientDescriptor) {
        requireNonNull(clientIds);
        requireNonNull(editClientDescriptor);

        this.clientIds = clientIds;
        this.editClientDescriptor = new EditClientDescriptor(editClientDescriptor);
    }

    /**
     * Creates and returns a {@code Client} with the details of {@code clientToEdit}
     * edited with {@code editClientDescriptor}.
     */
    public static Client createEditedClient(Client clientToEdit, EditClientDescriptor editClientDescriptor) {
        assert clientToEdit != null;

        ClientId oldClientId = clientToEdit.getClientId();
        Name updatedName = editClientDescriptor.getName().orElse(clientToEdit.getName());
        Email updatedEmail = editClientDescriptor.getEmail().orElse(clientToEdit.getEmail());
        Phone updatedPhone = editClientDescriptor.getPhone().orElse(clientToEdit.getPhone());
        Address updatedAddress = editClientDescriptor.getAddress().orElse(clientToEdit.getAddress());
        RiskAppetite updateRiskAppetite = editClientDescriptor.getRiskAppetite()
            .orElse(clientToEdit.getRiskAppetite());
        DisposableIncome updatedDisposableIncome = editClientDescriptor.getDisposableIncome()
            .orElse(clientToEdit.getDisposableIncome());
        CurrentPlan updatedCurrentPlan = editClientDescriptor.getCurrentPlan().orElse(clientToEdit.getCurrentPlan());
        LastMet updatedLastMet = editClientDescriptor.getLastMet().orElse(clientToEdit.getLastMet());
        NextMeeting tempUpdatedNextMeeting = editClientDescriptor.getNextMeeting()
            .orElse(clientToEdit.getNextMeeting());
        NextMeeting updatedNextMeeting = tempUpdatedNextMeeting.copyNextMeeting();
        if (!updatedNextMeeting.isNullMeeting()) {
            updatedNextMeeting.setWithWho(updatedName);

        }
        Set<Tag> updatedTags = editClientDescriptor.getTags().orElse(clientToEdit.getTags());

        return new Client(oldClientId, updatedName, updatedPhone, updatedEmail, updatedAddress, updateRiskAppetite,
            updatedDisposableIncome, updatedCurrentPlan, updatedLastMet, updatedNextMeeting, updatedTags);
    }

    /**
     * Creates and returns a {@code Client} with the {@code LastMet} date being replaced by
     * the {@code NextMeeting} date and {@code NextMeeting} being set to {@code NullMeeting}
     */
    public static Client createEditedMeetingOverClient(Client clientToEdit) {
        assert clientToEdit != null;

        ClientId oldClientId = clientToEdit.getClientId();
        Name oldName = clientToEdit.getName();
        Email oldEmail = clientToEdit.getEmail();
        Phone oldPhone = clientToEdit.getPhone();
        Address oldAddress = clientToEdit.getAddress();
        RiskAppetite oldRiskAppetite = clientToEdit.getRiskAppetite();
        DisposableIncome oldDisposableIncome = clientToEdit.getDisposableIncome();
        CurrentPlan oldCurrentPlan = clientToEdit.getCurrentPlan();
        Set<Tag> oldTags = clientToEdit.getTags();

        LastMet updatedLastMet = clientToEdit.getLastMet().getLaterLastMet(
            clientToEdit.getNextMeeting().convertToLastMet()
        );
        NextMeeting updatedNextMeeting = NextMeeting.NULL_MEETING;


        return new Client(oldClientId, oldName, oldPhone, oldEmail, oldAddress, oldRiskAppetite,
            oldDisposableIncome, oldCurrentPlan, updatedLastMet, updatedNextMeeting, oldTags);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<ClientId> distinctClientIds = clientIds.stream().distinct().collect(Collectors.toList());
        List<ClientId> invalidClientIds = distinctClientIds.stream()
            .filter(c -> !model.hasClientId(c))
            .collect(Collectors.toList());

        if (!invalidClientIds.isEmpty()) {
            String invalidClientIdsString = joinListToString(invalidClientIds, COMMA_DELIMITER);
            throw new CommandException(String.format(Messages.MESSAGE_NONEXISTENT_CLIENT_ID, invalidClientIdsString));
        }

        List<Client> editedClients;
        try {
            editedClients = model.setAllClients(clientIds, editClientDescriptor);
        } catch (DuplicateClientException de) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }


        model.updateFilteredClientList(PREDICATE_SHOW_ALL_CLIENTS);

        String clientsString = joinListToString(editedClients, CLIENT_DELIMITER);
        return new CommandResult(String.format(MESSAGE_EDIT_CLIENT_SUCCESS, clientsString));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        EditCommand e = (EditCommand) other;
        return clientIds.equals(e.clientIds)
            && editClientDescriptor.equals(e.editClientDescriptor);
    }

    /**
     * Stores the details to edit the client with. Each non-empty field value will replace the
     * corresponding field value of the client.
     */
    public static class EditClientDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private RiskAppetite riskAppetite;
        private DisposableIncome disposableIncome;
        private Set<Tag> tags;
        private LastMet lastMet;
        private NextMeeting nextMeeting;
        private CurrentPlan currentPlan;

        public EditClientDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditClientDescriptor(EditClientDescriptor toCopy) {
            setName(toCopy.name);
            setPhone(toCopy.phone);
            setEmail(toCopy.email);
            setAddress(toCopy.address);
            setDisposableIncome(toCopy.disposableIncome);
            setRiskAppetite(toCopy.riskAppetite);
            setLastMet(toCopy.lastMet);
            setNextMeeting(toCopy.nextMeeting);
            setCurrentPlan(toCopy.currentPlan);
            setDisposableIncome(toCopy.disposableIncome);
            setRiskAppetite(toCopy.riskAppetite);
            setTags(toCopy.tags);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, phone, email, address, riskAppetite, disposableIncome,
                currentPlan, lastMet, nextMeeting, tags);
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<LastMet> getLastMet() {
            return Optional.ofNullable(lastMet);
        }

        public void setLastMet(LastMet lastMet) {
            this.lastMet = lastMet;
        }

        public Optional<NextMeeting> getNextMeeting() {
            return Optional.ofNullable(nextMeeting);
        }

        public void setNextMeeting(NextMeeting nextMeeting) {
            this.nextMeeting = nextMeeting;
        }

        public Optional<CurrentPlan> getCurrentPlan() {
            return Optional.ofNullable(currentPlan);
        }

        public void setCurrentPlan(CurrentPlan currentPlan) {
            this.currentPlan = currentPlan;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<RiskAppetite> getRiskAppetite() {
            return Optional.ofNullable(riskAppetite);
        }

        public void setRiskAppetite(RiskAppetite riskAppetite) {
            this.riskAppetite = riskAppetite;
        }

        public Optional<DisposableIncome> getDisposableIncome() {
            return Optional.ofNullable(disposableIncome);
        }

        public void setDisposableIncome(DisposableIncome disposableIncome) {
            this.disposableIncome = disposableIncome;
        }

        /**
         * Returns an unmodifiable tag set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code tags} is null.
         */
        public Optional<Set<Tag>> getTags() {
            return (tags != null) ? Optional.of(Collections.unmodifiableSet(tags)) : Optional.empty();
        }

        /**
         * Sets {@code tags} to this object's {@code tags}.
         * A defensive copy of {@code tags} is used internally.
         */
        public void setTags(Set<Tag> tags) {
            this.tags = (tags != null) ? new HashSet<>(tags) : null;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditClientDescriptor)) {
                return false;
            }

            // state check
            EditClientDescriptor e = (EditClientDescriptor) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getLastMet().equals(e.getLastMet())
                && getNextMeeting().equals(e.getNextMeeting())
                && getCurrentPlan().equals(e.getCurrentPlan())
                && getDisposableIncome().equals(e.getDisposableIncome())
                && getRiskAppetite().equals(e.getRiskAppetite())
                && getNextMeeting().equals(e.getNextMeeting())
                && getTags().equals(e.getTags());
        }
    }
}
