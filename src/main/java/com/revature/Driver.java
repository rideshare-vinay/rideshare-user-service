package com.revature;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.beans.Admin;
import com.revature.beans.Batch;
import com.revature.beans.User;
import com.revature.services.AdminService;
import com.revature.services.BatchService;
import com.revature.services.CarService;
import com.revature.services.UserService;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Driver class is the main class for this project.
 * 
 * @author Adonis Cabreja
 *
 */

@SpringBootApplication
@EnableSwagger2
public class Driver {

	/**
	 * The main method of the Driver class.
	 * 
	 * @param args represents any CLI arguments.
	 * @throws Exception
	 */
	
	public static void main(String[] args) {
		SpringApplication.run(Driver.class, args);
	}

	/**
	 * apiInfo consists of metadata for the swagger page.
	 * 
	 * @return An ApiInfoBuilder which is used to add custom metadata to a swagger
	 *         page.
	 */

	ApiInfo apiInfo() {
		return new ApiInfoBuilder().title("RideShare User Service").description("API Documentation for User Service")
				.version("1.0.0").build();
	}

	/**
	 * api is needed for swagger to know what api it will be working with.
	 * 
	 * @return A Docket which selects the api, builds it and also adds custom
	 *         apiInfo.
	 */

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.revature")).build().apiInfo(apiInfo());
	}

//	@Bean
//	public CommandLineRunner demoData(BatchService batchService, UserService userService, AdminService adminService, CarService carService) {
//		return args -> {
//			Batch b = new Batch();
//			b.setBatchLocation("UTA - Arlington, TX");
//			b.setBatchNumber(4);
//			batchService.addBatch(b);
//			
//			User u = new User();
//			u.setUserName("admin");
//			u.setFirstName("john");
//			u.setLastName("smith");
//			u.setPhoneNumber("1234567890");
//			u.setActive(true);
//			u.setBatch(b);
//			userService.addUser(u);
//			
//			Admin a = new Admin();
//			a.setAdminId(1);
//			a.setUserName("admin");
//			adminService.createAdmin(a);
//		};
//	}

}
