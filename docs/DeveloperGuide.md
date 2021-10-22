---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

# **LeadsForce - Developer Guide**

By: `AY2122S1-CS2103T-T17-3`

--------------------------------------------------------------------------------------------------------------------

## **1. Introduction**

LeadsForce's developer Guide is written for developers who wish to contribute to or extend our project. It is technical, and explains the inner workings of LeadsForce and how the different components of our application work together.

**Reading this Developer Guide**
| icon | remark |
| --- | --- |
| 💡 | This icon denotes useful tips to note of during development. |
| ❗️ | This icon denotes important details to take note of during development. |

--------------------------------------------------------------------------------------------------------------------

## **2. Setting up and getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **3. Design**

### 3.1 Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

LeadsForce is a brown field project adapted and developed upon from **AddressBook3**. Our team decided on reusing the overall architecture by maintaining the system with 6 components (as listed in the diagram above) while building upon each component to cater to the needs of LeadsForce. The ***Architecture Diagram*** given above explains the high-level design of the App, which was adapted from **AddressBook3**. In the subsequent chapters, we will be providing an overview of the each component, explain how each component works internally, and how you could scale the system, which can server as a guideline for the developers to expand LeadsForce.

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/se-edu/addressbook-level3/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

**`Main`** has two classes called [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java). It is responsible for,
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

Each of the four main components,

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point)

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface.

<img src="images/LogicClassDiagram.png" width="574" />

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

The sections below give more details of each component.

### 3.2 UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ClientListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFX UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Client` object residing in the `Model`.

LeadsForce's GUI is primarily adapted from `AddressBook3`, with the addition of a scrollable `SideBar` that conveniently displays information for the user.

Our `SideBar` has a `ClientViewPanel`, which like the `ClientCard`, has a dependency on the `Model` class to fully display the information of the `Client` of interest to the user.

### 3.3 Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to add a client).
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### 3.4 Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />


The `Model` component,

* stores the address book data i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* has a `UniqueTagList` in `AddressBook` , which contains `Tag`(s) that a `Client` can reference. This allows `AddressBook` to only require one `Tag` object per unique tag.
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
    * this `ObservableList<Client>` is used for to get the `Client` object to display in `ClientListPanel` and `ClientViewPanel`
* stores the address book data i.e., all `Client` objects (which are contained in a `UniqueClientList` object).
* has a `UniqueTagList` in `AddressBook` , which contains `Tag`(s) that a `Client` can reference. This allows `AddressBook` to only require one `Tag` object per unique tag.
* stores the currently 'selected' `Client` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Client>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
    * this `ObservableList<Client>` is used for to get the `Client` object to display in `ClientListPanel` and `ClientViewPanel`
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### 3.5 Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<img src="images/StorageClassDiagram.png" width="550" />

The `Storage` component,
* can save both address book data and user preference data in json format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### 3.6 Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

### 3.6.1 Field options

We provide options for developers to easily customise the constraints on the user input such as whether the input is required or whether it is editable. These field options are encapsulated within the `Field` interface, which further branches into more concrete interfaces which can be implemented by `Client` attributes.

Field options largely dictate how the parsers respond to user's inputs.

Option | Description
--- | ---
IS_BLANK_VALUE_ALLOWED | If set to `true`, the field is allowed to be blank (for string fields such as phone, name, etc). Default to `true`.
IS_NULL_VALUE_ALLOWED | If set to `true`, the field is allowed to be null (for int/Date fields such as LastMet, etc). Default to `true`.
DEFAULT_VALUE | The default value for the field. Set when user does not pass in the prefix on `Client` creation. Default to `""`.
IS_EDITABLE | If set to `true`, the field is editable by the user through edit command. Default to `true`.

### 3.6.2 Field interfaces

<img src="images/FieldClassDiagram.png" width="700" />

The following concrete interfaces inherit the `Field` interface. You can alternatively define your own interface or provide a concrete implementation of the field options within the `attribute` classes if they don't suit your needs.

#### 3.6.3 OptionalStringBasedField

