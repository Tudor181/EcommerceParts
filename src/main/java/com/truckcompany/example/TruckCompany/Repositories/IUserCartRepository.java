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
