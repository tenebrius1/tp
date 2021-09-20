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

### Use Cases

(For all use cases below, the System is the `CLITutorsBook` and the Actor is the user, unless otherwise specified)

#### Use case (UC01): Add a Tutor

1. User keys in the tutor's details
2. Tutor is added to the database
   Use case ends.

##### Extensions

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC02): Add a Student

1. User keys in the student's details
2. Tutor is added to the database
   Use case ends.

##### Extensions

* 1a. User keys in an incorrect format.
    * 1a1. System displays an error message to tell the user about the format error.
      Use case ends.

#### Use case (UC03): Edit a Student name

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

