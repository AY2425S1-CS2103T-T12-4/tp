---
  layout: default.md
  title: "User Guide"
  pageNav: 3
---
# GamerBook Pro Max User Guide

GamerBook Pro Max is a **desktop app for managing contacts, optimized for use via a Command Line Interface** (CLI) while still having the benefits of a Graphical User Interface (GUI).
This app is useful for gamers who have many friends online across different games.
If you can type fast, GamerBook can get your contact management tasks done faster than traditional GUI apps.


<!-- * Table of Contents -->
<page-nav-print />

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have [Java `17`](https://www.oracle.com/sg/java/technologies/downloads/) or above installed in your Computer. You can check your Java version by opening a command terminal (e.g. Command Prompt) and typing `java -version`.

1. Download the latest `.jar` file from [here](https://github.com/AY2425S1-CS2103T-T12-4/tp/releases).

1. Copy the file to the folder you want to use as the _home folder_ for your AddressBook.

1. Open a command terminal, use `cd` to nvaigate into the folder you put the jar file in, and use the `java -jar GamerBook.jar` command to run the application.<br>
   A GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * `list` : Lists all contacts, this is useful after you filter the address book using commands such as `find`.

   * `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

   * `delete 3` : Deletes the 3rd contact shown in the current list.

   * `clear` : Deletes all contacts.

   * `exit` : Exits the app.

1. Refer to the [Features](#features) below for more details.
--------------------------------------------------------------------------------------------------------------------

## Features

<box type="info" seamless>

**Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.  
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are **optional**.  
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times.  
  e.g. `[t/TAG]…​` can be used as `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.  
  e.g. if the command specifies `n/NAME p/PHONE_NUMBER`, `p/PHONE_NUMBER n/NAME` is also acceptable.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.  
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

* If you are using a PDF version of this document, be careful when copying and pasting commands that span multiple lines as space characters surrounding line-breaks may be omitted when copied over to the application.

**Commamnd PopUp:**<br>

![CommandPopUp](images/commandpopup.png)

We have implemented a Command Suggestion PopUp for your convenience!

As long as the command box is currently the UI element **in focus** and **at least one** character is detected inside the command box, it will suggest the possible commands that you can autocomplete it to.

Underneath each suggested command is the syntax for that command and what parameters are needed to complete it
* To access the autocomplete functionality press `shift + up` or `shift + down` while the popup is open and it will highlight the **current selection** in **blue**.  
   _In the image the **current selection** would be `editgame`._

* If your **current selection** is correct press `tab` to autocomplete it within the command box.

* The suggestion will stay open as you finish your command so u can reference it in case you forget any syntax.

</box>
--------------------------------------------------------------------------------------------------------------------

## Command summary

| Action         | Format, Examples                                                                                                                                                                                                         |
|----------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **Help**       | `help`                                                                                                                                                                                                                   |
| **List**       | `list`                                                                                                                                                                                                                   |
| **Add**        | `add n/NAME p/PHONE_NUMBER e/EMAIL a/ADDRESS [g/GAME]… [t/TAG]… [pt/TIME-TIME]…​` <br> e.g., `add n/James Ho p/22224444 e/jamesho@example.com a/123, Clementi Rd, 1234665 g/Overwatch t/friend t/colleague pt/2130-2359` |
| **Edit**       | `edit INDEX [n/NAME] [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/Game]… [t/TAG]… [pt/TIME-TIME]…​`<br> e.g.,`edit 2 n/James Lee e/jameslee@example.com`                                                                    |
| **AddGame**    | `addgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`<br> e.g.,`addgame 1 g/Overwatch u/Potato`                                                                                                                   |
| **EditGame**   | `editgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`<br> e.g.,`editgame 1 g/Overwatch u/Potato`                                                                                                                 |
| **DeleteGame** | `deletegame INDEX g/GAME`<br> e.g.,`deletegame 1 g/Overwatch`                                                                                                                                                            |
| **FavGame**    | `favgame INDEX g/GAME`<br> e.g.,`favgame 2 g/Overwatch`                                                                                                                                                                  |
| **UnFavGame**  | `unfavgame INDEX g/GAME`<br> e.g.,`unfavgame 2 g/Overwatch`                                                                                                                                                              |
| **Find**       | `find KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`                                                                                                                                                               |
| **FindTime**   | `find TIME-TIME [TIME-TIME]`<br> e.g., `findtime 1800-1900 2000-2200 `                                                                                                                                                   |
| **Delete**     | `delete INDEX`<br> e.g., `delete 3`                                                                                                                                                                                      |
| **Clear**      | `clear`                                                                                                                                                                                                                  |
| **Undo**       | `undo`                                                                                                                                                                                                                   |
| **Save**       | `save`                                                                                                                                                                                                                   |
| **Load**       | `load`                                                                                                                                                                                                                   |
| **Exit**       | `exit`                                                                                                                                                                                                                   |

--------------------------------------------------------------------------------------------------------------------
## Commands <br><br>

### Viewing help : `help`

Shows a message explaning how to access the help page.
 
Can also be accessed by pressing `F1`. Pressing `F1` again or `esc` will close the window if it is in **focus**.

![help message](images/helpMessage.png)

Format: `help`<br><br>

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`<br><br>

### Adding a person: `add`

Adds a person to the address book.

Format: `add n/NAME [p/PHONE_NUMBER] [e/EMAIL] [a/ADDRESS] [g/GAME]... [t/TAG]... [pt/TIME-TIME]…​`

<box type="tip" seamless>

**Tip:** A person can have any number of tags, games and preferred times (including 0)   
**Tip:** Preferred time `pt/` TIME-TIME should be in the form of "HHmm-HHmm" and a valid time range that is not overnight
</box>

Examples:
* `add n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01`
* `add n/Betsy Crowe t/friend e/betsycrowe@example.com a/Newgate Prison p/1234567 t/criminal pt/2100-2300`<br><br>



### Editing a person : `edit`

Edits an existing person in the address book.

Format: `edit INDEX [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [g/GAME]… [t/TAG]… [pt/PREFERRED TIME]…​`

* Edits the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.
* When editing tags, games or preferred times, the existing tags/games/preferred times of the person will be removed i.e adding of tags/games is not cumulative.
* You can remove all the person’s tags/games/preferred times by typing `t/`, `g/` or `pt/` without
    specifying any tags after it.

Examples:
*  `edit 1 p/91234567 e/johndoe@example.com` Edits the phone number and email address of the 1st person to be `91234567` and `johndoe@example.com` respectively.
*  `edit 2 n/Matthew g/Overwatch g/Valorant` Edits the name of the 2nd person to be `Matthew` with games `Overwatch` and `Valorant`.
*  `edit 2 n/Betsy Crower t/ pt/` Edits the name of the 2nd person to be `Betsy Crower` and clears all existing tags and preferred times.<br><br>

### Adding a game : `addgame`

Adds a game to an existing person in the address book.

Format: `addgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`

* Adds a game `GAME` to the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* If the game `GAME` already exists for that person, it will not be added. It may be edited using the editgame command instead.

Examples:
* `addgame 1 g/Overwatch` Adds the game `Overwatch` to the 1st person.
* `addgame 2 g/League of Legends u/Potato s/Pro r/Support` Adds the game `League of Legends` to the 2nd person with the following information: Username: `Potato` Skill level: `Pro` Role: `Support`<br><br>

### Editing a game : `editgame`

Edits the game of an existing person in the address book.

Format: `editgame INDEX g/GAME [u/USERNAME] [s/SKILLLEVEL] [r/ROLE]​`

* Edits the game `GAME` of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* At least one of the optional fields must be provided.
* Existing values will be updated to the input values.

Examples:
*  `editgame 1 g/Overwatch u/Potato` Edits the username of game `Overwatch` of the 1st person to be `Potato`.
*  `editgame 2 g/League of Legends u/Potato s/Pro r/Support` Edits the game `League of Legends` of the 2nd person to have the following information: Username: `Potato` Skill level: `Pro` Role: `Support`<br><br>

### Deleting a game : `deletegame`

Deletes the specified game from an existing person in the address book.

Format: `deletegame INDEX g/GAME`

* Deletes the game `GAME` of the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​<br><br>


### Favouriting a game : `favgame`

Gives a **singular** game under an existing person in the address book the "favourite" status.

Format: `favgame INDEX g/GAME`

* Favourites the game `GAME` of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Favourited games are denoted by a star icon.
* There will be no observable change if this command is used on a game that is already given the "favourite" status.

Examples:
* `favgame 2 g/Overwatch` Sets the game "Overwatch" of the 2nd person to "favourite".<br><br>

### Un-favouriting a game : `unfavgame`

Removes the "favourite" status from a **singular** game under an existing person in the address book.

Format: `unfavgame INDEX g/GAME`

* Un-favourites the game `GAME` of the person at the specified `INDEX`. The index refers to the index number shown in the displayed person list. The index **must be a positive integer** 1, 2, 3, …​
* Once un-favourited, the star icon for that particular game should disappear.
* There will be no observable change if this command is used on a game that is not set to "favourite".

Examples:
* `unfavgame 3 g/LoL` Remove the "favourite" status from the game "LoL" of the 3rd person.<br><br>

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`<br>
  ![result for 'find alex david'](images/findAlexDavidResult.png)<br><br>

### Locating persons by time range: `findtime`

Finds persons whose preferred time ranges overlap with any of given time range.

Format: `findtime TIME-TIME [TIME-TIME]...`

<box type="tip" seamless>

**Tips:** TIME-TIME should be in the form of "HHmm-HHmm" with no extra space 
and `HHmm` should be a valid 4-digit number within 0000-2359, while the first time should be before the second time.
Overnight range is not accepted, e.g. `2300-0100` is not accepted.

You can enter more than one time range to search for.

You can enter the same time twice e.g. `2300-2300` to only search that time.
</box>

* The search is border-sensitive. e.g. `1200-1300` will match `1300-1400`
* The order of ranges does not matter.
* Persons matching at least one range will be returned(i.e. `OR` search).

Examples:
* `findtime 2030-2100` returns persons who have at least one preferred time range overlaps with the specified range.
* `findtime 2030-2100 2230-2330` returns persons with any preferred time range overlapping with any 
one of the specified ranges<br><br>

### Deleting a person : `delete`

Deletes the specified person from the address book.

Format: `delete INDEX`

* Deletes the person at the specified `INDEX`.
* The index refers to the index number shown in the displayed person list.
* The index **must be a positive integer** 1, 2, 3, …​

Examples:
* `list` followed by `delete 2` deletes the 2nd person in the address book.
* `find Betsy` followed by `delete 1` deletes the 1st person in the results of the `find` command.<br><br>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`<br><br>

### Undoing previous command : `undo`

Undoes the previous command.
Only undoes commands that change the content of GamerBook or the displayed list of contacts.

Supported commands: `list`, `add`, `edit`, `addgame`, `editgame`, `deletegame`, `favgame`, `unfavgame`,
`find`, `findtime`, `delete`, `clear`

Format: `undo`<br><br>

### Exiting the program : `exit`

Exits the program.

Format: `exit`<br><br>

### Data storage

GamerBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`.

GamerBook data are saved in the hard automatically after any command that changes the data.

GamerBook data are loaded from `[JAR file location]/data/addressbook.json` automatically when you open the app.<br><br>

### Saving the data file : `save`

Manually saves the data in another JSON file `[JAR file location]/data/save.json`.<br><br>

### Loading the data file : `load`

Manually loads the data from JSON file `[JAR file location]/data/save.json`.<br><br>

### Editing the data file

GamerBook data are saved automatically as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<box type="warning" seamless>

**Caution:**
If your changes to the data file makes its format invalid, GamerBook will discard all data and start with an empty data file at the next run.  Hence, it is recommended to take a backup of the file before editing it.<br>
Furthermore, certain edits can cause the GamerBook to behave in unexpected ways (e.g., if a value entered is outside the acceptable range). Therefore, edit the data file only if you are confident that you can update it correctly.
</box><br><br>

### Archiving data files `[coming in v2.0]`

_Details coming soon ..._

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Known issues

1. **When using multiple screens**, if you move the application to a secondary screen, and later switch to using only the primary screen, the GUI will open off-screen. The remedy is to delete the `preferences.json` file created by the application before running the application again.
2. **If you minimize the Help Window** and then run the `help` command (or use the `Help` menu, or the keyboard shortcut `F1`) again, the original Help Window will remain minimized, and no new Help Window will appear. The remedy is to manually restore the minimized Help Window.

--------------------------------------------------------------------------------------------------------------------
