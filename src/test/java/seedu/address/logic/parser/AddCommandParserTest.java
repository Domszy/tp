package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CURRENTPLAN_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.CURRENTPLAN_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DISPOSABLEINCOME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DISPOSABLEINCOME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DISPOSABLEINCOME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LASTMET_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_RISKAPPETITE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LASTMET_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.LASTMET_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.RISKAPPETITE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.RISKAPPETITE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CURRENTPLAN_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CLIENTID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LASTMET_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.DisposableIncome;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.RiskAppetite;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {

    private ModelManager model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private AddCommandParser parser = new AddCommandParser(model);

    @BeforeEach
    public void setUp() {
        model.getAddressBook().setClientCounter("1");
    }

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        model.getAddressBook().setClientCounter("10");
        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB
                        + LASTMET_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple names - last name accepted
        assertParseSuccess(parser, NAME_DESC_AMY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB
                        + LASTMET_DESC_BOB + TAG_DESC_FRIEND ,
                new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple phones - last phone accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB
                + LASTMET_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple emails - last email accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB
                + LASTMET_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple addresses - last address accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB
                + LASTMET_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple risk appetite - last risk appetite accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_AMY + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB
                + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple disposable income - last disposable income accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_AMY + DISPOSABLEINCOME_DESC_BOB
                + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple current plans - last current plan accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_AMY + DISPOSABLEINCOME_DESC_BOB
                + CURRENTPLAN_DESC_AMY + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple Last Met- last date accepted
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_AMY
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_AMY + DISPOSABLEINCOME_DESC_BOB
                + CURRENTPLAN_DESC_AMY + CURRENTPLAN_DESC_BOB + LASTMET_DESC_AMY + LASTMET_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        model.getAddressBook().setClientCounter("10");
        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        model.getAddressBook().setClientCounter("9");
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                        + RISKAPPETITE_DESC_AMY + DISPOSABLEINCOME_DESC_AMY + CURRENTPLAN_DESC_AMY + LASTMET_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);
        model.getAddressBook().setClientCounter(VALID_CLIENTID_BOB);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);


        // invalid riskAppetite
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_RISKAPPETITE_DESC + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, RiskAppetite.MESSAGE_CONSTRAINTS);

        // invalid Disposable Income
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + INVALID_DISPOSABLEINCOME_DESC + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, DisposableIncome.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + CURRENTPLAN_DESC_BOB + LASTMET_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + RISKAPPETITE_DESC_BOB + DISPOSABLEINCOME_DESC_BOB + TAG_DESC_HUSBAND
                + TAG_DESC_FRIEND, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
