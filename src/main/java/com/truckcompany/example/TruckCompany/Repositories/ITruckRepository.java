package com.truckcompany.example.TruckCompany.Repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.Truck;

// import io.swagger.v3.oas.annotations.Hidden;

// @Hidden
@Repository
public interface ITruckRepository extends MongoRepository<Truck, ObjectId> {
    // framework will do this method automatically(u can do this with any property
    // (field) as long as they are unique)
    Optional<Truck> findTruckByImdbId(String imdbId);

    List<Truck> findByIdIn(List<String> Id);
}
