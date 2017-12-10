## Introduction
The application is written for Data Centric Rad module in year 3 of Software Development(2017) course at Galway Mayo Institute of Technology, Galway Campus.
This is a Java Server Faces application with MySQL Database and Mongo Database.
### Features of the application:
* Connect to MySQL Database and read/modify/insert records(Intentionally left the printStackTrace() methods for easier debugging)
* Connect to Mongo Database and read/modify/insert documents(Intentionally left the printStackTrace() methods for easier debugging)
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
* Used jQuery for AJAX feature loads like:
    * Load modal with a form whenever a new item to be added to the database
    * Load modal to confirm deletion of an item
    * Load modal with the full details of a city
    * Load modal with a update country form
* Used Data tables jQuery plugin to add extra features to the list:
    * Pagination
    * Sorting
    * Quick Search

### Description
#### How it works
The application is divided into four parts, each part has it's own:
* Controller
    * Each controller extends a BasicController abstract class. This class implements "messaging" between the view and controller. On every view load the view shows the current message from the controller then calls the ```clearMessage()``` method.  BasicController also implements a method to check the database connection lost flags in the DAO, if the connection was lost it will set the error message. 
* Data Access Object
    * Each DAO implements:
        * DAOInterface interface- Structure which defines the base methods for the DAO classes
        * DAOFactory abstract class - Implements MySQL database connection and MongoDB connection and the appropriate error checking and closing of these databases. Also implements DAOInterface, however it does not implements the methods from it.
* Model
    * Stores attributes of one given item. It has setters and getters methods for every each attribute. The view uses this to fill up a new model and pass it to the controller. The DAO uses this to get the items from the database and pass to the controller which passes it to the view.
* One or more view
    * The view accesses the controller which accesses the DAO which connects to one or both of the databases and loads the result into the Model, which is passed back to the view trough the controller. This process can be reversed as well. (MVC).

#### View
The view is built with Bootstrap css and aid of jQuery. jQuery is used to load the add, update and details pages into the view(more precisely into a jQuery modal) through AJAX. If add or update page is loaded into the view, the information is being sent back through JSF ajax to the appropriate controller. jQuery is also used to show a confirmation dialog before deleting an item.
#### Find Cities View
At first load the view shows every single city from the database. Once the "Search" button is pressed it filters the result according to the filter criteria. If a field is left empty, it is going to be ignored. 



#### Dependencies
* mysql-connector-java-5.1.0-bin.jar
* mongodb-driver-core-3.4.2.jar
* mongodb-driver-3.4.2.jar
* javax.faces-2.2.9.jar