package com.shemesh.solar.solutions.config;//package com.shemesh.solar.solutions.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.builders.PathSelectors;
//import springfox.documentation.builders.RequestHandlerSelectors;
//import springfox.documentation.service.ApiInfo;
//import springfox.documentation.service.Contact;
//import springfox.documentation.spi.DocumentationType;
//import springfox.documentation.spring.web.plugins.Docket;
//import springfox.documentation.swagger2.annotations.EnableSwagger2;
//
///**
// * Swagger documentation endpoint configuration.
// *
// * @author Lior Gur
// */
//@Configuration
//@EnableSwagger2
//@Component
//@Primary
//public class SwaggerConfig {
//
//    @Bean
//    public Docket swaggerApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.shemesh"))
//                .paths(PathSelectors.any())
//                .build()
//                .enable(true)
//                .apiInfo(apiInfo())
//                ;
//    }
//
//    private ApiInfo apiInfo() {
//        Contact contactInfo = new Contact("Lior Gur", "https://github.com/liorgur", "liorgur2@gmail.com");
//        return new ApiInfo(
//                "Solar Solutions",
//                "",
//                "1.0.0",
//                "",
//                contactInfo,
//                "MIT",
//                "");
//    }
//
//}
