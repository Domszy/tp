package seedu.address.ui;

import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.tag.Tag;

/**
 * Tag label.
 */
public class TagLabel extends UiPart<Region> {

    private static final String FXML = "TagLabel.fxml";

    @FXML
    private Label label;

    /**
     * Creates a {@code TagLabel} with the given {@code value}, {@code tag} and {@code commandBox}.
     */
    public TagLabel(String value, Tag tag, CommandBox commandBox) {
        super(FXML);

        label.setText(value);
        label.setOnMouseClicked(event -> {
                commandBox.setCommand(FilterCommand.COMMAND_WORD + " " + PREFIX_TAG.getPrefix() + tag.getName()
                );
            }
        );
    }
}
