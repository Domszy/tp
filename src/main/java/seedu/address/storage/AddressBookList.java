package seedu.address.storage;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.util.FileUtil;

/**
 * A wrapper containing the Path to all the AddressBook Json File.
 */
public class AddressBookList {

    private final ObservableList<Path> addressBookPaths;

    public AddressBookList(Path jsonFilePath) {
        addressBookPaths = initialiseFileList(jsonFilePath);
    }

    public void addAddressBookPath(Path filePath) {
        this.addressBookPaths.add(filePath);
    }

    public void deleteAddressBookPath(Path filePath) {
        this.addressBookPaths.remove(filePath);
    }

    public ObservableList<Path> getAddressBookList() {
        return this.addressBookPaths;
    }

    private ObservableList<Path> initialiseFileList(Path jsonFilePath) {
        ObservableList<Path> temp = FXCollections.observableArrayList();
        try {
            DirectoryStream<Path> ds = Files.newDirectoryStream(jsonFilePath);
            for (Path d : ds) {
                temp.add(d);
            }
            return temp;
        } catch (IOException ie) {
            return temp;
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        addressBookPaths.forEach(x -> sb.append("\n-").append(FileUtil.convertToAddressBookName(x)));
        return sb.toString();
    }
}
