package com.project.college_portal.configure;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
//@EnableSwagger2
public class SwaggerConfig {
	 @Bean
	 
	    public Docket api() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                .apis(RequestHandlerSelectors.basePackage("com.project.college_portal.controller"))
	                .paths(PathSelectors.any())
	                .build()
	                .apiInfo(apiInfo())
	                .securitySchemes(Arrays.asList(apiKey()));
	    }
	    
	    private ApiInfo apiInfo() {
	    	
	    	
	    			
	        return new ApiInfoBuilder()
	                .title("My API Documentation")
	                .description("Welcome to College Portal")
	                .version("1.0.0")
	                .build();
	    }
	    
	    private ApiKey apiKey() {
	        return new ApiKey("apiKey", "apiKey", "header");
	    }
}