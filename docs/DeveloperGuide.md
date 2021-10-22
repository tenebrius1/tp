---
layout: page
title: Developer Guide
nav-text: Developer Guide
---

<p align="center">
  <img src="https://i.imgur.com/dLt7IDY.png" />
</p>

<p align="center">
  <img src="https://i.imgur.com/FKI1H1U.png" width="300"/>
</p>

# CLITutors Developer Guide (v1.3)

<div style="page-break-after: always;"></div>

## Table of Contents
{: .no_toc}
1. Table of Contents
{:toc}

**`CLITutors`** is a desktop app for **managing private tutoring jobs**, optimized for use via a **Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you have a big list of tutors to manage, `CLITutors` helps you to manage matching tutors and students for private tuition faster than using a regular database.

--------------------------------------------------------------------------------------------------------------------
## **Navigating this Developer Guide**
Before diving into the rest of the contents in our developer guide, the following are a few important syntaxes to take note of to facilitate your reading:

| Syntax                                                                  | Description                                         |
| ----------------------------------------------------------------------- | --------------------------------------------------- |
| `Markdown`                                                              | Denotes distinct classes, their methods or examples |
| <div markdown="span" class="alert alert-info">:information_source: Note</div> | Important things to take note of                    |
| <kbd>Keyboard</kbd>                                                     | Keyboard actions                                    |

<div style="page-break-after: always;"></div>

--------------------------------------------------------------------------------------------------------------------
## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

--------------------------------------------------------------------------------------------------------------------
## **Design**

<div markdown="span" class="alert alert-info"> :bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S1-CS2103T-T17-2/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture

