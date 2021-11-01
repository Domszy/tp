package seedu.address.ui;

import java.util.Optional;

import seedu.address.MainApp;

/**
 * Encapsulates a theme to be applied on the GUI.
 */
public class ThemeType {
    private final String themeName;

    private final String filePathName;

    /**
     * Create a {@code ThemeType} with the given {@code themeName}.
     */
    private ThemeType(String themeName, String filePathName) {
        this.themeName = themeName;
        this.filePathName = filePathName;
    }

    public String getFilePathName() {
        return this.filePathName;
    }

    public String getThemeName() {
        return this.themeName;
    }


    /**
     * Returns an {@code String} of the name  of the file path for
     * the given {@code themeName} wrapped by an {@code Optional}.
     */
    public static Optional<String> themeToFilePathName(String themeName) {
        String cssFileName = String.format("/view/%s.css", themeName);
        return Optional.ofNullable(MainApp.class.getResource(cssFileName)).map(Object::toString);
    }

    /**
     * Returns an {@code ThemeType} wrapped by an {@code Optional} for the given {@code themeName}.
     */
    public static Optional<ThemeType> of(String themeName) {
        Optional<String> filePathName = themeToFilePathName(themeName);
        return filePathName.map(x -> new ThemeType(themeName, x));
    }

    @Override
    public String toString() {
        return this.themeName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ThemeType)) {
            return false;
        }

        ThemeType otherTheme = (ThemeType) other;
        return otherTheme.themeName.equals(this.themeName);
    }
}
