package com.warmer.kgmaker.entity.test;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

@Data
public class Actor {

    @GeneratedValue
    @Id
    private Long id;

    private String name;
    private int age;
    private String sex;
}
