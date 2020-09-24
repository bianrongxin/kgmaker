package com.warmer.kgmaker.repository;

import com.warmer.kgmaker.entity.test.Person;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonReponsitory extends Neo4jRepository<Person, Long> {
    // 此处用法可见 https://docs.spring.io/spring-data/neo4j/docs/5.1.2.RELEASE/reference/html/#_query_methods
    Person findByName(@Param("name") String name);
}

