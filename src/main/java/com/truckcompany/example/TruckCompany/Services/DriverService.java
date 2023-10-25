package com.truckcompany.example.TruckCompany.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.Domain.Driver;
import com.truckcompany.example.TruckCompany.Domain.Truck;
import com.truckcompany.example.TruckCompany.Repositories.IDriverRepository;

@Service
public class DriverService {

    @Autowired
    private IDriverRepository driverRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private void assignDriverToTruck(Driver driver, String truckId) {
        mongoTemplate.update(Truck.class)
                .matching(Criteria.where("_id").is(truckId))
                .apply(new Update().push("driverIds").value(driver))
                .first();
    }

    public Driver createDriver(String driverName, int driverAge, String truckId) {
        // insert returns the data u just pushed to the db

        Driver driver = driverRepository.insert(new Driver(driverName, driverAge));

        assignDriverToTruck(driver, truckId);

        return driver;
    }

    public List<Driver> allDrivers() {
        return driverRepository.findAll();
    }

    public Optional<Driver> driverById(String id) {
        try {
            Optional<Driver> driverFound = driverRepository.findById(new ObjectId(id));

            if (driverFound != null && !driverFound.isEmpty()) {
                System.out.println("Driver found to STRING :" + driverFound.toString());
                return driverFound;
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("The driver was not found");
        }

    }

    public Driver changeDriverName(String id, String newName) throws RuntimeException {
        try {
            mongoTemplate.update(Driver.class)
                    .matching(Criteria.where("_id").is(id))
                    .apply(new Update().set("name", newName))
                    .first();
            Optional<Driver> driverFound = this.driverById(id);
            if (driverFound != null && !driverFound.isEmpty()) {
                // driverFound.get().setName(newName);

                return driverFound.get();
            } else {
                return null;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException();
        }
    }
}
