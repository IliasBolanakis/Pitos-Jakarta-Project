# Pitos-Web-Project
A fictional pie store web project build with JakartaEE

## About The Project
Pitos simulates a fully functioanl website of a pie store that supports the following functios:
* A custom authentication/authorization mechanism for login & registration
* Placing orders that are stored in the database
* Informing the client through emails

The goal was to create an introductory project to Back-End development with JakartaEE with the usage of Tomcat Server and
traditional technologies like jsp files, WebServlets, and JDBC to connect to the database

## Database structure
The database used is a simple relational MySQL database with the below structure:

![Στιγμιότυπο οθόνης (33)](https://user-images.githubusercontent.com/104007209/212188642-f94287f6-6af9-4845-a1d2-0a7b68ee5049.png)

## Noteworthy Facts
* A custom email validator was implemented that tries to perform actual email validation through DNS lookups
* Not much emphasis was given on logging (just a simple Logger for JUL with a ConsoleHandler)
* Some security measures where taken such as Hashing and salting for the user's password in the database and the implementation of a CSRF filter
* The project never got deployed
* The Front-End was not a priority for this project (some minor css and js for aesthetic purposes only)
* The entire page is in greek

## Authors
* [Ilias Bolanakis] (https://github.com/IliasBolanakis)
