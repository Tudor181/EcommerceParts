package com.truckcompany.example.TruckCompany.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.User;

@Repository
public interface IUserRepository extends MongoRepository<User, ObjectId> {
    public User findByEmail(String email);
    public User findByEmailAndPassword(String email, String password);
}

/**
 * IUserRepository is an interface for a repository that provides methods for managing User objects.
 * It extends the MongoRepository interface from Spring Data MongoDB, which provides methods for CRUD operations.
 * 
 * The interface has the following methods:
 * 
 * - findByEmail(String email): Returns a User object that has the given email. If no such User exists, it returns null.
 * 
 * - findByEmailAndPassword(String email, String password): Returns a User object that has the given email and password. If no such User exists, it returns null.
 * 
 * The interface is annotated with @Repository, which indicates that it's a Bean and it's expected to provide database operations.
 * 
 * The interface takes two parameters:
 * 
 * - User: The domain type the repository manages. It manages User objects.
 * 
 * - ObjectId: The type of the id of the domain the repository manages. The id of the User objects is of type ObjectId.
 */