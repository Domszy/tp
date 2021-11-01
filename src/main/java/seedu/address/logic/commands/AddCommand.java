package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CURRENTPLAN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DISPOSABLEINCOME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LASTMET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RISKAPPETITE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Optional;
import java.util.function.Function;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.client.Client;
import seedu.address.model.client.ClientId;

/**
 * Adds a client to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a client to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + PREFIX_RISKAPPETITE + "RISK APPETITE "
            + PREFIX_DISPOSABLEINCOME + "DISPOSABLE INCOME "
            + PREFIX_CURRENTPLAN + "CURRENT PLAN "
            + PREFIX_LASTMET + "LAST MET "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_RISKAPPETITE + "3 "
            + PREFIX_DISPOSABLEINCOME + "4000 "
            + PREFIX_CURRENTPLAN + "Prudential Proshield "
            + PREFIX_LASTMET + "21-03-2020 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New client added: %1$s";
    public static final String MESSAGE_DUPLICATE_CLIENT = "This client already exists in the address book";

    private final Function<ClientId, Client> toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Client}
     */
    public AddCommand(Function<ClientId, Client> client) {
        // HACK: making client a function is counter-intuitive
        requireNonNull(client);
        toAdd = client;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        // HACK: getClientCounter() should encapsulate the default
        String clientCounter = Optional.ofNullable(model.getAddressBook().getClientCounter()).orElse("0");
        Client client = toAdd.apply(new ClientId(clientCounter));

        // TODO: model.createClient (to do check and add client)
        if (model.hasClient(client)) {
            throw new CommandException(MESSAGE_DUPLICATE_CLIENT);
        }

        model.addClient(client);
        // TODO: model.incrementClientCounter should not be exposed, instead increment directly within addClient
        model.getAddressBook().incrementClientCounter();

        return new CommandResult(String.format(MESSAGE_SUCCESS, client));
    }
    /**
     * Returns an unmodifiable view of the filtered person list
     */
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.apply(new ClientId("0")).equals(((AddCommand) other)
                .toAdd.apply(new ClientId("0"))));
    }
}
