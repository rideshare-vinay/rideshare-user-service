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
//	public CommandLineRunner demoData(BatchService batchService,UserService userService, CarService carService,AdminService adminService) 
//		 {
//			return args -> {
//				/*
//				 * {batchNumber: 1, batchLocation: 'VWU - Morgantown, WV'},
//				 * {batchNumber: 2, batchLocation: 'UTA - Arlington, TX'},
//				 * {batchNumber: 3, batchLocation: 'USF - Tampa, FL'},
//				 * {batchNumber: 4, batchLocation: 'Revature HQ - Reston, VA'},
//				 * {batchNumber: 5, batchLocation: 'CUNY SPS - New York, NY'},
//				 * {batchNumber: 6, batchLocation: 'CUNY Queens College - Flushing, NY'}
//				 * */
//				Batch b=new Batch();
//				System.out.println("UTA - Arlington, TX");
//				b.setBatchLocation("UTA - Arlington, TX");
//				b.setBatchNumber(4);
//				batchService.addBatch(b);
//				
//				System.out.println("VWU - Morgantown, WV");
//				b.setBatchLocation("VWU - Morgantown, WV");
//				b.setBatchNumber(1);
//				batchService.addBatch(b);
//				
//				System.out.println("USF - Tampa, FL");
//				b.setBatchLocation("USF - Tampa, FL");
//				b.setBatchNumber(3);
//				batchService.addBatch(b);
//				
//				System.out.println("Revature HQ - Reston, VA");
//				b.setBatchLocation("Revature HQ - Reston, VA");
//				b.setBatchNumber(5);
//				batchService.addBatch(b);
//				
//				System.out.println("CUNY SPS - New York, NY");
//				b.setBatchLocation("CUNY SPS - New York, NY");
//				b.setBatchNumber(6);
//				batchService.addBatch(b);
//				
//				System.out.println("CUNY Queens College - Flushing, NY");
//				b.setBatchLocation("CUNY Queens College - Flushing, NY");
//				b.setBatchNumber(2);
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
//				user1.setUserName("Adney");
//				user1.setFirstName("Adney");
//				user1.setLastName("Jones");
//				user1.setEmail("revatureD1@gmail.com");
//				user1.setPhoneNumber("7131019999");
//				user1.setDriver(true);
//				user1.setActive(true);
//				user1.setAcceptingRides(true);
//				user1.setBatch(b);
//				user1.setAddress("2201 E Road to Six Flags St, Arlington, TX 76010");
//				user1.setLatitude(32.755562);
//				user1.setLongitude(-97.072769);
//				userService.addUser(user1);
//				
//				User user2 = new User();
//				user2.setUserId(2);
//				user2.setUserName("Archibald");
//				user2.setFirstName("Archibald");
//				user2.setLastName("Wilson");
//				user2.setEmail("revatureD2@gmail.com");
//				user2.setPhoneNumber("7132029999");
//				user2.setDriver(true);
//				user2.setActive(true);
//				user2.setAcceptingRides(true);
//				user2.setBatch(b);
//				user2.setAddress("2002 E Abram St, Arlington, TX 76010");
//				user2.setLatitude(32.734660);
//				user2.setLongitude(-97.076860);
//				userService.addUser(user2);
//				
//				User user3 = new User();
//				user3.setUserId(3);
//				user3.setUserName("Balder");
//				user3.setFirstName("Balder");
//				user3.setLastName("Miller");
//				user3.setEmail("revatureD3@gmail.com");
//				user3.setPhoneNumber("7133039999");
//				user3.setDriver(true);
//				user3.setActive(true);
//				user3.setAcceptingRides(true);
//				user3.setBatch(b);
//				user3.setAddress("1322 N Collins St, Arlington, TX 76011");
//				user3.setLatitude(32.755000);
//				user3.setLongitude(-97.097710);
//				userService.addUser(user3);
//				
//				User user4 = new User();
//				user4.setUserId(4);
//				user4.setUserName("Cabal");
//				user4.setFirstName("Cabal");
//				user4.setLastName("Davis");
//				user4.setEmail("revatureD4@gmail.com");
//				user4.setPhoneNumber("7134049999");
//				user4.setDriver(true);
//				user4.setActive(true);
//				user4.setAcceptingRides(true);
//				user4.setBatch(b);
//				user4.setAddress("1390 S Cooper St #150, Arlington, TX 76013");
//				user4.setLatitude(32.735859);
//				user4.setLongitude(-97.114838);
//				userService.addUser(user4);
//				
//				// rider	
//				User user5 = new User();
//				user5.setUserId(5);
//				user5.setUserName("Evelyn");
//				user5.setFirstName("Evelyn");
//				user5.setLastName("Brown");
//				user5.setEmail("revatureR1@gmail.com");
//				user5.setPhoneNumber("7135059999");
//				user5.setDriver(false);
//				user5.setActive(true);
//				user5.setAcceptingRides(false);
//				user5.setBatch(b);
//				user5.setAddress("230 N Center St, Arlington, TX 76011");
//				user5.setLatitude(32.738720);
//				user5.setLongitude(-97.107380);
//				userService.addUser(user5);
//				
//				User user6 = new User();
//				user6.setUserId(6);
//				user6.setUserName("Galen");
//				user6.setFirstName("Galen");
//				user6.setLastName("William");
//				user6.setEmail("revatureR2@gmail.com");
//				user6.setPhoneNumber("7136069999");
//				user6.setDriver(false);
//				user6.setActive(true);
//				user6.setAcceptingRides(false);
//				user6.setBatch(b);
//				user6.setAddress("2121 Margaret Dr, Arlington, TX 76012");
//				user6.setLatitude(32.769270);
//				user6.setLongitude(-97.118290);
//				userService.addUser(user6);
//				
//				User user7 = new User();
//				user7.setUserId(7);
//				user7.setUserName("Amarie");
//				user7.setFirstName("Amarie");
//				user7.setLastName("Johnson");
//				user7.setEmail("revatureR3@gmail.com");
//				user7.setPhoneNumber("7137079999");
//				user7.setDriver(false);
//				user7.setActive(true);
//				user7.setAcceptingRides(false);
//				user7.setBatch(b);
//				user7.setAddress("2800 W Arkansas Ln, Arlington, TX 76016");
//				user7.setLatitude(32.705818);
//				user7.setLongitude(-97.153198);
//				userService.addUser(user7);
//				
//				User user8 = new User();
//				user8.setUserId(8);
//				user8.setUserName("Birdee");
//				user8.setFirstName("Birdee");
//				user8.setLastName("Smith");
//				user8.setEmail("revatureR4@gmail.com");
//				user8.setPhoneNumber("7138089999");
//				user8.setDriver(false);
//				user8.setActive(true);
//				user8.setAcceptingRides(false);
//				user8.setBatch(b);
//				user8.setAddress("1851 E Mayfield Rd, Arlington, TX 76014");
//				user8.setLatitude(32.692080);
//				user8.setLongitude(-97.081140);
//				userService.addUser(user8);
//				
//				// Cars
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
//				c2.setModel("A5 Cabriolet quattro");
//				c2.setSeats(4);
//				c2.setUser(user2);
//				c2.setYear(2018);
//				carService.addCar(c2);
//				
//				Car c3 = new Car();
//				c3.setCarId(3);
//				c3.setColor("White");
//				c3.setMake("BMW");
//				c3.setModel("X6 M");
//				c3.setSeats(4);
//				c3.setUser(user3);
//				c3.setYear(2018);
//				carService.addCar(c3);
//				
//				Car c4 = new Car();
//				c4.setCarId(4);
//				c4.setColor("Red");
//				c4.setMake("Chevrolet");
//				c4.setModel("Impala");
//				c4.setSeats(4);
//				c4.setUser(user4);
//				c4.setYear(2018);
//				carService.addCar(c4);
//				
//			};
//	}
	
}
