---
layout: page
title: Kleon's Project Portfolio Page
---

# **Project: CLITutors**

## **Overview**

`CliTutors` is a desktop app for **managing private tutoring jobs**, optimized for use via a **Command Line Interface (CLI)** while still having the benefits of a Graphical User Interface (GUI). If you are a private tuition agency with a **big list of tutors to manage**, `CliTutors` can help you to manage matching tutors and students for private tuition faster than using a regular database.

**Code contributed**: [RepoSense link](https://nus-cs2103-ay2122s1.github.io/tp-dashboard/?search=&sort=groupTitle&sortWithin=title&timeframe=commit&mergegroup=&groupSelect=groupByRepos&breakdown=true&checkedFileTypes=docs~functional-code~test-code~other&since=2021-09-17&tabOpen=true&tabType=authorship&tabAuthor=kleonang&tabRepo=AY2122S1-CS2103T-T17-2%2Ftp%5Bmaster%5D&authorshipIsMergeGroup=false&authorshipFileTypes=docs~functional-code~test-code&authorshipIsBinaryFileTypeChecked=false)

## **My contributions**

### **New Features & Enhancements**
- Removed `Email` and `Address` fields, added `Gender` field to `Person` class (Pull request [#48](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/48))
- Split `Person` class into `Tutor` and `Student` classes, along with their associated classes (including `JsonAdaptedPerson`, `UniquePersonList` and `PersonBuilder`) (Pull request [#48](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/48))
- Make `StudentUtil` and `TutorUtil` inherit from `PersonUtil` (Pull request [#53](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/53))
- Integrate `EditCommand` to work with new `Tutor` and `Student` classes (Pull request [#58](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/58))
- Add `Remark` field in `Person` class (Pull request [#113](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/113))
- Make `Phone` accept exactly 8 digits (Pull request [#125](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/125))
- Make `Remark` accept a maximum of 100 characters (Pull request [#191](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/191))
- Code quality enhancements for test code (Pull request [#214](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/214))

### **Documentation**

#### **User Guide**
- Add Use Cases UC01 to UC05 (Pull request [#25](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/25/files))
- Fix broken links and grammar (Pull request [#108](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/108/files))
- Miscellaneous collaborative edits in group discussions

#### **Developer Guide**
- Add `Model` diagrams and description to DG (Pull request [#104](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/104))
- Fix broken links and grammar (Pull request [#108](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/108/files))
- Update `EditCommand` implementation details (Pull requests [#114](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/114), [#119](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/119))
- Miscellaneous collaborative edits in group discussions

#### **Miscellaneous**
- Bug fixes for issues identified in PE-D (Pull request [#191](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/191))

### **Community**
- PRs reviewed (with non-trivial review comments) (Pull requests [#55](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/55), [#62](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/62), [#68](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/68), [#105](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/105), [#121](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/121), [#123](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/123), [#129](https://github.com/AY2122S1-CS2103T-T17-2/tp/pull/129))
- Contributed to forum discussions (Issues [#125](https://github.com/nus-cs2103-AY2122S1/forum/issues/125), [#265](https://github.com/nus-cs2103-AY2122S1/forum/issues/265))
- Tested the product of other teams and [reported bugs](https://github.com/kleonang/ped/issues) during PE-D
