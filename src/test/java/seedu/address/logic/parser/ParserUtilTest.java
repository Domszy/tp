package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_CLIENT_ID;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalClientId.CLIENTID_ZERO_CLIENT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddCommandTest;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.client.Address;
import seedu.address.model.client.CurrentPlan;
import seedu.address.model.client.DisposableIncome;
import seedu.address.model.client.Email;
import seedu.address.model.client.LastMet;
import seedu.address.model.client.Name;
import seedu.address.model.client.NextMeeting;
import seedu.address.model.client.Phone;
import seedu.address.model.client.RiskAppetite;
import seedu.address.model.client.SortDirection;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_PHONE_FROM_PARSER = "+651234";
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_DIRECTION = "desc";
    private static final String INVALID_RISKAPPETITE = "10";
    private static final String INVALID_DISPOSABLEINCOME = "-2313213";
    private static final String INVALID_LASTMET = "20-30-2021";
    private static final String INVALID_NEXTMEETING = "18 Oct, 2pm-3pm";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE_FROM_PARSER = "123456";
    private static final String VALID_PHONE = "123456";
    private static final String VALID_ADDRESS_FROM_PARSER = "123 Main Street #0505";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";
    private static final String VALID_DIRECTION = "asc";
    private static final String VALID_RISKAPPETITE = "2";
    private static final String VALID_DISPOSABLEINCOME = "5000";
    private static final String VALID_CURRENTPLAN = "Prudential Prolife";
    private static final String VALID_LASTMET = "05-10-2021";
    private static final String VALID_NEXTMEETING = "24-09-2021 (10:00~12:00), Starbucks @ UTown";

    private static final String WHITESPACE = " \t\r\n";

    @Test
    public void parseClientId_invalidInput_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseClientId("10 a"));
    }

    @Test
    public void parseClientId_outOfRangeInput_throwsParseException() {
        assertThrows(ParseException.class,
                MESSAGE_INVALID_CLIENT_ID, () -> ParserUtil.parseClientId(Long.toString(Integer.MAX_VALUE + 1)));
    }

    @Test
    public void parseClientId_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(CLIENTID_ZERO_CLIENT, ParserUtil.parseClientId("0"));

        // Leading and trailing whitespaces
        assertEquals(CLIENTID_ZERO_CLIENT, ParserUtil.parseClientId("  0  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parsePhone(null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parsePhone(INVALID_PHONE_FROM_PARSER));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(VALID_PHONE_FROM_PARSER));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        assertEquals(expectedPhone, ParserUtil.parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress(null));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS_FROM_PARSER));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseEmail(null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        assertEquals(expectedEmail, ParserUtil.parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseDirection_validValueWithoutWhitespace_returnsDirection() throws Exception {
        SortDirection expectedSortDirection = SortDirection.of(VALID_DIRECTION);
        assertEquals(expectedSortDirection, ParserUtil.parseSortDirection(VALID_DIRECTION));
    }

    @Test
    public void parseDirection_validValueWithWhitespace_returnsTrimmedDirection() throws Exception {
        String directionWithWhitespace = WHITESPACE + VALID_DIRECTION + WHITESPACE;
        SortDirection expectedSortDirection = SortDirection.of(VALID_DIRECTION);
        assertEquals(expectedSortDirection, ParserUtil.parseSortDirection(directionWithWhitespace));
    }

    @Test
    public void parseDirection_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseSortDirection(null));
    }

    @Test
    public void parseDirection_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseEmail(INVALID_DIRECTION));
    }

    @Test
    public void parseRiskAppetite_validValueWithoutWhitespace_returnsRiskAppetite() throws Exception {
        RiskAppetite expectedRiskAppetite = new RiskAppetite(VALID_RISKAPPETITE);
        assertEquals(expectedRiskAppetite, ParserUtil.parseRiskAppetite(VALID_RISKAPPETITE));
    }

    @Test
    public void parseRiskAppetite_validValueWithWhitespace_returnsTrimmedRiskAppetite() throws Exception {
        String riskAppetiteWithWhitespace = WHITESPACE + VALID_RISKAPPETITE + WHITESPACE;
        RiskAppetite expectedRiskAppetite = new RiskAppetite(VALID_RISKAPPETITE);
        assertEquals(expectedRiskAppetite, ParserUtil.parseRiskAppetite(riskAppetiteWithWhitespace));
    }

    @Test
    public void parseRiskAppetite_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseRiskAppetite(null));
    }

    @Test
    public void parseRiskAppetite_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseRiskAppetite(INVALID_RISKAPPETITE));
    }

    @Test
    public void parseDisposableIncome_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseDisposableIncome(INVALID_DISPOSABLEINCOME));
    }

    @Test
    public void parseDisposableIncome_validValueWithoutWhitespace_returnsDisposableIncome() throws Exception {
        DisposableIncome expectedDisposableIncome = new DisposableIncome(VALID_DISPOSABLEINCOME);
        assertEquals(expectedDisposableIncome, ParserUtil.parseDisposableIncome(VALID_DISPOSABLEINCOME));
    }

    @Test
    public void parseDisposableIncome_validValueWithWhitespace_returnsTrimmedDisposableIncome() throws Exception {
        String disposableIncomeWithWhitespace = WHITESPACE + VALID_DISPOSABLEINCOME + WHITESPACE;
        DisposableIncome expectedDisposableIncome = new DisposableIncome(VALID_DISPOSABLEINCOME);
        assertEquals(expectedDisposableIncome, ParserUtil.parseDisposableIncome(disposableIncomeWithWhitespace));
    }

    @Test
    public void parseCurrentPlan_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseCurrentPlan(null));
    }

    @Test
    public void parseCurrentPlan_validValueWithoutWhitespace_returnsCurrentPlan() throws Exception {
        CurrentPlan expectedCurrentPlan = new CurrentPlan(VALID_CURRENTPLAN);
        assertEquals(expectedCurrentPlan, ParserUtil.parseCurrentPlan(VALID_CURRENTPLAN));
    }

    @Test
    public void parseCurrentPlan_validValueWithWhitespace_returnsTrimmedCurrentPlan() throws Exception {
        String currentPlanWithWhitespace = WHITESPACE + VALID_CURRENTPLAN + WHITESPACE;
        CurrentPlan expectedCurrentPlan = new CurrentPlan(VALID_CURRENTPLAN);
        assertEquals(expectedCurrentPlan, ParserUtil.parseCurrentPlan(currentPlanWithWhitespace));
    }

    @Test
    public void parseLastMet_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseLastMet(null));
    }

    @Test
    public void parseLastMet_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseLastMet(INVALID_LASTMET));
    }

    @Test
    public void parseLastMet_validValueWithoutWhitespace_returnsLastMet() throws Exception {
        LastMet expectedLastMet = new LastMet(VALID_LASTMET);
        assertEquals(expectedLastMet, ParserUtil.parseLastMet(VALID_LASTMET));
    }

    @Test
    public void parseLastMet_validValueWithWhitespace_returnsTrimmedLastMet() throws Exception {
        String lastMetWithWhitespace = WHITESPACE + VALID_LASTMET + WHITESPACE;
        LastMet expectedLastMet = new LastMet(VALID_LASTMET);
        assertEquals(expectedLastMet, ParserUtil.parseLastMet(lastMetWithWhitespace));
    }

    @Test
    public void parseNextMeeting_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseNextMeeting((String) null));
    }

    @Test
    public void parseNextMeeting_invalidValue_throwsParseException() {
        assertThrows(ParseException.class, () -> ParserUtil.parseNextMeeting(INVALID_NEXTMEETING));
    }

    @Test
    public void parseNextMeeting_emptyString_returnsNullMeeting() throws Exception {
        assertEquals(NextMeeting.getNullMeeting(), ParserUtil.parseNextMeeting(""));
    }

    @Test
    public void parseNextMeeting_noMeetingString_returnsNullMeeting() throws Exception {
        assertEquals(NextMeeting.getNullMeeting(), ParserUtil.parseNextMeeting(NextMeeting.NO_NEXT_MEETING));
        assertEquals(NextMeeting.getNullMeeting(), ParserUtil.parseNextMeeting("no meeting planned"));
    }

    @Test
    public void parseNextMeeting_validValueWithoutWhitespace_returnsNextMeeting() throws Exception {
        NextMeeting expectedNextMeeting = new NextMeeting("24-09-2021", "10:00", "12:00",
            "Starbucks @ UTown", "");
        assertEquals(expectedNextMeeting, ParserUtil.parseNextMeeting(VALID_NEXTMEETING));
    }

    @Test
    public void parseNextMeeting_validValueWithWhitespace_returnsTrimmedNextMeeting() throws Exception {
        String nextMeetingWithWhitespace = WHITESPACE + VALID_NEXTMEETING + WHITESPACE;
        NextMeeting expectedNextMeeting = new NextMeeting("24-09-2021", "10:00", "12:00",
            "Starbucks @ UTown", "");
        assertEquals(expectedNextMeeting, ParserUtil.parseNextMeeting(nextMeetingWithWhitespace));
    }

    @Test
    public void parseDisposableIncome_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseDisposableIncome(null));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() {
        AddCommandTest.ModelStub modelStub = new AddCommandTest.ModelStub();
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTag(null, modelStub));
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() {
        AddCommandTest.ModelStub modelStub = new AddCommandTest.ModelStub();
        assertThrows(ParseException.class, () -> ParserUtil.parseTag(INVALID_TAG, modelStub));
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        AddCommandTest.ModelStub modelStub = new ModelStubAcceptingTags();
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1, modelStub));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        AddCommandTest.ModelStub modelStub = new ModelStubAcceptingTags();
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace, modelStub));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() {
        AddCommandTest.ModelStub modelStub = new AddCommandTest.ModelStub();
        assertThrows(NullPointerException.class, () -> ParserUtil.parseTags(null, modelStub));
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() {
        AddCommandTest.ModelStub modelStub = new ModelStubAcceptingTags();
        assertThrows(ParseException.class, () ->
                ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG), modelStub));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        AddCommandTest.ModelStub modelStub = new ModelStubAcceptingTags();
        assertTrue(ParserUtil.parseTags(Collections.emptyList(), modelStub).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        AddCommandTest.ModelStub modelStub = new ModelStubAcceptingTags();
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2), modelStub);
        Set<Tag> expectedTagSet = new HashSet<>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }

    @Test
    public void parseConfirmation_throwsNullPointerException() throws Exception {
        assertThrows(NullPointerException.class, () -> ParserUtil.parseConfirmation(null));
    }

    @Test
    public void parseConfirmation_returnsTrue() throws Exception {
        assertTrue(ParserUtil.parseConfirmation("Yes"));
    }

    @Test
    public void parseConfirmation_returnsFalse() throws Exception {
        assertFalse(ParserUtil.parseConfirmation("No"));
    }

    @Test
    public void parseConfirmation_returnsTrue_inputWithSpaces() throws Exception {
        assertTrue(ParserUtil.parseConfirmation(" yes "));
    }

    @Test
    public void parseConfirmation_returnsTrue_inputWithNonLowerCase() throws Exception {
        assertTrue(ParserUtil.parseConfirmation("yES"));
    }

    @Test
    public void parseConfirmation_throwsParserException() throws Exception {
        assertThrows(ParseException.class, () -> ParserUtil.parseConfirmation("not yes not no"));
    }

    /**
     * A Model stub that always accept the tag being added.
     */
    private class ModelStubAcceptingTags extends AddCommandTest.ModelStub {

        final ArrayList<Tag> tagsAdded = new ArrayList<>();

        @Override
        public boolean hasTagName(String tagName) {
            requireNonNull(tagName);
            return tagsAdded.stream().anyMatch(t -> t.getName().equals(tagName));
        }

        @Override
        public Tag getTag(String tagName) {
            return new Tag(tagName);
        }

        @Override
        public void addTag(Tag tag) {
            requireNonNull(tag);
            tagsAdded.add(tag);
        }
    }
}
