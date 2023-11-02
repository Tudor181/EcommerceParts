package com.truckcompany.example.TruckCompany.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Domain.Truck;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
import com.truckcompany.example.TruckCompany.DataAbstraction.MyException;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Repositories.ICategoryRepository;
import com.truckcompany.example.TruckCompany.Repositories.ITruckPartinventoryRepository;
import com.truckcompany.example.TruckCompany.Repositories.ITruckRepository;

@Service
public class TruckPartInventoryService implements ITruckPartInventoryService {
    private final ITruckPartinventoryRepository truckPartInventoryRepository;
    private final ICategoryRepository categoryRepository;
    private final ITruckRepository truckRepository;

    public TruckPartInventoryService(ITruckPartinventoryRepository truckPartInventoryRepository,
            ICategoryRepository categoryRepository, ITruckRepository truckRepository) {
        this.truckPartInventoryRepository = truckPartInventoryRepository;
        this.categoryRepository = categoryRepository;
        this.truckRepository = truckRepository;
    }

    @Override
    public TruckPartInventory get(String id) throws MyException {
        try {
            Optional<TruckPartInventory> optionalTruckPartInventory = truckPartInventoryRepository
                    .findById(new ObjectId(id));
            return optionalTruckPartInventory.orElse(null);
        } catch (Exception e) {
            throw new MyException("An error occurred while getting the truck part inventory", e);
        }
    }

    @Override
    public Boolean insert(TruckPartInventory item) throws MyException{
        Category category = this.categoryRepository.findById(new ObjectId(item.getCategoryId())).orElse(null);
        if(category == null)
            return false;
        
        List<Truck> truck = this.truckRepository.findByIdIn(item.getTruckId());
        if(truck.size() != item.getTruckId().size())
            return false;
        if(item.getName().length()<1)
            return false;
        return truckPartInventoryRepository.save(item).getId().length() > 0;
    }

    @Override
    public Boolean update(TruckPartInventory item)throws MyException {
        Category category = this.categoryRepository.findById(new ObjectId(item.getCategoryId())).orElse(null);
        if(category == null)
            return false;
        
        List<Truck> truck = this.truckRepository.findByIdIn(item.getTruckId());
        if(truck.size() != item.getTruckId().size())
            return false;
        if(item.getName().length()<1)
            return false;
        TruckPartInventory itemToUpdate = this.truckPartInventoryRepository.findById(new ObjectId(item.getId())).orElse(null);
        if(itemToUpdate == null)
            return false;
        itemToUpdate.setName(item.getName());
        itemToUpdate.setTruckId(item.getTruckId());
        itemToUpdate.setAmount(item.getAmount());
        itemToUpdate.setCategoryId(item.getCategoryId());
        this.truckPartInventoryRepository.save(itemToUpdate);
        return true;
    }

    @Override
    public Boolean delete(String id) throws MyException{
        TruckPartInventory itemToDelete = this.truckPartInventoryRepository.findById(new ObjectId(id)).orElse(null);
        if(itemToDelete == null)
            return false;
        truckPartInventoryRepository.deleteById(new ObjectId(id));
        return true;
    }

    @Override
    public List<TruckPartInventory> getAll() throws MyException{
        return truckPartInventoryRepository.findAll();
    }

  @Override
public List<TruckPartInventory> GetTruckPartInventoryByTruckPartId(String categoryId, String truckId) throws MyException{
    List<String> truckIds = new ArrayList<>();
    truckIds.add(truckId);
    return truckPartInventoryRepository.findByCategoryIdAndTruckIdIn(categoryId, truckIds);
}
}

/**
 * TruckPartInventoryService is a service class that provides methods for managing truck part inventories.
 * It implements the ITruckPartInventoryService interface.
 * 
 * The class has the following fields:
 * 
 * - truckPartInventoryRepository: The repository for accessing the TruckPartInventory data.
 * - categoryRepository: The repository for accessing the Category data.
 * - truckRepository: The repository for accessing the Truck data.
 * 
 * The class has the following methods:
 * 
 * - TruckPartInventoryService(ITruckPartinventoryRepository truckPartInventoryRepository, ICategoryRepository categoryRepository, ITruckRepository truckRepository): Constructor that initializes the truckPartInventoryRepository, categoryRepository, and truckRepository fields.
 * 
 * - get(String id): Returns the TruckPartInventory with the given ID. If no such TruckPartInventory exists, it returns null.
 * 
 * - insert(TruckPartInventory item): Inserts a new TruckPartInventory. If the Category of the TruckPartInventory does not exist, or if the number of Trucks does not match the number of Truck IDs in the TruckPartInventory, or if the name of the TruckPartInventory is less than 1 character long, it returns false. Otherwise, it returns true if the TruckPartInventory was inserted successfully.
 * 
 * - update(TruckPartInventory item): Updates an existing TruckPartInventory. If the Category of the TruckPartInventory does not exist, or if the number of Trucks does not match the number of Truck IDs in the TruckPartInventory, or if the name of the TruckPartInventory is less than 1 character long, or if no TruckPartInventory with the given ID exists, it returns false. Otherwise, it updates the TruckPartInventory and returns true if the update was successful.
 * 
 * - delete(String id): Deletes the TruckPartInventory with the given ID. If no TruckPartInventory with the given ID exists, it returns false. Otherwise, it deletes the TruckPartInventory and returns true.
 * 
 * - getAll(): Returns a list of all TruckPartInventories.
 * 
 * - GetTruckPartInventoryByTruckPartId(String categoryId, String truckId): Returns a list of TruckPartInventories that have the given Category ID and Truck ID.
 */