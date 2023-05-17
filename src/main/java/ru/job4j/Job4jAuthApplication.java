package ru.job4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import liquibase.integration.spring.SpringLiquibase;
import javax.sql.DataSource;

@SpringBootApplication
public class Job4jAuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(Job4jAuthApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SpringLiquibase liquibase(DataSource ds) {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setChangeLog("classpath:liquibase-changeLog.xml");
		liquibase.setDataSource(ds);
		return liquibase;
	}
}