![Architecture Diagram](images/ArchitectureDiagram.png)

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of the main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for:
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#ui-component): The UI of the App.
* [**`Logic`**](#logic-component): The command executor.
* [**`Model`**](#model-component): Holds the data of the App in memory.
* [**`Storage`**](#storage-component): Reads data from and writes data to the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

![LogicClassDiagram](images/LogicClassDiagram.png)

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete s 1`.

![Architecture Sequence Diagram](images/ArchitectureSequenceDiagram.png)

The sections on the next few pages will give more details of each component.

<div style="page-break-after: always;"></div>

### UI component
**API** : [`Ui.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

Here's a class diagram of the `UI` component:

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc.
All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities
between classes that represent parts of the visible GUI.
The `PersonListPanel` is split up into `TutorCard` and `StudentCard` on the UI as 3 lists: Tutor List, Student List, and Match List (containing Tutor entries only).

The `UI` component uses the JavaFx UI framework. The layout of these UI parts is defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

![Logic Class Diagram](images/LogicClassDiagram.png)

How the `Logic` component works:
1. When `Logic` is called upon to execute a command, it uses the `AddressBookParser` class to parse the user command.
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to add a person).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete s 1")` API call.

![Interactions Inside the Logic Component for the `delete 1` Command](images/tracing/LogicSequenceDiagram.png)

<div markdown="span" class="alert alert-info"> :information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Taking a deeper look into the classes in `Logic` that are used for parsing a user command:

![Insert class diagram for Parser component](images/ParserClasses.png)

How the parsing works:
* When called upon to parse a user command, the `AddressBookParser` class creates an `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `AddCommandParser`) which uses the other classes shown above to parse the user command and create an `XYZCommand` object (e.g., `AddCommand`) which the `AddressBookParser` returns back as a `Command` object.
* All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model component
**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/model/Model.java)

Here's a class diagram of the `Model` component:

![ModelClassDiagram](images/ModelClassDiagram.png)

The `Model` component,

* stores the address book data i.e., all `Tutor` and `Student` objects (which are contained in a `UniqueTutorList` and `UniqueStudentList` object respectively).
* stores the currently 'selected' `Tutor` and `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Tutor>` and `ObservableList<Student>` respectively that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` object.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Tutor` and `Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Tutor` and `Student` needing their own `Tag` objects.<br>

![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>

### Storage component
**API** : [`Storage.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

Here's a class diagram of the `Storage` component:

![Storage component diagram](images/StorageClassDiagram.png)

The `Storage` component,
* can save both address book data and user preference data in JSON format, and read them back into corresponding objects.
* inherits from both `AddressBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
* depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

### Common classes

Classes used by multiple components are in the `seedu.addressbook.commons` package.

--------------------------------------------------------------------------------------------------------------------
## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Add feature
#### What it is
#### Implementation details

### Delete feature
#### What it is
Deletes the tutor or student at the specified `INDEX` from the displayed tutor/student list.

#### Implementation details
Similar to the `AddCommand` class above, the `DeleteCommand` class extends the `Command` class. The actual execution of the `delete` command is similar to that of the `add` command, except the `delete` command decides on the tutor/student to delete based on the `INDEX` of the tutor/student displayed on the `filteredTutors` list or the `filteredStudents` list of the `ModelManager` class.

##### Sequence of action
{:.no_toc}

Given below is an example usage scenario and how the `delete` command implementation behaves at each step:

1. The `LogicManager` calls `AddressBookParser#parseCommand` to parse the given user input. 
2. The `AddressBookParser` identifies the user command (`delete`) and creates a new `DeleteCommandParser` object. It then calls `DeleteCommandParser#parse` with the command arguments as the parameter.
3. `DeleteCommandParser` then generates a `DeleteCommand` object with the `INDEX` (of the tutor/student to be deleted) and `PersonType` as parameters.
4. As the `PersonType` is a tutor, `DeleteCommand` retrieves the `Tutor` (to be deleted) from the `filteredTutors` list of the `ModelManager`. `DeleteCommand` will then call `Model#deleteTutor`, which will delete the tutor from the tutor list.
5. Lastly, a new `CommandResult` with the success message is returned to the `LogicManager`.

Given below is a sequence diagram to show how the `delete` implementation works for a **valid** `delete` tutor input:

![DeleteCommandSequenceDiagram](images/DeleteCommandSequenceDiagram.png)

<div markdown="span" class="alert alert-info"> :information_source: **Note:** The lifeline for `DeleteCommandParser` and `DeleteCommand` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Given below is an activity diagram to show how the `delete` command works for a **valid** `delete` tutor input:

![DeleteCommandActivityDiagram](images/DeleteCommandActivityDiagram.png)

#### Design Considerations
##### Aspect: How `delete` is executed
{:.no_toc}

- **Alternative 1 (current choice)**: User can `delete` only one tutor/student at a time.
    - Pros: Reduces the lack of potential errors due to the decreased complexity of the code. It is also easier to implement since there are lesser use cases to consider.
    - Cons: It is less intuitive for the user as they are now limited by having to `delete` each tutor/student one by one.

<div markdown="span" class="alert alert-info"> :information_source: **Note:** In our current implementation, Users can delete all tutors/students from their respective lists using the `clear` command. This is to make the app more intuitive for users with clear goals on the command they want to execute while reducing any unnecessary complexity in our `delete` command. 
</div>

- **Alternative 2**: User can `delete` multiple tutors/students using the command at the same time.
    - Pros: Allows the user more flexibility in deleting tutors/students instead of just deleting them one by one.
      For example, users can choose to `delete` one or many tutors/students, depending on their command input.
    - Cons: This will cause the code to become more complex in dealing with many possible inputs and scenarios, leading to a higher amount of potential errors.

### Edit feature
#### What it is
#### Implementation details

### List feature
#### What it is
#### Implementation details

### Find feature
#### What it is
#### Implementation details

### Match feature
#### What it is
#### Implementation details

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

#### Target user profile

* Has a need to manage a significant number of private tutors and students
* Likes a lightweight tutor management application to reduce *bloatware*
* Wants to have a platform to match students to private tutors
* Prefers desktop app over other platforms
* Can type fast
* Prefers typing to mouse interactions
* Is comfortable with using a CLI

#### Value Proposition

Manage the matching of tutors and students for teaching jobs more efficiently than a regular GUI-driven application.

### User Stories

Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| Priority | As a ...                                       | I want to ...                                         | So that I can …                                                   |
| -------- | ---------------------------------------------- | ----------------------------------------------------- | ----------------------------------------------------------------- |
| `* * *`  | new user ready to adopt the app for my own use | purge all data                                        | clear all dummy data and begin to use the app                     |
| `* * *`  | user                                           | delete students and tutors from the list              | remove those people who do not use the service anymore            |
| `* * *`  | user                                           | add new students and tutors to the app                |                                                                   |
| `* * *`  | user                                           | view my saved data                                    | recall the information of my tutors/students                      |
| `* * *`  | user                                           | find people that match some keywords                  | avoid scrolling through thousands of entries of data              |
| `* * *`  | user                                           | assign tags to tutors and students                    | categorize them by their needs                                    |
| `* * *`  | user                                           | match students and tutors according to their needs    |                                                                   |
| `* * *`  | user                                           | To get feedback from the system                       | see if my commands are working                                    |
| `* * *`  | user                                           | type the commands I want to execute                   |                                                                   |
| `* * *`  | busy user                                      | use shorter commands                                  | save time keying in full commands                                 |
| `* *`    | first-time user                                | see some sample data when I open the app              | easily try out the features without needing to add my data first  |
| `* *`    | clumsy user                                    | have a safety net                                     | avoid purging my data by accident                                 |
| `* *`    | user                                           | edit personal details                                 | update any new particulars of the tutors/students                 |
| `* *`    | user                                           | sort by tags                                          | easily find groups of people with the same tags                   |
| `* *`    | forgetful user                                 | see the available commands for the program            | know what tasks I can do with the program                         |
| `* *`    | user                                           | search students and tutors by tags                    | easily import data from one machine to another                    |
| `* *`    | user                                           | load in pre-existing data                             | save time keying in full commands                                 |
| `* *`    | user                                           | assign ratings to tutors                              | see how well their ratings are when teaching students             |
| `* *`    | user                                           | undo my commands made                                 | retrieve the previous state of data if I’ve made a mistake        |
| `* *`    | user                                           | add priority for matching certain tutors and students | schedule urgent cases ahead of other cases for greater efficiency |
| `*`      | user                                           | be able to go through the history of my command       | avoid reentering frequently used commands                         |
| `*`      | user                                           | save pictures of students and tutors                  | match faces to names                                              |
| `*`      | first-time user                                | view an interactive tutorial                          | learn how to use the app                                          |

### Use Cases

(For all use cases below, the System is the `CLITutorsBook` and the Actor is the User, unless otherwise specified)

#### Use case (UC01): Add a Tutor
{:.no_toc}

**MSS**

1. User keys in the tutor's details
2. Tutor is added to the database
   <br>Use case ends.

**Extensions**

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      <br>Use case ends.

#### Use case (UC02): Add a Student
{:.no_toc}

**MSS**

1. User keys in the student's details
2. Tutor is added to the database
   <br>Use case ends.

**Extensions**

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      <br>Use case ends.

#### Use case (UC03): Edit a Student name
{:.no_toc}

**MSS**

1. User enters the command to edit the name of Student A
2. System replies with a confirmation message that the edit is successful
   <br>Use case ends.

**Extensions**

* 1a. User wants to <ins>edit the phone number (UC04)</ins> of Student A.
  <br>Use case ends.
* 2a. User keys in an incorrect prefix for editing name.
    * 2a1. System displays an error message to tell the user about the format error.
      <br>Use case ends.

#### Use case (UC04): Edit a Student phone number
{:.no_toc}

**MSS**

1. User enters the command to edit the phone number of Student B
2. System replies with a confirmation message that the edit is successful
   <br>Use case ends.

**Extensions**

* 1a. User wants to <ins>edit the name (UC03)</ins> of Student B.
  <br>Use case ends.
* 2a. User keys in an incorrect prefix for editing the phone number.
    * 2a1. System displays an error message to tell the user about the format error.
      <br>Use case ends.

#### Use case (UC05): Delete a Tutor
{:.no_toc}

**MSS**

1. User requests to list tutors
2. `CLITutorsBook` shows a list of tutors
3. User requests to delete a specific tutor in the list
4. `CLITutorsBook` deletes the tutor
   <br>Use case ends.

**Extensions**

* 2a. The list is empty.
  <br>Use case ends.
* 3a. The given index is invalid.
    * 3a1. `CLITutorsBook` shows an error message.
      <br>Use case resumes at step 2.

#### Use case (UC06): Delete a Student
{:.no_toc}

**MSS**

1. User requests to list students
2. `CLITutorsBook` shows a list of students
3. User requests to delete a specific student from the list
4. `CLITutorsBook` deletes the student
   <br>Use case ends.

**Extensions**

* 2a. The list is empty.
  <br>Use case ends.
* 3a. The given index is invalid.
    * 3a1. `CLITutorsBook` shows an error message.
      <br>Use case resumes at step 2.

#### Use case (UC07): Find a Tutor
{:.no_toc}

**MSS**

1. User requests to find a tutor using his/her name
2. `CLITutorsBook` shows all tutors that contain that specific name
   <br>Use case ends.

**Extensions**

* 1a. The list is empty.
  <br>Use case ends.
* 1b. There is no one with that specific name.
  <br>Use case ends.

#### Use case (UC08): Find a Student
{:.no_toc}

**MSS**

1. User requests to find a student using his/her name
2. `CLITutorsBook` shows all students that contain that specific name
   <br>Use case ends.

**Extensions**

* 1a. The list is empty.
  <br>Use case ends.
* 1b. There is no one with that specific name.
  <br>Use case ends.

#### Use case (UC09): Match a student with the tutors
{:.no_toc}

**MSS**

1. User requests to match a student with tutors with the required qualifications
2. `CLITutorsBook` shows the tutors that are able to match with the specified student in a window
   <br>Use case ends.

**Extensions**

* 1a. There are no tutors that match the requirements of the student.
  <br>Use case ends.

### Non-Functional Requirements

1. Should work on any *mainstream OS* as long as it has Java 11 or above installed.
2. Should be able to run on both 32-bit and 64-bit systems.
3. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4. Should not require the use of remote databases.
5. System should be able to run even if the data file has errors arising from a user manually editing it.
6. Response to user commands (add, delete, edit, match) should be visible within 2 seconds.
7. A user with above-average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
8. The system should be usable by a novice who may not be familiar with CLI. i.e. Users are not expected to key in complicated commands to get desired outputs.
9. Product should not be able to send out any information to the student
10. Product should not be able to verify the legitimacy of the information of the student or private tutor.

### Glossary

| Term              | Meaning                                                                                                                                           |
|:----------------- |:------------------------------------------------------------------------------------------------------------------------------------------------- |
| **CLI**           | Command-Line Interface                                                                                                                            |
| **JSON**          | JSON stands for ***JavaScript Object Notation*** which is a lightweight format for data storage                                                   |
| **Mainstream OS** | Windows, macOS, Linux                                                                                                                             |
| **Index**         | Index number shown in the displayed list. The index must be a positive integer 1, 2, 3, …                                                         |
| **Qualification** | How qualified the tutor is with regards to these levels:<br>0.Pre-University<br>1.University Student<br>2.Post-Grad<br>3.MOE-Trained              |
| **Tag**           | Subjects each Tutor teaches are saved under tags as ``[X][Y]`` (X is Level code and Y is Specific Subject code). eg. `PM` stands for Primary Math.|
| **Bloatware**     | Software that uses excessive memory and disk space, which makes the program run slow                                                              |
