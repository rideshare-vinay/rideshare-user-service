package com.revature;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Driver {
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Driver.class, args);
	}
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("RideShare User Service")
            .description("API Documentation for User Service")
            .version("1.0.0")
            .build();
    }
	
	@Bean
	public Docket api() {
		return new Docket (DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.revature"))
			.build()
			.apiInfo(apiInfo());
	}

}
