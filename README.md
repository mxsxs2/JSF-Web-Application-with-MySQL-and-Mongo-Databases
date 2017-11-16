## Introduction
The application is written for Data Centric Rad module in year 3 of Software Development(2017) course at Galway Mayo Institute of Technology, Galway Campus.
This is a Java Server Faces application with MySQL Database and Mongo Database.
### Features of the application:
* Connect to MySQL Database and read/modify/insert records
* Connect to Mongo Database and read/modify/insert documents
* Show a list of
    * Countries
    * Regions
    * Cities
    * Head of State
* Add new
    * Countries
    * Regions
    * Cities
    * Head of State
* Delete
    * Countries
    * Head of State
* Search cities by criteria

### Extra features added to the required ones:
* Used Bootstrap for styling
* Used jQuery for AJAX feutre loads like:
    * Load modal with a form whenever a new item to be added to the database
    * Load modal to confirm deletion of an item
    * Load modal with the full detils of a city
    * Load modal with a update country form
* Used Datatables jQuery plugin to add extra features to the list:
    * Pagination
    * Sorting
    * Quick Search

### Description


#### Dependecies
* mysql-connector-java-5.1.0-bin.jar
* mongodb-driver-core-3.4.2.jar
* mongodb-driver-3.4.2.jar
* javax.faces-2.2.9.jar