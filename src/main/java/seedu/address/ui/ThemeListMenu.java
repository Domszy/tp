package seedu.address.ui;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import seedu.address.logic.Logic;
import seedu.address.storage.ThemeList;

/**
 * Menu containing the menu item of different theme to switch to.
 */
public class ThemeListMenu extends UiPart<Menu> {
    private static final String FXML = "ThemeListMenu.fxml";

    @FXML
    private Menu menu;

    private final ObservableList<MenuItem> menuItems;

    /**
     * Creates a {@code ThemeListMenu} object with the {@code currentTheme} and {@code logic}.
     */
    public ThemeListMenu(Logic logic, ObservableList<String> styleSheets) {
        super(FXML);
        menuItems = menu.getItems();
        menu.setText(logic.getTheme().toString());

        ObservableList<ThemeType> themeList = logic.getThemeList();
        Function<ThemeType, MenuItem> menuItemFunction = theme -> {
            MenuItem temp = new MenuItem(theme.getThemeName());
            temp.setOnAction(x -> {
                String filePathName = logic.getTheme().getFilePathName();
                int index = styleSheets.indexOf(filePathName);
                styleSheets.set(index, theme.getFilePathName());
                logic.setTheme(theme);
            });
            return temp;
        };

        ThemeList.THEME_LIST.stream()
                .filter(theme -> !themeList.contains(theme))
                .map(menuItemFunction)
                .forEach(menuItems::add);

        Consumer<ThemeType> addedConsumer = theme ->
                menuItemFunction.andThen(Optional::ofNullable).apply(theme).ifPresent(menuItems::add);

        Consumer<ThemeType> removedConsumer = theme -> {
            Predicate<MenuItem> matchMenuItem = menuItem -> menuItem.getText().equals(theme.getThemeName());
            ObservableList<MenuItem> temp = menuItems.filtered(matchMenuItem);
            if (temp.isEmpty()) {
                return;
            }

            MenuItem tempMenu = temp.get(0);
            menuItems.remove(tempMenu);
        };

        themeList.addListener((ListChangeListener<? super ThemeType>) c -> {
            while (c.next()) {
                menu.setText(logic.getTheme().toString());
                c.getAddedSubList().forEach(removedConsumer);
                c.getRemoved().forEach(addedConsumer);
            }
        });
    }
}
