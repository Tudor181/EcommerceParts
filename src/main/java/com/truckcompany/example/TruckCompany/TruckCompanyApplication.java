package com.truckcompany.example.TruckCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication // annotation -> let the compiler knows what this class is doing
public class TruckCompanyApplication {

	public static void main(String[] args) {
		SpringApplication.run(TruckCompanyApplication.class, args);
	}

	// @GetMapping("/root")
	// public String apiRoot() {
	// return "Hello World";
	// }

}
