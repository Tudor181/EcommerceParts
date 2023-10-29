package com.truckcompany.example.TruckCompany.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Domain.Truck;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
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
    public TruckPartInventory get(String id) {
        Optional<TruckPartInventory> optionalTruckPartInventory = truckPartInventoryRepository.findById(new ObjectId(id));
        return optionalTruckPartInventory.orElse(null);
    }

    @Override
    public Boolean insert(TruckPartInventory item) {
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
    public Boolean update(TruckPartInventory item) {
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
    public Boolean delete(String id) {
        TruckPartInventory itemToDelete = this.truckPartInventoryRepository.findById(new ObjectId(id)).orElse(null);
        if(itemToDelete == null)
            return false;
        truckPartInventoryRepository.deleteById(new ObjectId(id));
        return true;
    }

    @Override
    public List<TruckPartInventory> getAll() {
        return truckPartInventoryRepository.findAll();
    }

  @Override
public List<TruckPartInventory> GetTruckPartInventoryByTruckPartId(String categoryId, String truckId) {
    List<String> truckIds = new ArrayList<>();
    truckIds.add(truckId);
    return truckPartInventoryRepository.findByCategoryIdAndTruckIdIn(categoryId, truckIds);
}
}