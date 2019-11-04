# hero-sightings-web-app

This was my final individual project for my Java Apprenticeship at the Software Guild. It is a full stack Spring Boot web
application that allows the user to view, create, edit, and delete sightings of superheroes.

Like my other Java projects, this was built with Maven in NetBeans. It uses Spring Thymeleaf for the front end. Before running
the project, you will need to have a MySQL database set up. I used MySQL Workbench 8.0. If you have that or an equivalent,
run the two sql scripts included in the project to create the main database and the test database. Please note that the two
```application.properties``` files in the project have the password set up as "rootroot." You will need to substitute your
own password to successfully build the project.

Aside from that, I would hope that using this application is self-explanatory! The only thing that might throw you off is 
that in order to add a Hero to the database, you must assign at least one sighting and at least one organization to that Hero.
This was poor design on my part, and if I were to either start this project from scratch or take the time to overhaul it,
I would definitely change how those relationships are mapped in the DAOs. Apart from that, I'm really happy with how
this project turned out!
