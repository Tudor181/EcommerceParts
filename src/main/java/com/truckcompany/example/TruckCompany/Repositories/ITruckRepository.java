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

/**
 * ITruckRepository is an interface for a repository that provides methods for managing Truck objects.
 * It extends the MongoRepository interface from Spring Data MongoDB, which provides methods for CRUD operations.
 * 
 * The interface has the following methods:
 * 
 * - findTruckByImdbId(String imdbId): Returns an Optional that may contain a Truck with the given IMDB ID. If no such Truck exists, the Optional will be empty.
 * 
 * - findByIdIn(List<String> Id): Returns a list of Trucks whose IDs are in the given list.
 * 
 * The interface is annotated with @Repository, which indicates that it's a Bean and it's expected to provide database operations.
 * 
 * The interface takes two parameters:
 * 
 * - Truck: The domain type the repository manages. It manages Truck objects.
 * 
 * - ObjectId: The type of the id of the domain the repository manages. The id of the Truck objects is of type ObjectId.
 */