package com.truckcompany.example.TruckCompany.Repositories;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;


@Repository
public interface ITruckPartinventoryRepository extends MongoRepository<TruckPartInventory, ObjectId> {
    List<TruckPartInventory> findByCategoryIdAndTruckIdIn(String categoryId, List<String> truckIds);
}

/**
 * ITruckPartinventoryRepository is an interface for a repository that provides methods for managing TruckPartInventory objects.
 * It extends the MongoRepository interface from Spring Data MongoDB, which provides methods for CRUD operations.
 * 
 * The interface has the following methods:
 * 
 * - findByCategoryIdAndTruckIdIn(String categoryId, List<String> truckIds): Returns a list of TruckPartInventory objects that have the given category ID and whose truck ID is in the given list of truck IDs.
 * 
 * The interface is annotated with @Repository, which indicates that it's a Bean and it's expected to provide database operations.
 * 
 * The interface takes two parameters:
 * 
 * - TruckPartInventory: The domain type the repository manages. It manages TruckPartInventory objects.
 * 
 * - ObjectId: The type of the id of the domain the repository manages. The id of the TruckPartInventory objects is of type ObjectId.
 */