package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.ClientId;
import seedu.address.model.person.Email;
import seedu.address.model.person.PersonHasEmail;
import seedu.address.model.person.PersonHasId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validClientId_returnsDeleteCommand() {
        ClientId clientId = new ClientId("1");
        PersonHasId predicate = new PersonHasId(clientId);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(predicate);
        assertParseSuccess(parser, " i/1", new DeleteCommand(predicates));
    }

    @Test
    public void parse_validEmail_returnsDeleteCommand() {
        Email email = new Email("test@gmail.com");
        PersonHasEmail predicate = new PersonHasEmail(email);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(predicate);
        assertParseSuccess(parser, " e/test@gmail.com", new DeleteCommand(predicates));
    }

    @Test
    public void parse_validEmailandClientId_returnsDeleteCommand() {
        Email email = new Email("test@gmail.com");
        PersonHasEmail predicateEmail = new PersonHasEmail(email);
        ClientId clientId = new ClientId("1");
        PersonHasId predicateId = new PersonHasId(clientId);
        ArrayList<Predicate> predicates = new ArrayList<>();
        predicates.add(predicateId);
        predicates.add(predicateEmail);
        assertParseSuccess(parser, " i/1 e/test@gmail.com", new DeleteCommand(predicates));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

}
