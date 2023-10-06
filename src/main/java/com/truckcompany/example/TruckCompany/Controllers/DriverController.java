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
                Driver createdDriver = driverService.createDriver(payload.driverName,
                                payload.driverAge,
                                payload.imdbId);
                return new ResponseEntity<Driver>(createdDriver,
                                HttpStatus.CREATED);
        }
}
