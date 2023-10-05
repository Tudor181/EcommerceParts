package com.truckcompany.example.TruckCompany.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.Driver;
import com.truckcompany.example.TruckCompany.Truck;
import com.truckcompany.example.TruckCompany.Repositories.IDriverRepository;

@Service
public class DriverService {

    @Autowired
    private IDriverRepository driverRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private void assignDriverToTruck(Driver driver, String imdbId) {
        mongoTemplate.update(Truck.class)
                .matching(Criteria.where("imdbId").is(imdbId))
                .apply(new Update().push("driverIds").value(driver))
                .first();
    }

    public Driver createDriver(String driverName, int driverAge, String imdbId) {
        // insert returns the data u just pushed to the db

        Driver driver = driverRepository.insert(new Driver(driverName, driverAge));

        assignDriverToTruck(driver, imdbId);

        return driver;
    }
}
