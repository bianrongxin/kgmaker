package com.warmer.kgmaker.util;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.transaction.Neo4jTransactionManager;

@Configuration
@EnableNeo4jRepositories(basePackages = "com.warmer.kgmaker.repository")
public class Neo4jConfig {
	@Value("${spring.neo4j.url}")
	private String url;

	@Value("${spring.neo4j.username}")
	private String username;

	@Value("${spring.neo4j.password}")
	private String password;

	/**
	 * 图数据库驱动模式
	 *
	 * @return
	 */

	@Bean
	public Driver neo4jDriver() {
		return GraphDatabase.driver(url, AuthTokens.basic(username, password));
	}

	@Bean
	public SessionFactory sessionFactory() {
		org.neo4j.ogm.config.Configuration configuration = new org.neo4j.ogm.config.Configuration.Builder()
				.uri(url)
				.credentials(username, password)
				.build();
		return new SessionFactory(configuration, "com.warmer.kgmaker.entity");
	}

	@Bean
	public Neo4jTransactionManager transactionManager() {
		return new Neo4jTransactionManager(sessionFactory());
	}

}