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
