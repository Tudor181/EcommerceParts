package com.truckcompany.example.TruckCompany;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.truckcompany.example.TruckCompany.swing.AuthFrame;
import com.truckcompany.example.TruckCompany.swing.MySwing;

@SpringBootApplication // annotation -> let the compiler knows what this class is doing
public class TruckCompanyApplication {

	public static void main(String[] args) {

		// MySwing mySwing = new MySwing();
		// mySwing.initialize();
		System.setProperty("java.awt.headless", "false");
		SpringApplication.run(TruckCompanyApplication.class, args);

		javax.swing.SwingUtilities.invokeLater(() -> {
			AuthFrame mySwing = new AuthFrame();
			mySwing.initialize();

		});

	}
}
