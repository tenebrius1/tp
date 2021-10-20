---
layout: page
title: CLITutors Developer Guide
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
* Table of Contents {:toc}

**`CLITutors`** is a desktop app for **managing private tutoring jobs**, optimized for use via a **Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you have a big list of tutors to manage, `CLITutors` helps you to manage matching tutors and students for private tuition faster than using a regular database.

## **Navigating this Developer Guide**
Before diving into the rest of the contents in our developer guide, the following are a few important syntaxes to take note of to facilitate your reading:

| Syntax                                                                  | Description                                         |
| ----------------------------------------------------------------------- | --------------------------------------------------- |
| `Markdown`                                                              | Denotes distinct classes, their methods or examples |
| <div markdown="span" class="alert alert-info">:information_source: Note</div> | Important things to take note of                    |
| <kbd>Keyboard</kbd>                                                     | Keyboard actions                                    |

<div style="page-break-after: always;"></div>

## **Acknowledgements**

We would like to thank Jun Xiong and Damith for supervising our project.

## **Setting up, getting started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

## **Design**

<div markdown="span" class="alert alert-info"> :bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S1-CS2103T-T17-2/tp/tree/master/docs/diagrams/) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.
</div>

### Architecture
<p align="center">
  <img src="images/ArchitectureDiagram.png" />
</p>

The ***Architecture Diagram*** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for:
* At app launch: Initializes the components in the correct sequence, and connects them up with each other.
* At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#Common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

