package com.truckcompany.example.TruckCompany.Repositories;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.Truck;
import com.truckcompany.example.TruckCompany.Domain.UserCart;

// import io.swagger.v3.oas.annotations.Hidden;

// @Hidden
@Repository
public interface IUserCartRepository extends MongoRepository<UserCart, ObjectId> {
    
@Query("{ 'UserId' : ?0 }")
UserCart findByUserId(String UserId);
}


/**
 * IUserCartRepository is an interface for a repository that provides methods for managing UserCart objects.
 * It extends the MongoRepository interface from Spring Data MongoDB, which provides methods for CRUD operations.
 * 
 * The interface has the following methods:
 * 
 * - findByUserId(String UserId): Returns a UserCart object that has the given user ID. If no such UserCart exists, it returns null.
 * 
 * The interface is annotated with @Repository, which indicates that it's a Bean and it's expected to provide database operations.
 * 
 * The interface takes two parameters:
 * 
 * - UserCart: The domain type the repository manages. It manages UserCart objects.
 * 
 * - ObjectId: The type of the id of the domain the repository manages. The id of the UserCart objects is of type ObjectId.
 */