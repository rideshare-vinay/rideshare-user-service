package com.revature;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.revature.beans.Admin;
import com.revature.beans.Batch;
import com.revature.beans.Car;
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
	 * @return An ApiInfoBuilder which is used to add custom metadata to a swagger page.
	 */
	
    ApiInfo apiInfo() {
        return new ApiInfoBuilder()
            .title("RideShare User Service")
            .description("API Documentation for User Service")
            .version("1.0.0")
            .build();
    }
	
    /**
     * api is needed for swagger to know what api it will be working with.
     * 
     * @return A Docket which selects the api, builds it and also adds custom apiInfo.
     */
    
	@Bean
	public Docket api() {
		return new Docket (DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.revature"))
			.build()
			.apiInfo(apiInfo());
	}
	//@Bean
//	public CommandLineRunner demoData(BatchService batchService,UserService userService, CarService carService,AdminService adminService) 
//		 {
//			return args -> {
//				Batch b=new Batch();
//				b.setBatchLocation("UTA - Arlington, TX");
//				b.setBatchNumber(4);
//				batchService.addBatch(b);
//				
//				Admin admin = new  Admin();
//				admin.setAdminId(1);
//				admin.setUserName("admin");
//				adminService.createAdmin(admin);
//				
//				// user driver
//				User user1 = new User();
//				user1.setUserId(1);
//				user1.setUserName("drivera");
//				user1.setFirstName("Adney");
//				user1.setLastName("Jones");
//				user1.setEmail("revatureD1@gmail.com");
//				user1.setPhoneNumber("7131019999");
//				user1.setAddress("123 Main St");
//				user1.setDriver(true);
//				user1.setActive(true);
//				user1.setAcceptingRides(true);
//				user1.setBatch(b);
//				userService.addUser(user1);
//				
//				User user2 = new User();
//				user2.setUserId(2);
//				user2.setUserName("driverb");
//				user2.setFirstName("Archibald");
//				user2.setLastName("Wilson");
//				user2.setEmail("revatureD2@gmail.com");
//				user2.setPhoneNumber("7132029999");
//				user2.setAddress("456 Alder Dr");
//				user2.setDriver(true);
//				user2.setActive(true);
//				user2.setAcceptingRides(true);
//				user2.setBatch(b);
//				userService.addUser(user2);
//				
//				User user3 = new User();
//				user3.setUserId(3);
//				user3.setUserName("driverc");
//				user3.setFirstName("Balder");
//				user3.setLastName("Miller");
//				user3.setEmail("revatureD3@gmail.com");
//				user3.setPhoneNumber("7133039999");
//				user3.setAddress("9169 Middle Dr");
//				user3.setDriver(true);
//				user3.setActive(true);
//				user3.setAcceptingRides(true);
//				user3.setBatch(b);
//				userService.addUser(user3);
//				
//				User user4 = new User();
//				user4.setUserId(4);
//				user4.setUserName("driverd");
//				user4.setFirstName("Cabal");
//				user4.setLastName("Davis");
//				user4.setEmail("revatureD4@gmail.com");
//				user4.setPhoneNumber("7134049999");
//				user4.setAddress("2012 Kroger St");
//				user4.setDriver(true);
//				user4.setActive(true);
//				user4.setAcceptingRides(true);
//				user4.setBatch(b);
//				userService.addUser(user4);
//				
//				// rider	
//				User user5 = new User();
//				user5.setUserId(5);
//				user5.setUserName("ridera");
//				user5.setFirstName("Evelyn");
//				user5.setLastName("Brown");
//				user5.setEmail("revatureR1@gmail.com");
//				user5.setPhoneNumber("7135029999");
//				user5.setAddress("9087 Jibber Ave");
//				user5.setDriver(false);
//				user5.setActive(true);
//				user5.setAcceptingRides(false);
//				user5.setBatch(b);
//				userService.addUser(user5);
//				
//				User user6 = new User();
//				user6.setUserId(6);
//				user6.setUserName("riderb");
//				user6.setFirstName("Galen");
//				user6.setLastName("William");
//				user6.setEmail("revatureR2@gmail.com");
//				user6.setPhoneNumber("7136069999");
//				user6.setAddress("673 Hilbert St");
//				user6.setDriver(false);
//				user6.setActive(true);
//				user6.setAcceptingRides(false);
//				user6.setBatch(b);
//				userService.addUser(user6);
//				
//				User user7 = new User();
//				user7.setUserId(7);
//				user7.setUserName("riderc");
//				user7.setFirstName("Amarie");
//				user7.setLastName("Johnson");
//				user7.setEmail("revatureR3@gmail.com");
//				user7.setPhoneNumber("7137079999");
//				user7.setAddress("4589 Lober Ln");
//				user7.setDriver(false);
//				user7.setActive(true);
//				user7.setAcceptingRides(false);
//				user7.setBatch(b);
//				userService.addUser(user7);
//				
//				User user8 = new User();
//				user8.setUserId(8);
//				user8.setUserName("ridere");
//				user8.setFirstName("Birdee");
//				user8.setLastName("Smith");
//				user8.setEmail("revatureR4@gmail.com");
//				user8.setPhoneNumber("7138089999");
//				user8.setAddress("102 George Dr");
//				user8.setDriver(false);
//				user8.setActive(true);
//				user8.setAcceptingRides(false);
//				user8.setBatch(b);
//				userService.addUser(user8);
//				
//				Car c1 = new Car();
//				c1.setCarId(1);
//				c1.setColor("blue");
//				c1.setMake("Acura");
//				c1.setModel("ILX");
//				c1.setSeats(4);
//				c1.setUser(user1);
//				c1.setYear(2013);
//				carService.addCar(c1);
//				
//				Car c2 = new Car();
//				c2.setCarId(2);
//				c2.setColor("Silver");
//				c2.setMake("Audi");
//				c2.setModel("A5");
//				c2.setSeats(4);
//				c2.setUser(user2);
//				c2.setYear(2018);
//				carService.addCar(c2);
//				
//				Car c3 = new Car();
//				c3.setCarId(3);
//				c3.setColor("White");
//				c3.setMake("BMW");
//				c3.setModel("X3");
//				c3.setSeats(4);
//				c3.setUser(user3);
//				c3.setYear(2018);
//				carService.addCar(c3);
//				
//				Car c4 = new Car();
//				c4.setCarId(4);
//				c4.setColor("Red");
//				c4.setMake("Chevrolet");
//				c4.setModel("Blazer");
//				c4.setSeats(4);
//				c4.setUser(user4);
//				c4.setYear(2018);
//				carService.addCar(c4);
//				
//			};
//	}

}
