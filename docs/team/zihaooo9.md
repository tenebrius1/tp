---
layout: page
title: Zi Hao's Project Portfolio Page
---

### Project: CliTutors

`CliTutors` is a desktop app for **managing private tutoring jobs**, optimized for use via a **Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you are a private tuition agency with a **big list of tutors to manage**, `CliTutors` can help you to manage matching tutors and students for private tuition faster than using a regular database.

Given below are my contributions to the project.

* **New Feature**: Added a `match` command (Pull requests [\#68](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/68))
  * What it does: allows the user to match students to tutors
  * Justification: This feature is one of the core feature of `CLITutors` as one of the use case of this product is to match students to tutors whom teach the subject that the students want to learn
  * Highlights: I had to come up with different ways to match students to tutors and decide the most optimal way to do it. Students have multiple tags as well and I had to account for it when matching, making implementation of this feature more challenging.

* **New Feature**: Added sorting functionality to match tutor list after a `match` command. (Pull requests [\#120](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/120))
  * What it does: Sorts the matched tutor list based on number of matching tags with students after matching a student to tutor.
  * Justification: This feature allows tutors who have more matching tags with students to be displayed first, which provides students with the conevnience to view the tutor that is more suited for them first.
  * Highlights: Implementation of this feature was very challenging due to `FilteredList` being an unmodifiable list. It took me some time to find a workaround as I had to study the code base more carefully to find out where I was able to modify the list.

* **Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=t17&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=zihaooo9&tabRepo=AY2122S1-CS2103T-T17-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

* **Project management**:
  * Managed release [`v1.3`](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/132) on GitHub (Pull requests [\#132](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/132))

* **Enhancements to existing features**:
  * Modified tags to be tailored for `CLITutors` (Pull requests [\#54](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/54),  [\#63](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/63), [\#93](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/93))
  * Added `LevelSubjectCode` and `QualitficationsCode` to reflect subjects taught and qualification respectively. (Pull requests [\#54](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/54), [\#93](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/93))
  * Updated the GUI color scheme and design to match our mock-up (Pull requests [\#72](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/72), [\#74](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/74), [\#99](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/99), [\#126](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/126))
  * Implemented matched tutor list into UI and seperated Person Card to Student and Tutor Card (Pull requests [\#99](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/99),  [\#126](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/126))
  * Made a unique phone list to ensure there is no duplicated phone numbers (Pull request [\#199](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/199))

* **Documentation**:
  * User Guide:
    * Added navigating the user guide section (Pull request [\#124](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/124))
    * Updated command syntax section and notes on command format (Pull request [\#124](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/124))
    * Added documentation for the `match` feature (Pull request [\#20](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/20))
    * Added Appendix to better explain tags and qualifications (Pull request [\#47]())
  * Developer Guide:
    * Updated the `Storage` component to reflect changes made to it for `CLITutors` (Pull request [\#103](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/103))
    * Added implementation details of the `match` feature (Pull requests [\#112](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/112), [\#122](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/122))
    * Came up with some user stories
    * Added non-functional requirements and glossary (Pull request [\#27](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/27))
    * Added instructions for manual testing (Pull request [\#127](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/127))

* **Community**:
  * PRs reviewed (with non-trivial review comments): (Pull requests [\#52](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/52), [\#58](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/58), [\#113](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/113), [\#123](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/123))
  * Reported bugs and suggestions for other teams in the class through [PE-D](https://github.com/zihaooo9/ped/issues)