* [**`UI`**](#UI-component): The UI of the App.
* [**`Logic`**](#Logic-component): The command executor.
* [**`Model`**](#Model-component): Holds the data of the App in memory.
* [**`Storage`**](#Storage-component): Reads data from, and writes data to, the hard disk.

Each of the four components,

* defines its *API* in an `interface` with the same name as the Component.
* exposes its functionality using a concrete `{Component Name}Manager` class (which implements the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component (see the class diagram given below) defines its API in the `Logic.java` interface and exposes its functionality using the `LogicManager.java` class which implements the `Logic` interface.

<p align="center">
  <img src="images/LogicClassDiagram.png" />
</p>

<div style="page-break-after: always;"></div>

**How the architecture components interact with each other**

The *Sequence Diagram* below shows how the components interact with each other for the scenario where the user issues the command `delete s 1`.

<p align="center">
  <img src="images/ArchitectureSequenceDiagram.png" />
</p>

The sections on the next few pages will give more details of each component.

<div style="page-break-after: always;"></div>

### UI component
![Structure of the UI Component](images/UiClassDiagram.png)
**API** : [`Ui.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `PersonListPanel`, `StatusBarFooter` etc. 
All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities 
between classes that represent parts of the visible GUI. `PersonListPanel` is split up into `TutorCard` and `StudentCard` on the UI as 3 lists: Tutor List, Student List, and Matchlist.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

* executes user commands using the `Logic` component.
* listens for changes to `Model` data so that the UI can be updated with the modified data.
* keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
* depends on some classes in the `Model` component, as it displays `Person` object residing in the `Model`.

### Logic component

### Model component
**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-T17-2/tp/blob/master/src/main/java/seedu/address/model/Model.java)

![ModelClassDiagram](images/ModelClassDiagram.png)

The `Model` component,

* stores the address book data i.e., all `Tutor` and `Student` objects (which are contained in a `UniqueTutorList` and `UniqueStudentList` object respectively).
* stores the currently 'selected' `Tutor` and `Student` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Tutor>` and `ObservableList<Student>` respectively that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
* stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
* does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

<div markdown="span" class="alert alert-info">:information_source: **Note:** An alternative (arguably, a more OOP) model is given below. It has a `Tag` list in the `AddressBook`, which `Tutor` and `Student` references. This allows `AddressBook` to only require one `Tag` object per unique tag, instead of each `Tutor` and `Student` needing their own `Tag` objects.<br>

![BetterModelClassDiagram](images/BetterModelClassDiagram.png)

</div>

### Storage component

### Common classes


## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

## **Documentation, logging, testing, configuration, dev-ops**

* [Documentation guide](Documentation.md)
* [Testing guide](Testing.md)
* [Logging guide](Logging.md)
* [Configuration guide](Configuration.md)
* [DevOps guide](DevOps.md)

## Appendix: Requirements

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

(For all use cases below, the System is the `CLITutorsBook` and the Actor is the user, unless otherwise specified)

#### Use case (UC01): Add a Tutor

##### MSS

1. User keys in the tutor's details
2. Tutor is added to the database
   Use case ends.

##### Extensions

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC02): Add a Student

##### MSS

1. User keys in the student's details
2. Tutor is added to the database
   Use case ends.

##### Extensions

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC03): Edit a Student name

##### MSS

1. User enters the command to edit the name of Student A
2. System replies with a confirmation message that the edit is successful
   Use case ends.

##### Extensions

* 1a. User wants to <ins>edit the phone number (UC04)</ins> of Student A.
  Use case ends.
* 2a. User keys in an incorrect prefix for editing name.
    * 2a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC04): Edit a Student phone number

##### MSS

1. User enters the command to edit the phone number of Student B
2. System replies with a confirmation message that the edit is successful
   Use case ends.

##### Extensions

* 1a. User wants to <ins>edit the name (UC03)</ins> of Student B.
  Use case ends.
* 2a. User keys in an incorrect prefix for editing phone number.
    * 2a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC05): Delete a Tutor

##### MSS

1. User requests to list tutors
2. `CLITutorsBook` shows a list of tutors
3. User requests to delete a specific tutor in the list
4. `CLITutorsBook` deletes the tutor
   Use case ends.

##### Extensions

* 2a. The list is empty.
  Use case ends.
* 3a. The given index is invalid.
    * 3a1. `CLITutorsBook` shows an error message.
      Use case resumes at step 2.

#### Use case (UC06): Delete a Student

##### MSS

1. User requests to list students
2. CLITutorsBook shows a list of students
3. User requests to delete a specific student in the list
4. CLITutorsBook deletes the student
   Use case ends.

##### Extensions

* 2a. The list is empty.
  Use case ends.
* 3a. The given index is invalid.
    * 3a1. `CLITutorsBook` shows an error message.
      Use case resumes at step 2.

#### Use case (UC07): Finding a Tutor

##### MSS

1. User requests to find a tutor using his/her name
2. `CLITutorsBook` shows all tutors that contains that specific name
   Use case ends.

##### Extensions

* 1a. The list is empty.
  Use case ends.
* 1b. There is no one with that specific name.
  Use case ends.

#### Use case (UC08): Finding a Student

##### MSS

1. User requests to find a student using his/her name
2. `CLITutorsBook` shows all students that contains that specific name
   Use case ends.

##### Extensions

* 1a. The list is empty.
  Use case ends.
* 1b. There is no one with that specific name.
  Use case ends.

#### Use case (UC09): Matching a student with the tutors

##### MSS

1. User requests to match a student with tutors with the required qualifications
2. `CLITutorsBook` shows the tutors that are able to match with the specified student in a window
   Use case ends.

##### Extensions

* 1a. There are no tutors that match the requirements of the student.
  Use case ends.

### Non-Functional Requirements

1. Should work on any *mainstream OS* as long as it has Java 11 or above installed.
2. Should be able to run on both 32-bit and 64-bit systems.
3. Should be able to hold up to 1000 persons without a noticeable sluggishness in performance for typical usage.
4. Should not require the use of remote databases.
5. System should be able to run even if the data file has errors arising from a user manually editing it.
6. Response to user command (add, delete, edit, match) should be visible within 2 seconds.
7. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
8. The system should be usable by a novice who may not be familar with CLI. i.e. Users are not expected to key in complicated commands to get desired outputs.
9. Product should not be able to send out any information to the student
10. Product should not be able to verify the legitimacy of the information of the student or private tutor.

### Glossary

| Term              | Meaning                                                                                                                                           |
|:----------------- |:------------------------------------------------------------------------------------------------------------------------------------------------- |
| **CLI**           | Command-Line Interface                                                                                                                            |
| **JSON**          | JSON stands for ***JavaScript Object Notation*** which is a lightweight format for data storage                                                   |
| **Mainstream OS** | Windows, macOS, Linux   
| **Index**         | Index number shown in the displayed list. The index must be a positive integer 1, 2, 3, …​                                                        |
| **Qualification** | How qualified the tutor is with regards to these levels:<br>0.Pre-University<br>1.University Student<br>2.Post-Grad<br>3.MOE-Trained              |
| **Tag**           | Subjects each Tutors teach are saved under tags as ``[X][Y]`` (X is Level code and Y is Specific Subject code). eg. `PM` stands for Primary Math. |
| **Bloatware**     |                                                         Software that uses excessive memory and disk space, which makes the program run slow|
