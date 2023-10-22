package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truckcompany.example.TruckCompany.Driver;
import com.truckcompany.example.TruckCompany.Requests.ChangeDriverNameRequest;
import com.truckcompany.example.TruckCompany.Requests.NewDriverRequest;
import com.truckcompany.example.TruckCompany.Services.DriverService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("/api/v1.0/drivers")
public class DriverController {

        @Autowired
        private DriverService driverService;

        @ApiResponse(responseCode = "200", description = "Drivers found", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)) })
        @GetMapping("/GetAllDrivers")
        public ResponseEntity<List<Driver>> getAllDrivers() {
                return new ResponseEntity<List<Driver>>(driverService.allDrivers(), HttpStatus.OK);
        }

        @Operation(summary = "Get driver by id")
        @ApiResponse(responseCode = "200", description = "Driver found", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)) })
        @GetMapping("/{id}")
        public ResponseEntity<Driver> getDriverById(@PathVariable String id) {
                try {
                        Optional<Driver> driver = driverService.driverById(id);
                        if (driver != null) {

                                return new ResponseEntity<>(driver.get(),
                                                HttpStatus.OK);
                        } else
                                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } catch (RuntimeException e) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

        }

        @Deprecated
        @Operation(summary = "Create a new driver, use /new instead")
        @ApiResponse(responseCode = "200", description = "Driver created", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)) })
        @io.swagger.v3.oas.annotations.parameters.RequestBody(content = {
                        @Content(schema = @Schema(implementation = NewDriverRequest.class)) })
        @PostMapping
        public ResponseEntity<Driver> createNewDriver(@RequestBody Map<String, String> payload) {
                Driver createdDriver = driverService.createDriver(payload.get("driverName"),
                                Integer.parseInt(payload.get("driverAge")),
                                payload.get("imdbId"));
                return new ResponseEntity<Driver>(createdDriver,
                                HttpStatus.CREATED);
        }

        //
        @Operation(summary = "Create a new driver and assign it to a truck")
        @ApiResponse(responseCode = "201", description = "Driver created", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)) })
        @PostMapping("/new/")
        public ResponseEntity<Driver> createNewDriver2(@RequestBody NewDriverRequest payload) {
                if (payload.driverAge < 18)
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                Driver createdDriver = driverService.createDriver(payload.driverName,
                                payload.driverAge,
                                payload.truckId);
                return new ResponseEntity<Driver>(createdDriver,
                                HttpStatus.CREATED);
        }

        @Operation(summary = "Change driver name")
        @ApiResponse(responseCode = "200", description = "Driver updated", content = {
                        @Content(mediaType = "application/json", schema = @Schema(implementation = Driver.class)) })
        @PutMapping("/changeName/")
        public ResponseEntity<Driver> changeDriverName(@RequestBody ChangeDriverNameRequest payload) {
                try {
                        return new ResponseEntity<Driver>(
                                        driverService.changeDriverName(payload.driverId, payload.newName),
                                        HttpStatus.OK);
                } catch (RuntimeException e) {
                        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
                }

        }

}
