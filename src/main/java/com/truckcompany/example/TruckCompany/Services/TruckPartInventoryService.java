package com.truckcompany.example.TruckCompany.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Repositories.ITruckPartinventoryRepository;

@Service
public class TruckPartInventoryService implements ITruckPartInventoryService {
    private final ITruckPartinventoryRepository truckPartInventoryRepository;

    public TruckPartInventoryService(ITruckPartinventoryRepository truckPartInventoryRepository) {
        this.truckPartInventoryRepository = truckPartInventoryRepository;
    }

    @Override
    public TruckPartInventory get(String id) {
        Optional<TruckPartInventory> optionalTruckPartInventory = truckPartInventoryRepository.findById(new ObjectId(id));
        return optionalTruckPartInventory.orElse(null);
    }

    @Override
    public void insert(TruckPartInventory item) {
        truckPartInventoryRepository.save(item);
    }

    @Override
    public void update(TruckPartInventory item) {
        truckPartInventoryRepository.save(item);
    }

    @Override
    public void delete(String id) {
        truckPartInventoryRepository.deleteById(new ObjectId(id));
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