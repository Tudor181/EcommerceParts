package com.truckcompany.example.TruckCompany.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.Truck;
import com.truckcompany.example.TruckCompany.Repositories.ITruckRepository;

@Service
public class TruckService {
    // let the framework know to
    @Autowired // instanciate this class here
    private ITruckRepository truckRepository;

    public List<Truck> allTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> truckById(String id) {

        try {
            Optional<Truck> truckFound = truckRepository.findById(new ObjectId(id));
            // List<String> driverIdsModified = new ArrayList<String>();

            if (truckFound != null && !truckFound.isEmpty()) {
                System.out
                        .println(
                                "bTruck found to STRING :" + truckFound.toString() + "\nMax speed: " + Truck.MAX_SPEED);
                // truckFound.ifPresent(value -> {
                // value.getDriverIds().forEach(driverId -> {
                // driverId.setId(driverId.getId().toString());
                // });
                // ;
                // });
                // (driverId -> {
                // driverId.toString();
                // });
                return truckFound;
            } else {
                return null;
            }

        } catch (

        RuntimeException e) {
            throw new RuntimeException("The truck was not found");
        }
    }

    public Optional<Truck> truckByImdbId(String imdbId) {
        return truckRepository.findTruckByImdbId(imdbId);
    }

    public Truck createTruck(String imageId, String manufacturer, Optional<String> nrOfRegistration,
            int manufactureYear) {

        // Truck truck = new Truck(id, "Default",
        // LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString(),
        // "undefined",
        // "undefined",
        // Collections.emptyList(), manufacturer, Collections.emptyList());

        Truck truckInserted = truckRepository
                .insert(new Truck(imageId, manufacturer, nrOfRegistration, manufactureYear,
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")).toString()));
        return truckInserted;
    }

    public boolean deleteTruckById(String truckId) {
        if (truckRepository.findById(new ObjectId(truckId)).isPresent()) {
            try {
                truckRepository.deleteById(new ObjectId(truckId));
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }
        return false;
    }

}
