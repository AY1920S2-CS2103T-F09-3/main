# iGrad 
> A handy guide to help you make the most of your application

## Table of Contents

1. [Introduction](#Introduction)
2. [Features](#Features)
    
    2.1 [Course Builder](#course-builder)
    
    2.2 [Modular Credits Tracker](#modular-credits-mcs-tracker)
    
    2.3 [Cumulative Average Point Tracker](#cumulative-average-point-cap-tracker)

3. [Components](#Components)

    3.1 [Course](#courses)
    
    3.2 [Requirements](#requirements)
    
    3.3 [Modules](#modules)
    
4. [Walkthrough](#walkthrough)
5. [Command List](#command-list)
    
    5.1.[`help`](#help)
    
    5.2 [`course`](#course)
    
    5.3 [`requirement`](#requirements)
    
    5.4 [`assign`](#assign)
    
    5.5 [`module`](#module)
  
    5.6 [`modify`](#modify)
  
    5.7 [`delete`](#delete)
  
    5.8 [`exam`](#exam)
  
    5.9 [`achieve`](#achieve)
  
    5.10 [`batch`](#batch)
  
    5.11 [`export`](#export)
  
    5.12 [`exit`](#exit)
   
6. [Frequently Asked Questions (FAQ)](#faqs)
7. [Cheat Sheet](#cheat-sheet)
8. [Glossary](#glossary)

---

## Introduction

Sick of having tracking your university progress the manual way? 
Start getting rid of your Excel sheets and scribbled down notes and download *iGrad* right now!

What is *iGrad*? 

*iGrad* is the app to track your university progress, for students who are frustrated with the
limited features the university provides, by students who are frustrated by the limited
features the university provides. 

*iGrad* offers users the ability to create custom courses and graduation requirements,
ensuring the **maximum** degree of flexibility when planning and keeping track of your progress

*iGrad* also retrieves data directly from [NUS Mods](https://nusmods.com), ensuring that module
information is always up to date.

*iGrad* calculates your CAP at every step, ensuring you never have to use a CAP calculator again

Finally, the *iGrad* team is always open to feedback and suggestions from the public will always be followed up on.

## Features  

#### Course Builder
iGrad was built with every NUS student in mind. Our custom course builder allows you to build
the course of your dreams.

#### Modular Credits (MCs) Tracker
We are sick of counting our MCs at the beginning of every semester too. Easily see how many MCs you
have left in order to apply for graduation.

#### Cumulative Average Point (CAP) Tracker
No more googling CAP calculators. iGrad's CAP tracker keeps track of your CAP at every step and
even offers predictive services so you know how well you have to do
in order to achieve your dream Cumulative Point Average (CAP).

## Components

<a name="fig-1">**Figure 1**</a>
![](https://user-images.githubusercontent.com/34233605/75425925-9774ff80-597e-11ea-87f5-228f95b5c84f.png)

#### Courses
A course is simply a group of requirements. It is also how we keep track of your overall CAP
and MCs. 

#### Requirements
A requirement consists of at least one module. Fulfill all modules within a requirement to
complete it.

#### Modules
A module is the building block of all other components. Mark your modules as done and give it
a grade. You can also add optional memos to help you remember why
you took the module.

## Walkthrough

#### 1. Start up the application

Double-click the .jar file to get started right away!

#### 2. Enter your course details

![](https://user-images.githubusercontent.com/34233605/75436477-9dbfa780-598f-11ea-9076-8d4e4e09c8bc.png)

#### 3. Key in your graduation requirements

![](https://user-images.githubusercontent.com/34233605/75436492-a1ebc500-598f-11ea-91f1-94509bce253e.png)

#### 4. Assign your modules

#### 5. Mark a module as done

![](https://user-images.githubusercontent.com/34233605/75436503-a57f4c00-598f-11ea-9902-a912ce16815e.png)

#### 6. Key in a memo

#### 7. Track your MCs 

#### 8. View your CAP

![](https://user-images.githubusercontent.com/34233605/75436570-bdef6680-598f-11ea-887b-16279de675e6.png)

#### 9. Run batch commands

#### 10. Export your data

![](https://user-images.githubusercontent.com/34233605/75436540-b4fe9500-598f-11ea-814d-ed3a0bbf5c6a.png)

## Command List

#### `help`

Displays a help message to the user. Lists all possible commands
and provides a link to the user guide online.

Command Format

    help

Constraints
   
:warning: NIL
   
Expected Outcome

:white_check_mark: A help message should be displayed

---

#### `course`

Creates a course.

Command Format

    course n/COURSE_NAME
    
Command Sample

    /*
    * Creating a course named "Computer Science"
    */
    course n/Computer Science

Constraints
   
:warning: You can only have one course at a time
   
Expected Outcome

:white_check_mark: You should be able to see the course name in the 
top panel

---
    
#### `requirement`

Creates a graduation requirement.

Command Format

    requirement n/REQUIREMENT_NAME u/NO_OF_MCS

Command Sample

    /**
    * Creating a requirement named "Unrestricted Electives" 
    * which requires 32 MCs to fulfill
    */
    requirement n/Unrestricted Electives u/32

Constraints
   
:warning: The number of MCs needed to fulfill the requirement is needed

:warning: Requirement names have to be unique 
   
Expected Outcome

:white_check_mark: You should be able to see the requirement name in the
main panel

---

#### `assign`

Assigns a module to a graduation requirement. If there is good internet
connectivity, the module will be validated with NUS Mods and its description
will be auto-filled.

Command Format
    
    /**
    * DESCRIPTION is optional
    */
    assign n/REQUIREMENT_NAME: n/MODULE_CODE t/MODULE_TITLE u/NO_OF_MCS d/DESCRIPTION

Command Sample

    /**
    * Assigns module "LAJ1201 Japanese 1" worth 4 MCs 
    * to requirement "Unrestricted Electives"
    */
    assign n/Unrestricted Electives: n/LAJ1201 t/Japanese 1 u/4

Constraints
 
:warning: A module cannot be assigned if there are not enough MCs left under 
a graduation requirement

:warning: The module code and title have to be unique
   
Expected Outcome

:white_check_mark: The module will be displayed in the main panel

---

#### `module`

Tags a module with semester, grade or memo notes.

Command Format
    
    /**
    * At least one option must be specified.
    * SEMESTER is specified in format Y_S_ ( e.g. Y1S2 - Year 1 Semester 2 ) 
    */
    module n/MODULE_CODE: s/SEMESTER g/GRADE m/MEMO_NOTES

Command Sample
   
    /**
    * Tags CS1101 with "Y1S2" and grade "A+"
    */
    module n/CS1101: s/Y1S2 g/A+
    
    /**
    * Tags ST2234 with "Y2S1" and gives it a memo "pretty easy module"
    */
    module n/ST2334: s/Y2S1 m/pretty easy module

Constraints
   
:warning: The module has to be assigned
   
Expected Outcome

:white_check_mark: The tags should appear under their respective column headers

---

#### `modify`

Modify course, graduation requirements or modules

Command Format

    modify course n/COURSE_NAME
    
    modify req n/REQUIREMENT_NAME: u/NO_OF_MCS
    
    /**
    * At least one option must be specified
    */
    modify module n/MODULE_CODE: t/MODULE_NAME u/NO_OF_MCS

Command Sample

    /**
    * Modifies CS4239 and gives it the title "Machine Learning" and updates
    * it to be worth 5 MCs
    */
    modify module n/CS4239: t/Machine Learning u/5

Constraints
   
:warning: The component to be modified must exist in the system

:warning: The COURSE_NAME, REQUIREMENT_NAME, MODULE_NAME and MODULE_CODE
have to be unique
   
Expected Outcome

:white_check_mark: You should be able to see the relevant changes

---
    
#### `delete`

Deletes course, graduation requirements or modules

Command Format

    del course n/COURSE_NAME
    
    del req n/REQUIREMENT_NAME
    
    del module n/MODULE_CODE

Command Sample
    
    /**
    * Deletes the course "Computer Science"
    */
    del course n/Computer Science

Constraints
   
:warning: The component to be deleted must exist in the system
   
Expected Outcome

:white_check_mark: The components deleted should disappear from their respective panels

---
 
#### `exam`

View your examination results.

Command Format

    /**
    * SEMESTER is optional.
    * If not specified, displays results for all semesters.
    */
    exam s/SEMESTER

Command Sample
    
    /**
    * Displays exam results for Year 3 Semester 2 
    */
    exam s/Y3S2

Constraints

:warning: NIL
   
Expected Outcome

:white_check_mark: You should be able to view your exam results

---
    
#### `achieve`

Calculates the average grade needed to achieve the CAP you desire/

Command Format

    achieve c/DESIRED_CAP

Command Sample

    /**
    * Calculates the avergae grade needed
    * to achieve a CAP of 4.50
    */
    achieve c/4.50

Constraints
   
:warning: NIL
   
Expected Outcome

:white_check_mark: You should be able to view the average grade needed to achieve
the CAP you desire

---
    
#### `batch`

Executes a series of commands written in a text (.txt) file

Command Format

    batch f/ FILE_PATH

Command Sample

    batch f/ C:\Users\wayne\OneDrive\Desktop\commands.txt

Constraints
   
:warning: The text file has to properly formatted. 
Partial execution will not take place.
   
Expected Outcome

:white_check_mark: All changes should be made based on the commands
in the text file. Please cross-check changes with the file to ensure 
this is so.

---
    
#### `export`

Exports all data in a text file. If information is sufficient, 
this file can be submitted to NUS as a study plan.

Command Format

    export

Constraints
   
:warning: NIL
   
Expected Outcome

:white_check_mark: A text file "study_plan.txt" should be generated in
the same folder as the iGrad application.

---
 
#### `exit`

Exits the program

Command Format

    exit

Constraints
      
:warning: NIL
   
Expected Outcome

:white_check_mark: The application should exit.
 
 ## FAQs
 
*I'm not an NUS student. Can I still use iGrad?*

As long as your university follows a similar [hierachical structure](#fig-1)!
However, we will be unable to provide features such as validation from NUS Mods.
 
## Cheat Sheet
 
> This segment contains all the commands detailed in this guide in a consolidated list
    
`help`

`course n/COURSE_NAME`

`requirement n/REQUIREMENT_NAME u/NO_OF_MCS`

`assign n/REQUIREMENT_NAME: n/MODULE_CODE t/MODULE_TITLE u/NO_OF_MCS d/DESCRIPTION`

`module n/MODULE_CODE: s/SEMESTER g/GRADE m/MEMO_NOTES`

`modify course n/COURSE_NAME`

`modify req n/REQUIREMENT_NAME: u/NO_OF_MCS`

`modify module n/MODULE_CODE: t/MODULE_NAME u/NO_OF_MCS`

`del course n/COURSE_NAME`
    
`del req n/REQUIREMENT_NAME`
    
`del module n/MODULE_CODE`

`exam s/SEMESTER`

`achieve c/DESIRED_CAP`

`batch f/ FILE_PATH`

`export`

`exit`
 
 ## Glossary
 
|               |               |
| ------------- |-------------  |
| Course        |  A course is the entire programme of studies required to complete a university degree |
| Graduation requirement      | Requirements specified by the university in order for a student to graduate |
| Module      | Each module of study has a unique module code consisting of a two- or three-letter prefix that generally denotes the discipline, and four digits, the first of which indicates the level of the module |
| Cumulative Average Point (CAP) |  The Cumulative Average Point (CAP) is the weighted average grade point of the letter grades of all the modules taken by the students. |
| Semester      | A semester is a part of the academic year. Each semester typically lasts 13 weeks in NUS. |
| Modular Credits (MCs)      | A modular credit (MC) is a unit of the effort, stated in terms of time, expected of a typical student in managing his/her workload.       |
| NUS Mods | A timetabling application built for NUS students, by NUS students. Much like this iGrad!     |
|               |               |

**Handy Links**

[NUS - Modular System](http://www.nus.edu.sg/registrar/academic-information-policies/graduate/modular-system)

[NUS - Degree Requirements](http://www.nus.edu.sg/registrar/academic-information-policies/undergraduate-students/degree-requirements)

[NUS - Grading System and Regulations](http://www.nus.edu.sg/nusbulletin/yong-siew-toh-conservatory-of-music/undergraduate-education/degree-requirements/grading-system-and-regulations/)

[NUS - Academic Calendar](http://www.nus.edu.sg/registrar/calendar)