package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalClientId.CLIENTID_FIRST_CLIENT;
import static seedu.address.testutil.TypicalClientId.CLIENTID_OUTOFBOUND;
import static seedu.address.testutil.TypicalClientId.CLIENTID_SECOND_CLIENT;
import static seedu.address.testutil.TypicalClientId.CLIENTID_THIRD_CLIENT;
import static seedu.address.testutil.TypicalClientId.CLIENTID_ZERO_CLIENT;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.util.StringUtil;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.EditClientDescriptorBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() {
        ClientId clientId = CLIENTID_ZERO_CLIENT;
        Client editedClient = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(editedClient).build();
        EditCommand editCommand = new EditCommand(List.of(clientId), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS, editedClient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        expectedModel.setClientByClientIds(List.of(clientId), descriptor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() {
        ClientId clientId = CLIENTID_THIRD_CLIENT;
        Client thirdClient = model.getClient(clientId);

        ClientBuilder clientInList = new ClientBuilder(thirdClient);
        Client editedClient = clientInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withTags(VALID_TAG_HUSBAND).build();

        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB)
            .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(List.of(clientId), descriptor);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS, editedClient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClientByClientIds(List.of(clientId), descriptor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecified_success() {
        ClientId clientId = CLIENTID_FIRST_CLIENT;
        EditCommand editCommand = new EditCommand(List.of(clientId), new EditClientDescriptor());
        Client editedClient = model.getClient(clientId);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS, editedClient);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_batchEdit_success() {
        ClientId clientId1 = CLIENTID_FIRST_CLIENT;
        ClientId clientId2 = CLIENTID_THIRD_CLIENT;
        ClientId clientId3 = CLIENTID_SECOND_CLIENT;
        List<ClientId> clientIdList = List.of(clientId1, clientId2, clientId3);
        EditClientDescriptor editClientDescriptor = new EditClientDescriptorBuilder()
            .withAddress(VALID_ADDRESS_AMY).withPhone(VALID_PHONE_BOB).build();

        EditCommand editCommand = new EditCommand(clientIdList, editClientDescriptor);
        Client client1 = model.getClient(clientId1);
        Client client2 = model.getClient(clientId2);
        Client client3 = model.getClient(clientId3);

        Client editedClient1 = new ClientBuilder(client1).withAddress(VALID_ADDRESS_AMY)
            .withPhone(VALID_PHONE_BOB).build();
        Client editedClient2 = new ClientBuilder(client2).withAddress(VALID_ADDRESS_AMY)
            .withPhone(VALID_PHONE_BOB).build();
        Client editedClient3 = new ClientBuilder(client3).withAddress(VALID_ADDRESS_AMY)
            .withPhone(VALID_PHONE_BOB).build();

        List<ClientId> clientIdlist = List.of(clientId1, clientId2, clientId3);
        List<Client> editClientList = List.of(editedClient1, editedClient2, editedClient3);

        String expectedMessage = String.format(EditCommand.MESSAGE_EDIT_CLIENT_SUCCESS,
            StringUtil.joinListToString(editClientList, "\n"));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setClientByClientIds(clientIdlist, editClientDescriptor);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_duplicateClient_failure() {
        ClientId clientId = CLIENTID_FIRST_CLIENT;
        Client firstClient = model.getClient(clientId);
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(firstClient).build();
        EditCommand editCommand = new EditCommand(List.of(CLIENTID_THIRD_CLIENT), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_CLIENT);
    }


    @Test
    public void execute_invalidClient_failure() {
        ClientId outOfBound = CLIENTID_OUTOFBOUND;
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(List.of(outOfBound), descriptor);

        assertCommandFailure(editCommand, model,
            String.format(Messages.MESSAGE_NONEXISTENT_CLIENT_ID, CLIENTID_OUTOFBOUND)
        );
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(List.of(CLIENTID_THIRD_CLIENT), DESC_AMY);

        // same values -> returns true
        EditClientDescriptor copyDescriptor = new EditClientDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(List.of(CLIENTID_THIRD_CLIENT), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(List.of(CLIENTID_SECOND_CLIENT), DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(List.of(CLIENTID_FIRST_CLIENT), DESC_BOB)));
    }

}
