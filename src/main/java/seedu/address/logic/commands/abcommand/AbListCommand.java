package seedu.address.logic.commands.abcommand;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.StringUtil.JSON_FILE_PREFIX;
import static seedu.address.commons.util.StringUtil.getStringWithoutSuffix;

import java.io.File;
import java.util.Arrays;

import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

public class AbListCommand extends AbCommand {
    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": List all existing address books\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all address book:%s";

    public static final String MESSAGE_FAILURE = "Address book directory cannot be found";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        File addressBookFolder = new File("data");

        String[] addressBookList = addressBookFolder.list();
        if (addressBookList == null) {
            return new CommandResult(MESSAGE_FAILURE);
        }

        StringBuilder allAddressBooks = new StringBuilder();
        Arrays.stream(addressBookList)
                .filter(s -> s.endsWith(JSON_FILE_PREFIX))
                .map(s -> getStringWithoutSuffix(s, JSON_FILE_PREFIX))
                .forEach(formattedName -> allAddressBooks.append("\n- ").append(formattedName));

        return new CommandResult(String.format(MESSAGE_SUCCESS, allAddressBooks));
    }
}
