package com.truckcompany.example.TruckCompany.Repositories;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.truckcompany.example.TruckCompany.Domain.Category;

@Repository
public interface ICategoryRepository extends MongoRepository<Category, ObjectId>{
    
}
