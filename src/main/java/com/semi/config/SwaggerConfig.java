package com.semi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Date 21.05.2018
 *
 * @author Hursanov Sulaymon
 * @version v1.0
 **/
@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket taskApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .select().apis(RequestHandlerSelectors.basePackage("com.semi.controllers"))
                .paths(PathSelectors.any())
                .build();

    }
}