Option | Default
--- | ---
IS_BLANK_VALUE_ALLOWED | `true`
IS_NULL_VALUE_ALLOWED | `false`
DEFAULT_VALUE | `""`
IS_EDITABLE | `true`

#### 3.6.4 OptionalNonStringBasedField

Option | Default
--- | ---
IS_BLANK_VALUE_ALLOWED | `true`
IS_NULL_VALUE_ALLOWED | `true`
DEFAULT_VALUE | `""`
IS_EDITABLE | `true`

#### 3.6.5 RequiredField

Option | Default
--- | ---
IS_BLANK_VALUE_ALLOWED | `false`
IS_NULL_VALUE_ALLOWED | `false`
DEFAULT_VALUE | `""`(But not applicable here)
IS_EDITABLE | `true`

--------------------------------------------------------------------------------------------------------------------

## **4. Implementation**

This section describes some noteworthy details on how certain features are implemented.

## AddressBook feature

### 4.1 Search Clients

#### Description

LeadsForce allows the user to `search` for clients using keywords. The keywords can be used to match generically
with any client's attribute or specifically with the specified attributes

#### Implementation

1. The `LogicManager` starts to parses the given input text using `AddressBookParser`.
2. The `AddressBookParser` invoke the respective `Parser` based on the first word of the input text.
3. The remaining input text will be passed to the `SearchCommandParser` to parse.
4. The `SearchCommandParser` will tokenize the remaining input text using the `ArgumentTokenizer` into an `ArgumentMultiMap`.
5. The `SearchCommandParser` will then create a new `ClientContainsKeywordPredicate` using the `ArgumentMultiMap`.
6. The `SearchCommandParser` will then create a `SearchCommand` with the `ClientContainsKeywordPredicate`.
7. The `LogicManger` will call the `execute` method of `SearchCommand`.
8. The `SearchCommand` wil then call the `updateFilteredClientList` method of the provided `Model` with it's `ClientContainsKeywordPredicate`.
9. The `SearchCommand` will finally create a new `CommandResult` which will be returned to `LogicManager`.

Below is sequence diagram for search clients.

<img src="images/tracing/SearchCommandSequenceDiagram.png" />

#### Implementation of ClientContainsKeywordPredicate

`ClientContainsKeywordPredicate` implements `Predicate<Client>` and allow filtering of a list of `Client` based on
generic and attribute keywords.`ClientContainsKeywordPredicate` contains an `ArgumentMultiMap` which holds these
keywords. The `preamble` string of the `ArgumentMultiMap` corresponds to generic keywords. All the words in that string
will be used to match with all the attributes of the `Client`. The different `values` String that is mapped to the
different `Prefix` corresponds to attribute keywords. Each of these `values` string will then be matched with the
corresponding `Client`'s attribute that their `Prefix` refers to e.g. if the `Prefix` `e/` was mapped to `@gmail.com`,
then `@gmail.com` will be used to matched with the `Email` attribute of the `Client`. For this predicate to return true,
the given `Client` must match with any of the generic keywords if there is any and all the attribute keywords if there
is any.

### 4.2 Filter Clients

#### Description

LeadsForce allows the user to `filter` for clients using keywords. This works similar to the `search` but it allows
for multiple `filter` to be stacked, which allows for user to look for clients incrementally.

#### Implementation

1. The `LogicManager` starts to parses the given input text using `AddressBookParser`
2. The `AddressBookParser` invoke the respective `Parser` based on the first word of the input text.
3. The remaining input text will be passed to the `FilterCommandParser` to parse.
4. The `FilterCommandParser` will tokenize the remaining input text using the `ArgumentTokenizer` into an `ArgumentMultiMap`.
5. The `FilterCommandParser` will then create a new `ClientContainsKeywordPredicate` using the `ArgumentMultiMap`.
6. The `FilterCommandParser` will then create a `FilterCommand` with the `ClientContainsKeywordPredicate`
7. The `LogicManger` will call the `execute` method of `FilterCommand`.
8. The `FilterCommand` wil then call the `filterFilteredClientList` method of the provided `Model` with it's `ClientContainsKeywordPredicate`.
9. The `FilterCommand` will finally create a new `CommandResult` which will be returned to `LogicManager`.

