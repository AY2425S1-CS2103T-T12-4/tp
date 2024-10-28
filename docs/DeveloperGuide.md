---
  layout: default.md
  title: "Developer Guide"
  pageNav: 3
---

# AB-3 Developer Guide

<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## **Acknowledgements**

This project is an extension of [_AddressBook-Level3 by se-edu_](https://se-education.org/addressbook-level3/).

Libraries used include: [_JavaFX_](https://openjfx.io/), [_Jackson_](https://github.com/FasterXML/jackson), [_JUnit5_](https://github.com/junit-team/junit5)

Graphical Interface (GUI) Testing was adapted from [_AddressBook-Level4 by se-edu_](https://se-education.org/addressbook-level4/)


--------------------------------------------------------------------------------------------------------------------

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------

## **Design**

### Architecture

<puml src="diagrams/ArchitectureDiagram.puml" width="280" />

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** (consisting of classes [`Main`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/MainApp.java)) is in charge of the app launch and shut down.
* At app launch, it initializes the other components in the correct sequence, and connects them up with each other.
* At shut down, it shuts down the other components and invokes cleanup methods where necessary.

The bulk of the app's work is done by the following four components:

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete 1`.

<puml src="diagrams/ArchitectureSequenceDiagram.puml" width="574" />

Each of the four main components (also shown in the diagram above),

* defines its *API* in an `interface` with the same name as the Component.
* implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<puml src="diagrams/ComponentManagers.puml" width="300" />

The sections below give more details of each component.

### UI component

The **API** of this component is specified in [`Ui.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/Ui.java)

<puml src="diagrams/UiClassDiagram.puml" alt="Structure of the UI Component"/>

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<puml src="diagrams/LogicClassDiagram.puml" width="550"/>

The sequence diagram below illustrates the interactions within the `Logic` component, taking `execute("delete 1")` API call as an example.

<puml src="diagrams/DeleteSequenceDiagram.puml" alt="Interactions Inside the Logic Component for the `delete 1` Command" />

<box type="info" seamless>

**Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline continues till the end of diagram.
</box>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it is passed to an `AddressBookParser` object which in turn creates a parser that matches the command (e.g., `DeleteCommandParser`) and uses it to parse the command.
1. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `DeleteCommand`) which is executed by the `LogicManager`.
1. The command can communicate with the `Model` when it is executed (e.g. to delete a person).<br>
   Note that although this is shown as a single step in the diagram above (for simplicity), in the code it can take several interactions (between the command object and the `Model`) to achieve.
1. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<puml src="diagrams/ParserClasses.puml" width="600"/>

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/model/Model.java)

<puml src="diagrams/ModelClassDiagram.puml" width="450" />


The `Model` component,

* stores the address book data i.e., all `Person` objects (which are contained in a `UniquePersonList` object).
* stores the currently 'selected' `Person` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Person>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<box type="info" seamless>

**Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Person` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Person` needing their own `Tag` objects.<br>

<puml src="diagrams/BetterModelClassDiagram.puml" width="450" />

</box>


### Storage component

