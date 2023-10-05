package com.truckcompany.example.TruckCompany.Controllers;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truckcompany.example.TruckCompany.Driver;
import com.truckcompany.example.TruckCompany.Services.DriverService;

@RestController
@RequestMapping("/api/v1.0/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @PostMapping
    public ResponseEntity<Driver> createNewDriver(@RequestBody Map<String, String> payload) {
        Driver createdDriver = driverService.createDriver(payload.get("driverName"),
                Integer.parseInt(payload.get("driverAge")),
                payload.get("imdbId"));
        return new ResponseEntity<Driver>(createdDriver,
                HttpStatus.CREATED);
    }
}
