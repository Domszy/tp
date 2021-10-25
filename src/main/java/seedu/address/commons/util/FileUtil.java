package seedu.address.commons.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Writes and reads files
 */
public class FileUtil {

    private static final String CHARSET = "UTF-8";

    /**
     * Converts {@code String addressBookName} into a {@code Path}
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String convertToAddressBookPathString(String addressBookName, Path fileDirectory) {
        String trimmedLowerCase = addressBookName.trim().toLowerCase();
        return String.format("%s/%s.json", fileDirectory.toString(), trimmedLowerCase);
    }

    /**
     * Converts {@code Path} into it's address book name
     * Leading and trailing whitespaces will be trimmed.
     */
    public static String convertToAddressBookName(Path filePath) {
        String filePathString = filePath.toString();
        int index = filePathString.lastIndexOf("\\");
        String fileName = filePathString.substring(index + 1);
        return fileName.substring(0, fileName.length() - 5);
    }

    /**
     * Return true if {@code filePath} is Json file.
     */
    public static boolean isJsonFile(Path filePath) {
        return filePath.toString().endsWith(StringUtil.JSON_FILE_PREFIX);
    }

    public static boolean isFileExists(Path file) {
        return Files.exists(file) && Files.isRegularFile(file);
    }

    /**
     * Returns true if {@code path} can be converted into a {@code Path} via {@link Paths#get(String, String...)},
     * otherwise returns false.
     * @param path A string representing the file path. Cannot be null.
     */
    public static boolean isValidPath(String path) {
        try {
            Paths.get(path);
        } catch (InvalidPathException ipe) {
            return false;
        }
        return true;
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     * @throws IOException if the file or directory cannot be created.
     */
    public static void createIfMissing(Path file) throws IOException {
        if (!isFileExists(file)) {
            createFile(file);
        }
    }

    /**
     * Creates a file if it does not exist along with its missing parent directories.
     */
    public static void createFile(Path file) throws IOException {
        if (Files.exists(file)) {
            return;
        }

        createParentDirsOfFile(file);

        Files.createFile(file);
    }

    /**
     * Creates parent directories of file if it has a parent directory
     */
    public static void createParentDirsOfFile(Path file) throws IOException {
        Path parentDir = file.getParent();

        if (parentDir != null) {
            Files.createDirectories(parentDir);
        }
    }

    /**
     * Assumes file exists
     */
    public static String readFromFile(Path file) throws IOException {
        return new String(Files.readAllBytes(file), CHARSET);
    }

    /**
     * Writes given string to a file.
     * Will create the file if it does not exist yet.
     */
    public static void writeToFile(Path file, String content) throws IOException {
        Files.write(file, content.getBytes(CHARSET));
    }

}
