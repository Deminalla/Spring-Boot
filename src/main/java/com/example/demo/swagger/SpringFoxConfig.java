package com.example.demo.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build()
                .apiInfo(apiInfo()) // this one is not a must
                .useDefaultResponseMessages(false); // this disables always showing http responses by default (like 401, 403, 404)
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("[enter application name in the future] microservice API")
                .description("small part of a larger upcoming system, name is not ready :DD")
                .version("1.0")
                .build();
    }

}
