package com.shemesh.solar.solutions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;


@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories
//@EnableAsync
@SpringBootApplication
@Slf4j
@PropertySources({
		@PropertySource("classpath:application.properties"),
		@PropertySource("classpath:application.db.properties"),
		@PropertySource("classpath:auth0.properties"),
		//@PropertySource("log4j2.properties")

})
public class Application {

	public static void main(String[] args) {
		log.info("Starting Solar Solutions app !2");
		SpringApplication.run(Application.class, args);
	}

}
