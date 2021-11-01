package seedu.address.ui;

import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Logger;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.tag.Tag;
import seedu.address.storage.StorageManager;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class TagsPanel extends UiPart<Region> {

    private static final Logger logger = LogsCenter.getLogger(StorageManager.class);
    private static final String FXML = "TagsPanel.fxml";
    private final HashMap<Tag, Region> tagLabels = new HashMap<>();

    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code TagsPanel} with the given {@code tagList} and {@code commandBox}.
     */
    public TagsPanel(ObservableList<Tag> tagList, CommandBox commandBox) {
        super(FXML);

        tagList.stream()
                .sorted(Comparator.comparing(Tag::getName))
                .forEach(tag -> {
                    TagLabel tagLabel = new TagLabel(tag.toString(), tag, commandBox);
                    tags.getChildren().add(tagLabel.getRoot());
                    tagLabels.put(tag, tagLabel.getRoot());
                });

        tagList.addListener((ListChangeListener<Tag>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    Tag tag = change.getAddedSubList().get(0);
                    TagLabel tagLabel = new TagLabel(tag.toString(), tag, commandBox);
                    logger.fine(tag.getName() + "was added to the list!");
                    tags.getChildren().add(tagLabel.getRoot());
                    tagLabels.put(tag, tagLabel.getRoot());
                } else if (change.wasRemoved()) {
                    Tag tag = change.getRemoved().get(0);
                    logger.fine(tag.getName() + "was removed from the list!");
                    tags.getChildren().remove(tagLabels.remove(change.getRemoved().get(0)));
                } else if (change.wasUpdated()) {
                    tags.getChildren().clear();
                    change.getList().stream()
                            .sorted(Comparator.comparing(Tag::getName))
                            .forEach(tag -> {
                                TagLabel tagLabel = new TagLabel(tag.toString(), tag, commandBox);
                                tags.getChildren().add(tagLabel.getRoot());
                            });
                }
            }
        });
    }
}
