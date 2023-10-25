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