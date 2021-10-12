---
layout: page
title: User Guide
---

LeadsForce is a desktop app that is optimized for use via a Command Line Interface (CLI) while still having the benefits of a Graphical User Interface (GUI).
It provides a new way to streamline the process for student financial advisors to find the right clients to contact.
We aim to help you manage your leads by making it effortless to store information regarding them and retrieving this information seamlessly.
Finding your next lead has never been easier.
## Table of Contents

* [Quick Start](#quick-start)
* [Client Information](#client-information)
* [Features](#features)
    * [Create new contact: create](#create-new-contact--create)
    * [Retrieve particular contact: view](#retrieve-particular-contact--view)
    * [Update existing contact: update](#update-existing-contact--update)
    * [Delete particular contact: delete](#delete-particular-contact--delete)
    * [List all contacts](#list-all-contacts--list)
    * [Clearing all entries: clear](#clearing-all-entries--clear)
    * [Sort Contacts: sort](#sort-contacts--sort)
    * [Locating person by name: find](#locating-clients-by-keywords--find)
    * [Locating clients by keywords](#locating-clients-by-keywords--search)
    * [Filter current list](#filter-current-list-by-keywords--filter)
    * [Exiting the program: exit](#exiting-the-program--exit)
    * [Saving data](#saving-the-data)
    * [Edit data file](#editing-data-file)
* [FAQ](#faq)
* [Command Summary](#command-summary)

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

2. Download the latest `leadsforce.jar` from [here](https://github.com/AY2122S1-CS2103T-T17-3/tp/releases).

3. Copy the file to the folder you want to use as the _home folder_ for your LeadsForce.

4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>
   ![Ui](images/Ui.png)

5. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

    * **`list`** : Lists all contacts.

    * **`add`**`n/John Doe p/98765432 e/johnd@example.com a/John street, block 123, #01-01` : Adds a contact named `John Doe` to the Address Book.

    * **`delete`**`3` : Deletes the 3rd contact shown in the current list.

    * **`clear`** : Deletes all contacts.

    * **`exit`** : Exits the app.

6. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Client Information

Every client that is registered in LeadsForce have the following attributes that has the corresponding attribute type and argument tag.

Client Attribute | Type of Attribute | Argument Tag
-----------------|-----------------|-----------------
Client ID (**unique**) | integer | None. Assigned on creation of new contact
Name (**Compulsory**) | String | name/
Email (**Compulsory**)| String (email address)| email/
Contact number | Integer (8 digits long)| contact/
Risk appetite | Integer from 1-5, <br>where 1 is very low risk tolerance and 5 is very high risk tolerance| risk-appetite/
Last met/contacted | Date | last-met/
Current financial plans | List of Strings | current-plans-add/<br>current-plans-remove/
Disposable Income | Integer | disposable-income/



--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* In the format for the commands provided, words which are in UPPERCASE refers to input that the user must key in

* Inputs in square brackets are optional input:<br>
  e.g. `KEYWORD [OTHER_KEYWORD]` can be in the form of `firstName` or `firstName lastName`

* Inputs with `…`​ at the end refers to inputs that can be used multiple times in that command
  .<br>
  e.g. `[/tag ATTRIBUTE]…​` can be in the form of `/email @gmail.com` or `/email @gmail.com /risk-appetite 5`
</div>


### Create New Contact : `create`

Adds a new client to the address book.

Format: `create {client’s name} <email>/{email} <phone-no>/{phone number} <risk-appetite>/{risk appetite}...​`

* A client must have minimally the name and email tag filled during creation
* Any other tags are optional
* Tags that can be added are as seen in the client information in the Client Info Section

Examples:
* `create Benedict Chua email/benchua@hotmail.com`
* `create Keith email/keithtan@ymail.com phone-no/12345678 risk-appetite/4`


### Retrieve Particular Contact : `view`

View a particular client on your address book to view the client’s information.

Format: `view {client’s id number}`

Example: `view 2` would be used to view client 2's information

### Update Existing Contact : `update`

Update the information of existing users by using the “update” command. This can be used to change the
attributes of a client, using the tag of the client’s attribute.

* Multiple attributes could be changed with one command.

Format: `update {Client’s id number} <attribute>/{changed value of attribute} ...`

Examples:

* `update 15 name/Dominic` command changes the name of client 15 to “Dominic”.
* `update 3 contact-number/12345678 risk-appetite/5` command changes the contact number to “12345678” and
  the risk appetite to 5 for the client who’s id number is 3.

### Delete particular contact : `delete`

Deletes an existing client from the address book using any specified attribute to identify the client.

Format: `delete <attribute>/{value}`

* Attributes would be limited to client id, email or contact number.
* It is possible to bulk delete multiple clients by inputting multiple keys separated by ‘,’.

Examples:
* `delete id/4,6,7`(deletes clients with client id 4, client id 6 and client 7)
* `delete email/keithtan@gmail.com`(deletes a client whose email address is keithtan@gmail.com)

### List all contacts : `list`

Shows a list of all tasks in the list.

Format: `list`


### Sort Contacts : `sort`

Sorts clients in order based off the inputted attribute

Format: `sort <attribute>/{ASC/DESC}`

* The asc and desc tag dictates whether filtered client list is sorted in ascending or descending order

Examples:
* `sort risk-appetite/ASC`

### Locating persons by name: `find`

Finds persons whose names contain any of the given keywords.

Format: `find KEYWORD [MORE_KEYWORDS]...`

* The search is case-insensitive. e.g `hans` will match `Hans`
* The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
* Only the name is searched.
* Only full words will be matched e.g. `Han` will not match `Hans`
* Persons matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`

Examples:
* `find John` returns `john` and `John Doe`
* `find alex david` returns `Alex Yeoh`, `David Li`

### Locating clients by keywords : `search`

Finds clients whose contacts match with the given keywords.

Format: `search KEYWORD [MORE_KEYWORDS]... [ATTRIBUTE/ATTRIBUTE_KEYWORD]...`

* `KEYWORD` and `MORE_KEYWORDS` will be used to match will all attribute of the person.
* `ATTRIBUTE/` refers to the argument tag for the client's attribute.
* `ATTRIBUTE_KEYWORD` refers to the keyword that is to be matched with the corresponding client attribute.
* If no `KEYWORD` is provided, search will be based on `ATTRIBUTE/ATTRIBUTE_KEYWORD` only.
* The search is case-insensitive. e.g `keith` will match `Keith`.
* The order of the keywords does not matter. e.g. `John Doe` will match `Doe John`.
* Clients matching at least one keyword will be returned (i.e. `OR` search).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.
* Only clients whose attribute matches with the attribute keyword will be returned (i.e. `AND` search), if attribute keyword is provided.
  e.g. `Tom Tim e/@gmail.com` will return `Tom Lee e/Tom@gmail.com` and not `Tim Shum e/Tim@yahoo.com`.

Examples:
* `search John` returns `john` and `John Doe`
* `search alex david` returns `Alex Yeoh`, `David Li`<br>

### Filter current list by keywords : `filter`

Filter the current list by the given keywords.

Format: `filter [KEYWORD]... [ATTRIBUTE/ATTRIBUTE_KEYWORD]...`

* Works similar to `search` but `filter` works based on the current list shown as opposed to entire lists of contacts.
* `KEYWORD` will be used to match will all attribute of the person.
* If no `KEYWORD` is provided, then filter will be based on `ATTRIBUTE/ATTRIBUTE_KEYWORDS`
* `ATTRIBUTE/` refers to the argument tag for the client's attribute.
* `ATTRIBUTE_KEYWORD` refers to the keyword that is to be matched with the corresponding client attribute.
* The filter is case-insensitive. e.g `keith` will match `Keith`.
* The order of the keywords does not matter. e.g. `John Doe` will match `Doe John`.
* Clients matching at least one keyword will be returned (i.e. `OR` filter).
  e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`.
* Only clients whose attribute matches with the attribute keyword will be returned (i.e. `AND` search), if attribute keyword is provided.
  e.g. `Tom Tim e/@gmail.com` will return `Tom Lee e/Tom@gmail.com` and not `Tim Shum e/Tim@yahoo.com`.

Examples:
* `search John` returns `john` and `John Doe`
* `search alex david` returns `Alex Yeoh`, `David Li`<br>

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

LeadsForce's data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Edit data file

LeadsForce's data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>

--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous LeadsForce home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format | Examples
--------|---------|---------
**Create** | `create {client’s name} <email>/{email} <phone-no>/{phone number} <risk-appetite>/{risk appetite}`| create benedict email/benedict@gmail.com phone-no/90909898 risk-appetite/3 |
**View** | `view {client’s id number}` | view 123 |
**Delete** | `delete <attribute>/{value}` | delete id/4,6,7  |
**Update** | `update {Client’s id number} <attribute>/{change value of attribute}` | update 1234 name/Dominic phone-number/12345678 |
**List** | `list` | - |
**Find** | `find KEYWORD [OTHER_KEYWORD]` | find alex tom |
**Search** | `search [KEYWORD]... [ATTRIBUTE/ATTRIBUTE_KEYWORD]...` | search * email/doe@gmail.com risk-appetite/5 |
**Filter** | `filter KEYWORD [OTHER_KEYWORD] [ATTRIBUTE/ATTRIBUTE_KEYWORD]...` | search * email/doe@gmail.com p/9 |
**Sort** | `sort <attribute>/{ASC/DESC}` | sort risk-appetite/asc |
**Exit** | `exit` | - |
