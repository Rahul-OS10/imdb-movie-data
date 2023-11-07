# imdb-movie-data
Spring boot project with neo4j 

This project is my Programming Assignment-2 (ADB).

First, I have setup the DataBase model(neo4j). Created the required nodes and the Relationships between them.

There are 3 nodes, namely Movie , Person, Genres.
Person node can have actor or director. And if the person is an actor , there will be ACTED relationship from Person to Movie node.

If the Person id director, there is a DIRECTED relationship from Person to Movie node.
And from Movie node to Generes node there is IN relationship.

-------------------------------------------------------------------------------------------------------

Hello Professor, I have attached the postman collection of this assignment in the " /test " folder, you can export the collection and 
test all the endpoints that I have created . 

REQUIREMENTS FOR PROJECT SETUP
------------------------------
1) JDK 17 with java installed on the pc 
    -> This is the link to install java 17 on pc https://www.oracle.com/java/technologies/downloads/
    -> Download the zip file from my git repo from here https://github.com/Rahul-os/imdb-movie-data, and then unzip the folder.
2) Then open any code editor like intellij and then import the project. 
3) After importing the project go to the main class i.e, ImdbMovieDataApplication , present at C:\Users\malla\OneDrive\Desktop\MS-sem1\MS ADB\imdb-movie-data\src\main\java\com\neo4j\imdbmoviedata
    and run it. This will start the application on Tomcat Server and the port is 8081 .
4) To test the application endpoints use Postman tool .(I have attached the postman collection in \test folder)

My application will have Controller, service , Repository layers , the request will be first mapped to controller ,
and the Controller will process request to service layer , and service layer will then forward to Repository layer and repository
layer will interact with DB and return back the result.

I have also pushed the code to my git-hub repository and i am providing the link for that repo below, you can clone the project from there.

https://github.com/Rahul-os/imdb-movie-data    <-- MY GITHUB LINK TO CLONE THE PROJECT -->

----------------------------------------------------------------------
I have also added Swagger-ui, and it can be accessed at
http://localhost:8081/swagger-ui/index.html#/