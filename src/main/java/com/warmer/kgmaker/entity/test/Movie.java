package com.warmer.kgmaker.entity.test;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;

import java.util.Date;

@Data
public class Movie {

    @GeneratedValue
    @Id
    private Long id;

    private String title;

    private Date shotDate;
}
