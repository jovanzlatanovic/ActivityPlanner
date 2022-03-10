# ActivityPlanner
An application designed to keep track of your weekly tasks.
Created to provide myself with experience and practice in Java object-oriented programming, design patterns, and JavaFX.

## What's used in this project?
- Java 17
- Open JavaFX 17.0.1
- Google GSON 2.9.0
- Apache Maven

All dependencies are included in the Maven pom.xml file.

## How to run it
The project was created using IntelliJ IDEA (CE) 2021.3.2 so the easiest way would be to clone the project and simply open it with IntelliJ. Otherwise any other IDE will do. The only necessity is to download all Maven dependencies.

Project is ran from `src/main/java/com.jovan.activityplanner/Main.java`

## Project structure
- src/main
  - java
    - controller - Contains controllers used by FXML files
    - model - Contains data models and certain design patterns which implement the activity data (an activity meaning an activity from a certain date to a certain date)
      - command - Contains classes which implement the Command design pattern and allows the usage of undo and redo
      - filemanager - Contains classes which implement the Bridge design pattern allowing a developer to separate file loading implementations from concrete object loaders
      - listener - Contains interfaces which implement listeners
    - util - Utilities which assist in the execution of the program
    - view - Classes which extend base JavaFX objects
  - resources - Contains images and font files, as well as FXML files