Below is sequence diagram for filter clients.

<img src="images/tracing/FilterCommandSequenceDiagram.png" />

#### Implementation of ClientContainsKeywordPredicate

See the above description in `Search Clients`.

### 4.3 View Client Info

#### Description

LeadsForce allows users to view client info in the `ClientViewPanel` in the `SideBar` of the GUI using the `View` command.

#### Implementation

1. The `LogicManager` starts to parses the given input text using `AddressBookParser`.
2. The `AddressBookParser` invoke the respective `Parser` based on the first word of the input text.
3. The remaining input text will be passed to the `ViewCommandParser` to parse.
4. The `ViewCommandParser` will parse the `ClientId` from the remaining input text. In our implementation, a valid `ClientId` is any non-negative integer.
5. The `ViewCommandParser` will then create a new `ClientHasId` using the `ClientId` parsed.
6. The `ViewCommandParser` will then create a `ViewCommand` with the `ClientId` and `ClientHasId`.
7. The `LogicManger` will call the `execute` method of `ViewCommand`.
8. The `ViewCommand` wil then call the `updateClientToView` method of the provided `Model` with it's `ClientHasId`.
9. The `ViewCommand` will finally create a new `CommandResult` which will be returned to `LogicManager` and the client's information with the given `ClientId` in the `ClientViewPanel`.

Below is the sequence diagram for view clients.

<img src="images/ViewCommandSequenceDiagram.png" />

#### Implementation of ClientHasId

`ClientHasId` implements `Predicate<Client>` and allow filtering of a list of `Client` based on `ClientId` objects. `ClientHasId` contains a list which holds these 'ClientId'. This allows the `Model` to use this list of `ClientId` objects to filter for `Client`(s) that contain the given `ClientId`(s).

### 4.4 \[Proposed\] Multiple Address Book

#### 4.4.1 Proposed Implementation

To be included

### 4.5 \[Proposed\] Undo/redo feature

#### 4.5.1 Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

![UndoRedoState0](images/UndoRedoState0.png)

Step 2. The user executes `delete 5` command to delete the 5th client in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

![UndoRedoState1](images/UndoRedoState1.png)

Step 3. The user executes `add n/David …​` to add a new client. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

