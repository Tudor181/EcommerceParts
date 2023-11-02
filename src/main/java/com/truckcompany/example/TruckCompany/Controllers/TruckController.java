package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truckcompany.example.TruckCompany.DataAbstraction.MyException;
import com.truckcompany.example.TruckCompany.Domain.Truck;
import com.truckcompany.example.TruckCompany.Requests.NewTruckRequest;
import com.truckcompany.example.TruckCompany.Services.TruckService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;


@RestController
@RequestMapping("/api/v1.0/trucks")
@Tag(name = "Truck", description = "Find all the crud operations related to truck-controller here")
public class TruckController {

    private final static int MIN_MANUFACTURERYEAR = 1900;

    @Autowired
    private TruckService truckService;

    @Operation(summary = "Get all trucks")
    @ApiResponse(responseCode = "200", description = "Found the trucks", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Truck.class)) })
    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        try {
            List<Truck> trucks = truckService.allTrucks();
            if (trucks == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(trucks, HttpStatus.OK);
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Get truck by id")
    @GetMapping("/{id}")
    public ResponseEntity<Truck> getTruckById(@PathVariable String id) {
        try {
            if (id == null) {
                throw new MyException("Id is null");
            }
            Optional<Truck> truck = truckService.truckById(id);
            if (truck.isPresent()) {
                return new ResponseEntity<>(truck.get(), HttpStatus.OK);
            } else {
                throw new MyException("Truck not found");
            }
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Deprecated
    @Operation(summary = "Get truck by imdbId")
    @GetMapping("/imdb/{imdbId}")
    public ResponseEntity<Optional<Truck>> getTruckByImdbId(@PathVariable String imdbId) {
        try {
            if (imdbId == null) {
                throw new MyException("ImdbId is null");
            }
            Optional<Truck> truck = truckService.truckByImdbId(imdbId);
            if (truck == null) {
                throw new MyException("Truck not found");
            }
            return new ResponseEntity<>(truck, HttpStatus.OK);
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Create a new truck")
    @ApiResponse(responseCode = "201", description = "Truck created", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Truck.class)) })
    @PostMapping("/new/")
    public ResponseEntity<Truck> createTruck(@RequestBody NewTruckRequest payload) {
        try {
            if (payload == null || payload.manufacturer == null || payload.nrOfRegistration == null) {
                throw new MyException("Payload is null");
            }
            final String nrOfReg = payload.nrOfRegistration.get();
            if (payload.manufactureYear < MIN_MANUFACTURERYEAR || payload.manufacturer.length() < 2 || !(nrOfReg.length() == 7 || nrOfReg.length() == 0)) {
                throw new MyException("Invalid payload data");
            }
            Truck createdTruck = truckService.createTruck(payload.imageId, payload.manufacturer, payload.nrOfRegistration, payload.manufactureYear);
            if (createdTruck == null) {
                throw new MyException("Truck not created");
            }
            return new ResponseEntity<>(createdTruck, HttpStatus.CREATED);
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(summary = "Delete a truck by id")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTruck(@PathVariable String id) {
        try {
            if (id == null) {
                throw new MyException("Id is null");
            }
            final boolean response = truckService.deleteTruckById(id);
            if (response) {
                return new ResponseEntity<>("Truck Deleted", HttpStatus.OK);
            } else {
                throw new MyException("Truck not found");
            }
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
/**
 * TruckController is a REST controller that handles HTTP requests related to Truck entities.
 * It uses the TruckService to perform operations on the Truck entities.
 * 
 * The class has the following methods:
 * 
 * - getAllTrucks(): Retrieves all Trucks. Returns 200 along with the list of Trucks.
 * 
 * - getTruckById(String id): Retrieves a Truck by its id. Returns 404 if the Truck is not found, 400 if the id is not a valid ObjectId, and 200 along with the Truck if it is found.
 * 
 * - getTruckByImdbId(String imdbId): Retrieves a Truck by its IMDB id. Returns 200 along with the Truck if it is found. This method is marked as deprecated.
 * 
 * - createTruck(NewTruckRequest payload): Creates a new Truck. The request body should contain the details of the Truck to be created. Returns 201 if the creation is successful, and 400 if the request is not valid.
 * 
 * - deleteTruck(String id): Deletes a Truck by its id. Returns 200 if the deletion is successful, and 404 if the Truck is not found.
 */