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
2) Postman tool to test the API's
3) Code editot like intellij(i used intellij)

I have also pushed the code to my git-hub repository and i am providing the link for that repo below, you can clone the project from there.

https://github.com/Rahul-os/imdb-movie-data    <-- MY GITHUB LINK TO CLONE THE PROJECT -->

----------------------------------------------------------------------
I have also added Swagger-ui, and it can be accessed at
http://localhost:8081/swagger-ui/index.html#/