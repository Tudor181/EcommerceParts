package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.truckcompany.example.TruckCompany.Truck;
import com.truckcompany.example.TruckCompany.Services.TruckService;

@RestController
@RequestMapping("/api/v1.0/trucks")
public class TruckController {

    @Autowired
    private TruckService truckService;

    @GetMapping
    public ResponseEntity<List<Truck>> getAllTrucks() {
        // return new ResponseEntity<String>("All trucks", HttpStatus.OK);
        return new ResponseEntity<List<Truck>>(truckService.allTrucks(), HttpStatus.OK);
    }

    // unsafe because of the expose of the obj id
    @GetMapping("/{id}")
    public ResponseEntity<Optional<Truck>> getTruckById(@PathVariable ObjectId id) {
        return new ResponseEntity<Optional<Truck>>(truckService.truckById(id), HttpStatus.OK);
    }

    // better to not expose the ObjId
    @GetMapping("/imdb/{imdbId}")
    public ResponseEntity<Optional<Truck>> getTruckByImdbId(@PathVariable String imdbId) {
        return new ResponseEntity<Optional<Truck>>(truckService.truckByImdbId(imdbId), HttpStatus.OK);
    }
}
