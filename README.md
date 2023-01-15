# Pitos-Web-Project
A fictional pie store web project build on JakartaEE

## About The Project
Pitos simulates a fully functional website of a pie store that supports the following functions:

* A custom authentication/authorization mechanism for login & registration
* Placing orders that are stored in the database
* Informing the client through emails

The goal was to create an introductory MVC web application on JakartaEE that makes use of 
traditional technologies like jsp files, WebServlets and JDBC to connect to the database

## Web Page navigation & Instructions
In the Bar on the common  header are available all the functionalities :

* Login/Logout: Login and Logout forms (switches based on current user status, forgot password & password reset options available)
* Εγγραφή: Registration form (Email validation required to complete registration)
* Κατάστημα: Store Description page
* Πίτες: All the avaiable pies & a short overview of each one
* Παρεγγελία: Order form (uppon form submition email is sent to both admin & user)
* Επικοινωνία : Contact Form (uppon form submition email is sent to both admin & user)

Note: Another page is included not visible from the common header bar /admin. Only admins have access (Currently only two admin users), includes 2 commands

![Στιγμιότυπο οθόνης (34)](https://user-images.githubusercontent.com/104007209/212487350-9f6588de-d576-4c80-8b76-f781ac614184.png)

## Database structure

The database used is a simple relational MySQL database with the below structure:

![Στιγμιότυπο οθόνης (33)](https://user-images.githubusercontent.com/104007209/212188642-f94287f6-6af9-4845-a1d2-0a7b68ee5049.png)

## Code Specifications

* JakartaEE 9
* Tomcat 10.1.0-M17
* OpenJDK 17.0.2
* MySQL 8.0.30

## Noteworthy Facts

* A custom email validator was implemented that tries to perform actual email validation through DNS lookups (Not applicable in real world applications, for educational purposes only)
* Not much emphasis was given on logging (just a simple Logger from JUL with a ConsoleHandler, To be improved) 
* Some security measures where taken such as Hashing and salting for the user's password in the database and the implementation of a CSRF filter
* The project never got deployed
* The Front-End was not a priority for this project (some minor css and js for aesthetic purposes only)
* The entire page is in greek

## Authors

* Ilias Bolanakis (https://github.com/IliasBolanakis)

12-1-2023