![UndoRedoState2](images/UndoRedoState2.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</div>

Step 4. The user now decides that adding the client was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

![UndoRedoState3](images/UndoRedoState3.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</div>

The following sequence diagram shows how the undo operation works:

![UndoSequenceDiagram](images/UndoSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</div>

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<div markdown="span" class="alert alert-info">:information_source: **Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</div>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

![UndoRedoState4](images/UndoRedoState4.png)

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

![UndoRedoState5](images/UndoRedoState5.png)

The following activity diagram summarizes what happens when a user executes a new command:

<img src="images/CommitActivityDiagram.png" width="250" />

#### 4.5.2 Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1 (current choice):** Saves the entire address book.
    * Pros: Easy to implement.
    * Cons: May have performance issues in terms of memory usage.

* **Alternative 2:** Individual command knows how to undo/redo by
  itself.
    * Pros: Will use less memory (e.g. for `delete`, just save the client being deleted).
    * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### 4.6 \[Proposed\] Data archiving

_{Explain here how the data archiving feature will be implemented}_


--------------------------------------------------------------------------------------------------------------------

## **5. Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **6. Appendix: Requirements**

### 6.1 Product scope

**Target user**: Student Financial Advisor

**Target user profile**:

* Has a need to manage a significant number of contacts
* Short on time as they have to juggle both school and work at the same time
* Trying to sell financial products effectively by finding the right clients
* Job is competitive in nature

**Value propositions**:
* LeadsForce is a new way to streamline the process for student financial advisors to find the right clients to contact. We aim to help you manage your leads by making it effortless to store information regarding them and retrieving this information seamlessly. Finding your next lead has never been easier.



### 6.2 User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​                                    | I can …​                     | So that I can…​                                                        |
| -------- | ------------------------------------------ | ------------------------------ | ---------------------------------------------------------------------- |
| `* * *`  | user                                       | add my clients information     | easily refer to it         |
| `* * *`  | user                                       | delete contact information     | remove contacts who I no longer keep in touch with                                                                       |
| `* * *`  | user                                       | lookup a client in the address book | retrieve relevant information |
| `* * *`  | user                                       | search for a client by their name |                         |
| `* * *`  | user                                       | save my address book locally   | access the information again when I reopen the application |
| `* * *`  | user                                       | see hints in error messages when I input a wrong command | know how to rectify my command |
| `* * *`  | user                                       | be warned that I am about to add the same user again |                         |
| `* * *`  | user                                       | have the addressbook to be functional 99.999 percent of the time | not get frustrated and find another app to use |
| `* * *`  | user                                       | exit the app safely            | my data will not be corrupted |
| `* *`    | user                                       | edit my client's information   | keep track of relevant client information |
| `* *`    | user                                       | sort my address book           | quickly identify the clients based on the last time I've seen them or the number of financial plans they have |

*{More to be added}*

### 6.3 Use cases

(For all use cases below, the **System** is the `LeadsForce` and the **Actor** is the `user`, unless specified otherwise)

**6.3.1 Use case: Add a client**
**6.3.1 Use case: Add a client**

**MSS**

1. User requests to add a client
2. LeadsForce adds the client to the contact book

   Use case ends.

**Extensions**

* 1a.  The user forgets to input the required info (name and email)

    * 1a1. LeadsForce shows an error message.

      Use case resumes at step 2.


**6.3.2 Use case: Delete a client**
**6.3.2 Use case: Delete a client**

**MSS2**

1.  User requests to list clients
2.  LeadsForce shows a list of clients
3.  User requests to delete a specific client in the list
4.  LeadsForce deletes the client

    Use case ends.

**Extensions**

* 2a. The list is empty.

  Use case ends.

* 3a. The given index is invalid.

    * 3a1. LeadsForce shows an error message.

      Use case resumes at step 2.

**6.3.3 Use case: Search for a client**
**6.3.3 Use case: Search for a client**

**MSS3**

1. User requests to list clients
2. LeadsForce shows a list of clients
3. User requests to search using specific keywords
4. LeadsForce shows the list of all people which match the keyword

   Use case ends.

**Extensions**

* 2a.  The list is empty.

  Use case ends.

* 3a. No client fits the inputted keyword

  Use case ends.

*{More to be added}*

### 6.4 Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has Java `11` or above installed.
2. Should be able to hold up to 1000 clients without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
4. The system should respond within two seconds.
5. Should work without requiring an installer.
6. The system should work on a 64-bit environment.
7. The product should be usable by a student who has little to much experience in using computers.


*{More to be added}*

### 6.5 Glossary

* **Mainstream OS**: Windows, Linux, Unix, OS-X
* **Command Line Interface (CLI)**: Text-based user interface that is used to view and manage device files
* **Graphical User Interface (GUI)**: A visual way of interacting with a device using a variety of items
* **Leads**: refers to contact with a potential customer, also known as a “prospect”
* **Risk Appetite**: level of risk that a lead is prepared to accept in pursuit of his/her objectives, before action is deemed necessary to reduce the risk
* **Disposable Income**: total cliental income minus cliental current taxes
--------------------------------------------------------------------------------------------------------------------

## **7. Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### 7.1 Launch and shutdown

1. Initial launch

    1. Download the jar file and copy into an empty folder

    1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

    1. Resize the window to an optimum size. Move the window to a different location. Close the window.

    1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### 7.2 Deleting a client
### 7.2 Deleting a client

1. Deleting a client while all clients are being shown

    1. Prerequisites: List all clients using the `list` command. Multiple clients in the list.

    1. Test case: `delete 1`<br>
       Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

    1. Test case: `delete 0`<br>
       Expected: No client is deleted. Error details shown in the status message. Status bar remains the same.

    1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
       Expected: Similar to previous.

1. _{ more test cases …​ }_

### 7.3 Saving data

1. Dealing with missing/corrupted data files

    1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
