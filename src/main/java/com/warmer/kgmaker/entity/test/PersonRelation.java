package com.warmer.kgmaker.entity.test;

import lombok.Data;
import org.neo4j.ogm.annotation.*;

@RelationshipEntity(type = "KNOWN")
@Data
public class PersonRelation {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @StartNode
    private Person startNode;

    @EndNode
    private Person endNode;

}