**API** : [`Storage.java`](https://github.com/se-edu/addressbook-level3/tree/master/src/main/java/seedu/address/storage/Storage.java)

<puml src="diagrams/StorageClassDiagram.puml" width="550" />

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.address.commons` package.

--------------------------------------------------------------------------------------------------------------------

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Implemented Undo feature

Each `Command` has its `undo()` method that undoes what the command executed.
Each command stores the necessary information to undo the execution.
All successful commands done by the user is stored in a `CommandLog`.

When `undo` is executed, the latest command in `CommandLog` is retrieved and that command's `undo()` method is called.

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagramNew-Logic.puml" alt="UndoSequenceDiagramNew-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` and `Command` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

### \[Proposed\] Undo/redo feature

#### Proposed Implementation

The proposed undo/redo mechanism is facilitated by `VersionedAddressBook`. It extends `AddressBook` with an undo/redo history, stored internally as an `addressBookStateList` and `currentStatePointer`. Additionally, it implements the following operations:

* `VersionedAddressBook#commit()` — Saves the current address book state in its history.
* `VersionedAddressBook#undo()` — Restores the previous address book state from its history.
* `VersionedAddressBook#redo()` — Restores a previously undone address book state from its history.

These operations are exposed in the `Model` interface as `Model#commitAddressBook()`, `Model#undoAddressBook()` and `Model#redoAddressBook()` respectively.

Given below is an example usage scenario and how the undo/redo mechanism behaves at each step.

Step 1. The user launches the application for the first time. The `VersionedAddressBook` will be initialized with the initial address book state, and the `currentStatePointer` pointing to that single address book state.

<puml src="diagrams/UndoRedoState0.puml" alt="UndoRedoState0" />

Step 2. The user executes `delete 5` command to delete the 5th person in the address book. The `delete` command calls `Model#commitAddressBook()`, causing the modified state of the address book after the `delete 5` command executes to be saved in the `addressBookStateList`, and the `currentStatePointer` is shifted to the newly inserted address book state.

<puml src="diagrams/UndoRedoState1.puml" alt="UndoRedoState1" />

Step 3. The user executes `add n/David …​` to add a new person. The `add` command also calls `Model#commitAddressBook()`, causing another modified address book state to be saved into the `addressBookStateList`.

<puml src="diagrams/UndoRedoState2.puml" alt="UndoRedoState2" />

<box type="info" seamless>

**Note:** If a command fails its execution, it will not call `Model#commitAddressBook()`, so the address book state will not be saved into the `addressBookStateList`.

</box>

Step 4. The user now decides that adding the person was a mistake, and decides to undo that action by executing the `undo` command. The `undo` command will call `Model#undoAddressBook()`, which will shift the `currentStatePointer` once to the left, pointing it to the previous address book state, and restores the address book to that state.

<puml src="diagrams/UndoRedoState3.puml" alt="UndoRedoState3" />


<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index 0, pointing to the initial AddressBook state, then there are no previous AddressBook states to restore. The `undo` command uses `Model#canUndoAddressBook()` to check if this is the case. If so, it will return an error to the user rather
than attempting to perform the undo.

</box>

The following sequence diagram shows how an undo operation goes through the `Logic` component:

<puml src="diagrams/UndoSequenceDiagram-Logic.puml" alt="UndoSequenceDiagram-Logic" />

<box type="info" seamless>

**Note:** The lifeline for `UndoCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.

</box>

Similarly, how an undo operation goes through the `Model` component is shown below:

<puml src="diagrams/UndoSequenceDiagram-Model.puml" alt="UndoSequenceDiagram-Model" />

The `redo` command does the opposite — it calls `Model#redoAddressBook()`, which shifts the `currentStatePointer` once to the right, pointing to the previously undone state, and restores the address book to that state.

<box type="info" seamless>

**Note:** If the `currentStatePointer` is at index `addressBookStateList.size() - 1`, pointing to the latest address book state, then there are no undone AddressBook states to restore. The `redo` command uses `Model#canRedoAddressBook()` to check if this is the case. If so, it will return an error to the user rather than attempting to perform the redo.

</box>

Step 5. The user then decides to execute the command `list`. Commands that do not modify the address book, such as `list`, will usually not call `Model#commitAddressBook()`, `Model#undoAddressBook()` or `Model#redoAddressBook()`. Thus, the `addressBookStateList` remains unchanged.

<puml src="diagrams/UndoRedoState4.puml" alt="UndoRedoState4" />

Step 6. The user executes `clear`, which calls `Model#commitAddressBook()`. Since the `currentStatePointer` is not pointing at the end of the `addressBookStateList`, all address book states after the `currentStatePointer` will be purged. Reason: It no longer makes sense to redo the `add n/David …​` command. This is the behavior that most modern desktop applications follow.

<puml src="diagrams/UndoRedoState5.puml" alt="UndoRedoState5" />

The following activity diagram summarizes what happens when a user executes a new command:

<puml src="diagrams/CommitActivityDiagram.puml" width="250" />

#### Design considerations:

**Aspect: How undo & redo executes:**

* **Alternative 1:** Saves the entire address book.
  * Pros: Easy to implement.
  * Cons: May have performance issues in terms of memory usage.

* **Alternative 2 (current choice):** Individual command knows how to undo/redo by
  itself.
  * Pros: Will use less memory (e.g. for `delete`, just save the person being deleted).
  * Cons: We must ensure that the implementation of each individual command are correct.

_{more aspects and alternatives to be added}_

### \[Proposed\] Data Archiving

#### Proposed Implementation

The data archiving feature allows users to archive older address book entries, such as contacts or other information, that are no longer actively used but should still be stored for record-keeping purposes. The archived data will be stored separately from the main active address book, ensuring a cleaner and more efficient main application state while still retaining access to archived information when needed.

To implement this, the following changes and components are proposed:

1. **Archive Mechanism**:
    - A new class called `ArchivedAddressBook` will be introduced to handle archived entries.
    - The `ArchivedAddressBook` will extend the base `AddressBook` class but will be optimized to manage entries that are less frequently accessed or modified.
    - `VersionedAddressBook` will also maintain a history for the `ArchivedAddressBook`, allowing undo/redo of archive operations.

2. **Operations**:
    - `Model#archivePerson(Person person)`: Archives a person by moving the person entry from the active `AddressBook` to the `ArchivedAddressBook`.
    - `Model#restorePerson(Person person)`: Restores a person from the `ArchivedAddressBook` back to the active `AddressBook`.
    - `Model#getArchivedPersons()`: Retrieves a list of all archived persons for display or further actions.
    - These operations will also be added to the command logic so that the user can archive and restore entries using new commands, e.g., `archive 5` (to archive the 5th person in the list).

3. **Storage**:
    - The `JsonAddressBookStorage` class will be modified to support saving and loading both active and archived data separately. The archived data will be stored in a separate JSON file (`archivedAddressBook.json`), keeping it distinct from the main address book data.
    - When the application starts, both the main and archived address books will be loaded into the system, and changes to either will be saved independently.

4. **User Interface**:
    - A new tab or section in the UI will be added to display archived entries separately from the main list.
    - Users will be able to toggle between viewing the active address book and the archived address book.
    - Archived entries will have limited actions available, such as restoring or permanently deleting them.

#### Example Usage Scenario

1. The user decides to archive an old contact, `John Doe`, by executing the command `archive 3`. This will call `Model#archivePerson()`, moving `John Doe` from the active address book to the archived one and committing the state.

2. The user later views the archived entries via a command like `show archived`. The application displays a list of archived entries.

3. If the user wishes to bring back a previously archived contact, they can use the command `restore 2` while viewing the archived list. This restores the contact to the active address book and updates the state history.

4. If the user clears the active address book, archived entries remain unaffected. This separation ensures that old data is kept while maintaining the flexibility to manage current information.

#### Design Considerations

**Aspect**: How data is stored and managed.
- **Alternative 1 (Current Choice)**: Store archived data in a separate file (`archivedAddressBook.json`).
    - **Pros**: Keeps the main address book file smaller, improving performance when accessing active data.
    - **Cons**: Requires additional storage logic and structure.
- **Alternative 2**: Store archived data in the same file with an "archived" flag.
    - **Pros**: Simplifies storage management as only one file is used.
    - **Cons**: Increases file size and complexity when accessing or updating active entries, as all entries (active and archived) are mixed together.

**Aspect**: User access to archived data.
- **Alternative 1 (Current Choice)**: A separate tab for archived data.
    - **Pros**: Clear separation in the UI, reducing confusion for users.
    - **Cons**: Requires additional UI components and logic.
- **Alternative 2**: Filter archived entries within the main view.
    - **Pros**: Easier to implement with existing UI.
    - **Cons**: Could clutter the main view and make it harder to manage active entries.

This design ensures that the application efficiently manages both current and archived data, aligning with user needs for flexibility and simplicity.

--------------------------------------------------------------------------------------------------------------------

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Requirements**

### Product scope

**Target user profile**:

* has a need to manage a large number of gaming contacts
* prefer desktop apps over web apps or mobile apps
* is proficient in typing and prefers keyboard inputs over mouse interactions
* feels comfortable using CLI applications

**Value proposition**: 

* manage contacts more quickly than a typical mouse/GUI driven app
* enable gamers to track the games played by friends and find people to game with more easily


### User stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a …​  | I want to …​                          | So that I can…​                                                              |
|----------|----------|---------------------------------------|------------------------------------------------------------------------------|
| `* * *`  | new user | see usage instructions                | refer to instructions when I forget how to use the App                       |
| `* * *`  | gamer    | add a new gaming contact              | keep track of my gaming friends and their details.                           |
| `* * *`  | gamer    | delete a gaming contact               | keep my address book up-to-date by removing inactive or irrelevant contacts. |
| `* * *`  | gamer    | view my contact list                  | see who I have contact with                                                  |
| `* * *`  | gamer    | tag my contacts with specific games   | easily find friends who play specific games                                  |
| `* * *`  | gamer    | find a contact by name                | locate details of persons without having to go through the entire list       |
| `* *`    | gamer    | find a contact by game                | quickly find contacts who play a specific game                               |
| `* *`    | gamer    | tag my contacts with different groups | manage my contacts into teams                                                |
| `* *`    | gamer    | tag contacts as inactive              | hide inactive contacts without deleting their  information                   |
| `* *`    | gamer    | add links to contacts                 | quickly find a contact's socials and other links                             |
| `*`      | gamer    | aff custom fields to each contact     | add various details to each contact, such as timezone, skill level, etc.     |

<!--
*{More to be added}*
-->

### Use cases

(For all use cases below, the **System** is the `AddressBook` and the **Actor** is the `user`, unless specified otherwise)

#### Use Case: UC1 - Add a Contact
**MSS**
1. User requests to add contact
2. GamerBook adds the contact
Use case ends.

**Extensions**
* 1a. User adds contact incorrectly.
  * 1a1. GamerBook notifies user that the command was entered incorrectly.
  
    Use case ends.
* 1b. User tries to add a duplicate contact.
    * 1b1. GamerBook notifies the user that the contact already exists.
  
      Use case ends.

* 1c. User leaves mandatory fields empty.
    * 1c1. GamerBook prompts the user to fill in the required fields.

      Use case ends.

#### Use Case: UC2 - Delete a Contact
**MSS**
1. User list the contacts.
2. User requests to delete a contact.
3. GamerBook deletes the contact.
Use case ends.

**Extensions**
* 2a. User attempts to delete a contact that does not exist.
    * 2a1. GamerBook notifies the user that the contact does not exist.

      Use case ends.
* 2b. User cancels the delete operation.
    * 2b1. GamerBook returns to the contact list without making any changes.

      Use case ends.

#### Use Case: UC3 - Find a Contact by Name
**MSS**
1. User try to find a contact by name.
2. GamerBook shows the corresponding contact.
Use case ends.

**Extensions**
* 1a.  User enters a name that does not exist.
    * 1a1. GamerBook notifies the user that no contacts were found.

      Use case ends.
* 1b. User types a partial name.
    * 1b1. GamerBook returns all contacts that match the partial name.

      Use case ends.

#### Use Case: UC4 - Tag a Contact
**MSS**
1. User request to tag a contact.
2. GamerBook tags the contact.
Use case ends.

**Extensions**
* 1a. User tries to tag a contact that does not exist.
    * 1a1. GamerBook notifies the user that the contact cannot be found.

      Use case ends.
* 1b. User attempts to tag a contact with an invalid game name.
    * 1b1. GamerBook shows an error message indicating the game name is invalid.

      Use case ends.

#### Use Case: UC5 - List All Contacts
**MSS**
1. User requests to list all contacts.
2. GamerBook displays all contacts in the system.
   Use case ends.

**Extensions**
* 1a. User tries to list contacts when none exist.
    * 1a1. GamerBook notifies the user that no contacts are available.

      Use case ends.

#### Use Case: UC6 - Edit a Game Details
**MSS**
1. User requests to edit a game's details (username, rank, skill level).
2. GamerBook updates the game's details accordingly.
   Use case ends.

**Extensions**
* 1a. User tries to edit a game that does not exist.
    * 1b1. GamerBook notifies the user that the game could not be found.

      Use case ends.

#### Use Case: UC7 - Find Contacts by Game
**MSS**
1. User requests to find contacts associated with a specific game.
2. GamerBook shows a list of contacts that match the specified game.
   Use case ends.

**Extensions**
* 1a. User enters a game that does not exist in GamerBook.
    * 1a1. GamerBook notifies the user that no contacts were found for the specified game.

      Use case ends.
* 1b. User enters a partial game name.
    * 1b1. GamerBook returns all contacts that match the partial game name.

      Use case ends.
* 1c. User enters an invalid or misspelled game name.
    * 1c1. GamerBook notifies the user that no contacts were found for the specified game.

      Use case ends.
  
<!-- 
TEMPLATE FOR ADDING USE CASE

#### Use Case: 
**MSS**
1. 
2. 
Use case ends.

**Extensions**
* 1a. 
  * 1a1. 
  
    Use case ends.
* 1b. 
    * 1b1. 
  
      Use case ends.

*{More to be added}*
-->

### Non-Functional Requirements
#### Compatibility
Should work on any _mainstream OS_ as long as it has Java `17` or above installed.

#### Performance
Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
   (eg. addition, deletion, and search operations)

#### Usability
The application should provide clear and concise error messages and usage instructions to assist the user in operating the system effectively.

#### Efficiency
A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

#### Security
The application should ensure that user data is securely stored and that sensitive information (like social media links) is not exposed unintentionally.

#### Data Integrity
The application should implement mechanisms to prevent data corruption and ensure that contacts are not lost during operations.

#### Scalability
The system should be designed to accommodate future enhancements, such as additional fields for contacts or new functionalities without requiring significant rework.

#### Accessibility
The user interface should be intuitive, user-friendly, and support accessibility features to cater to users with diverse needs and abilities.
<!--
*{More to be added}*
-->

### Glossary

* **Mainstream OS**: Windows, Linux, Unix, MacOS
* **Private contact detail**: A contact detail that is not meant to be shared with others

--------------------------------------------------------------------------------------------------------------------

## **Appendix: Instructions for manual testing**

Given below are instructions to test the app manually.

<box type="info" seamless>

**Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</box>

### Launch and shutdown

1. Initial launch

   1. Download the jar file and copy into an empty folder

   1. Double-click the jar file Expected: Shows the GUI with a set of sample contacts. The window size may not be optimum.

1. Saving window preferences

   1. Resize the window to an optimum size. Move the window to a different location. Close the window.

   1. Re-launch the app by double-clicking the jar file.<br>
       Expected: The most recent window size and location is retained.

1. _{ more test cases …​ }_

### Deleting a person

1. Deleting a person while all persons are being shown

   1. Prerequisites: List all persons using the `list` command. Multiple persons in the list.

   1. Test case: `delete 1`<br>
      Expected: First contact is deleted from the list. Details of the deleted contact shown in the status message. Timestamp in the status bar is updated.

   1. Test case: `delete 0`<br>
      Expected: No person is deleted. Error details shown in the status message. Status bar remains the same.

   1. Other incorrect delete commands to try: `delete`, `delete x`, `...` (where x is larger than the list size)<br>
      Expected: Similar to previous.

1. _{ more test cases …​ }_

### Saving data

1. Dealing with missing/corrupted data files

   1. _{explain how to simulate a missing/corrupted file, and the expected behavior}_

1. _{ more test cases …​ }_
