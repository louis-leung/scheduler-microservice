package com.louisleung.springboot.schedulermicroservice.config;

import com.louisleung.springboot.schedulermicroservice.controllers.HomeController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@ComponentScan(basePackageClasses = HomeController.class)
@Configuration
public class SwaggerConfig {
    private static final String SWAGGER_API_VERSION="1.0";
    private static final String title="Scheduler RESTful API";

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title(title)
                .version(SWAGGER_API_VERSION)
                .build();
    }

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .pathMapping("/")
                .select()
                .paths(PathSelectors.regex("/api.*"))
                .build();
    }
}
