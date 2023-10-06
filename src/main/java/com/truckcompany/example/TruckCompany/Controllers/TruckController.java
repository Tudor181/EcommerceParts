package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truckcompany.example.TruckCompany.Truck;
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

    @Autowired
    private TruckService truckService;

    @Operation(summary = "Get all trucks")
    @ApiResponse(responseCode = "200", description = "Found the trucks", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = Truck.class)) })
    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        // return new ResponseEntity<String>("All trucks", HttpStatus.OK);
        return new ResponseEntity<List<Truck>>(truckService.allTrucks(), HttpStatus.OK);
    }

    // unsafe because of the expose of the obj id
    @Operation(summary = "Get truck by id")
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Truck>> getTruckById(@PathVariable String id) {
        try {
            Optional<Truck> truck = truckService.truckById(id);
            if (truck != null) {
                return new ResponseEntity<Optional<Truck>>(truck, HttpStatus.OK);
            } else
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    // better to not expose the ObjId
    @Operation(summary = "Get truck by imdbId")
    @GetMapping("/imdb/{imdbId}")
    public ResponseEntity<Optional<Truck>> getTruckByImdbId(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Truck>>(truckService.truckByImdbId(imdbId),
                HttpStatus.OK);
    }
}
