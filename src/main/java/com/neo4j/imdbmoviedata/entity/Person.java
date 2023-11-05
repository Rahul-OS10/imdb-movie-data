package com.neo4j.imdbmoviedata.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Node
public class Person {
    @Id
    private Long id;
    @Relationship(type = "ACTED_IN" , direction = Relationship.Direction.OUTGOING)
    public List<Movie> actors;

    @Relationship(type = "DIRECTED" , direction = Relationship.Direction.OUTGOING)
    public List<Movie> director;
}
