package com.truckcompany.example.TruckCompany.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.Category;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, ObjectId>{
    
}

/**
 * ICategoryRepository is an interface for a repository that provides methods for managing Category objects.
 * It extends the MongoRepository interface from Spring Data MongoDB, which provides methods for CRUD operations.
 * 
 * The interface has no methods of its own. It inherits all its methods from the MongoRepository interface.
 * 
 * The interface is annotated with @Repository, which indicates that it's a Bean and it's expected to provide database operations.
 * 
 * The interface takes two parameters:
 * 
 * - Category: The domain type the repository manages. It manages Category objects.
 * 
 * - ObjectId: The type of the id of the domain the repository manages. The id of the Category objects is of type ObjectId.
 */