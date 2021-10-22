package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClientId.CLIENTID_ZERO_CLIENT;
import static seedu.address.testutil.TypicalClients.getTypicalAddressBook;

import java.util.List;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditClientDescriptor;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.commands.abcommand.AbCommand;
import seedu.address.logic.commands.abcommand.AbListCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientContainsKeywordsPredicate;
import seedu.address.model.client.ClientHasId;
import seedu.address.model.client.ClientId;
import seedu.address.testutil.ClientBuilder;
import seedu.address.testutil.ClientUtil;
import seedu.address.testutil.EditClientDescriptorBuilder;

public class AddressBookParserTest {

    private ModelManager model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private final AddressBookParser parser = new AddressBookParser(model);

    @BeforeEach
    public void setUp() {
        model.getAddressBook().setClientCounter("0");
    }

    @Test
    public void parseCommand_add() throws Exception {
        Function<ClientId, Client> clientFunction = new ClientBuilder().buildFunction();
        Client client = new ClientBuilder().build();
        AddCommand command = (AddCommand) parser.parseCommand(ClientUtil.getAddCommand(client));
        assertEquals(new AddCommand(clientFunction), command);
    }

    @Test
    public void parseCommand_clear() throws Exception {
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD) instanceof ClearCommand);
        assertTrue(parser.parseCommand(ClearCommand.COMMAND_WORD + " 3") instanceof ClearCommand);
    }

    @Test
    public void parseCommand_delete() throws Exception {
        DeleteCommand command = (DeleteCommand) parser.parseCommand(
            DeleteCommand.COMMAND_WORD + " 1");
        ClientId clientId = new ClientId("1");
        assertEquals(new DeleteCommand(List.of(clientId)), command);
    }

    @Test
    public void parseCommand_edit() throws Exception {
        Client client = new ClientBuilder().build();
        EditClientDescriptor descriptor = new EditClientDescriptorBuilder(client).build();

        EditCommand command = (EditCommand) parser.parseCommand(EditCommand.COMMAND_WORD + " "
            + CLIENTID_ZERO_CLIENT.value + " " + ClientUtil.getEditClientDescriptorDetails(descriptor));
        List<ClientId> clientIds = List.of(new ClientId(CLIENTID_ZERO_CLIENT.value));
        assertEquals(new EditCommand(clientIds, descriptor), command);
    }

    @Test
    public void parseCommand_exit() throws Exception {
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD) instanceof ExitCommand);
        assertTrue(parser.parseCommand(ExitCommand.COMMAND_WORD + " 3") instanceof ExitCommand);
    }

    @Test
    public void parseCommand_search() throws Exception {
        String keywords = "do t/friends e/example.com";
        ArgumentMultimap aMM = ArgumentTokenizer.tokenize(keywords,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        SearchCommand command = (SearchCommand) parser.parseCommand(
            SearchCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new SearchCommand(new ClientContainsKeywordsPredicate(aMM)), command);
    }

    @Test
    public void parseCommand_filter() throws Exception {
        String keywords = "do t/friends e/example.com";
        ArgumentMultimap aMM = ArgumentTokenizer.tokenize(keywords,
            PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_TAG);
        FilterCommand command = (FilterCommand) parser.parseCommand(
            FilterCommand.COMMAND_WORD + " " + keywords);
        assertEquals(new FilterCommand(new ClientContainsKeywordsPredicate(aMM)), command);
    }

    @Test
    public void parseCommand_view() throws Exception {
        String input = "1";
        ClientId clientId = new ClientId(input);
        ViewCommand command = (ViewCommand) parser.parseCommand(
            ViewCommand.COMMAND_WORD + " " + input);
        assertEquals(new ViewCommand(clientId, new ClientHasId(clientId)), command);
    }

    @Test
    public void parseCommand_ab() throws Exception {
        String input = AbListCommand.COMMAND_WORD;
        AbCommand command = (AbCommand) parser.parseCommand(AbCommand.COMMAND_WORD + " " + input);
        assertTrue(command instanceof AbListCommand);
    }

    @Test
    public void parseCommand_help() throws Exception {
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD) instanceof HelpCommand);
        assertTrue(parser.parseCommand(HelpCommand.COMMAND_WORD + " 3") instanceof HelpCommand);
    }

    @Test
    public void parseCommand_list() throws Exception {
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD) instanceof ListCommand);
        assertTrue(parser.parseCommand(ListCommand.COMMAND_WORD + " 3") instanceof ListCommand);
    }

    @Test
    public void parseCommand_unrecognisedInput_throwsParseException() {
        assertThrows(ParseException.class, String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE), ()
            -> parser.parseCommand(""));
    }

    @Test
    public void parseCommand_unknownCommand_throwsParseException() {
        assertThrows(ParseException.class, MESSAGE_UNKNOWN_COMMAND, () -> parser.parseCommand("unknownCommand"));
    }
}
