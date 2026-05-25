# Student
Deolinda Mavungo - ST10518726

DISD0601 - PROG5112

POE - Part1 & Part2

Youtube Link: 

# Overview

QuickChat is an command-line messaging application developed in Java, designed to simulate the functionalities of a modern messaging platform. The system features user registration and login validation processes to ensure proper access. Users can send and manage multiple messages through interactive looping structures, while the application automatically generates unique message identifiers to maintain message traceability. To enhance data integrity and security, QuickChat incorporates cryptographic-style word hashing techniques. All messaging data is stored in JSON format using real-time file append operations, enabling persistent and organized record keeping.

# Features

- User Authentication:

  - Validates usernames - must contain an underscore and be $\le 5 characters.

  - Validates passwords - must be $\ge 8 characters, containing an uppercase letter, a number, and a special character.

   - Validates cell phone numbers - must start with the international code "+" followed by up to 11 digits).

- Interactive QuickChat Menu

  - Only accessible upon successful login.

  - Presents a loop-driven console menu

- Message Builder & Processing

  - Accepts a user-defined quantity of messages to process.

  - Validates recipient numbers using strict international formatting.

  - Validates that message lengths do not exceed 250 characters.

  - Generates a custom 10-digit unique Message ID.

  - Generates a Message Hash using the first two digits of the Message ID, the running index of the message, and a concatenated uppercase string of the first and last alphanumeric words of your message (e.g., 00:0:HITONIGHT).

# Reference

JUnit, 2023. JUnit 5 User Guide. [online] Available at: https://docs.junit.org/5.14.4/overview.html [Accessed 25 May 2026].

Oracle, 2024. FileWriter (Java SE 17 & JDK 17). [online] Available at: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/io/FileWriter.html [Accessed 25 May 2026].

Oracle, 2024. Pattern (Java SE 17 & JDK 17). [online] Available at: https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/regex/Pattern.html [Accessed 25 May 2026].

The Independent Institute of Education (IIE), 2026. Introduction to Programming [PROG5112 Module Outline]. The Independent Institute of Education. Unpublished.

The Independent Institute of Education, 2021a. How to use Maven for automated testing in GitHub. [video online] Available at: https://www.youtube.com/watch?v=oz0Qd5H4Onk [Accessed 25 May 2026].



