package com.shemesh;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.CorsRegistry;


@Configuration
@EnableTransactionManagement
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
public class MainApplication {

    public static void main(String[] args) {
        log.info("Starting Xmed app !2");
        SpringApplication.run(MainApplication.class, args);
    }

}