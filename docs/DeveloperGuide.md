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
