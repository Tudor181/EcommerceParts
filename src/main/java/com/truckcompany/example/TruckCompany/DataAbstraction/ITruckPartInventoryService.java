package com.truckcompany.example.TruckCompany.DataAbstraction;
import java.util.List;

import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;

public interface ITruckPartInventoryService extends IService<TruckPartInventory> {
    public List<TruckPartInventory> GetTruckPartInventoryByTruckPartId(String categoryId, String truckId);
}
