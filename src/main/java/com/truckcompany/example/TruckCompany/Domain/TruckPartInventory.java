package com.truckcompany.example.TruckCompany.Domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "truckpartinventory")
@Data
public class TruckPartInventory {
    private List<String> truckId;
    private String id;
    private int amount;
    private String categoryId;
    private String name;

    public TruckPartInventory(List<String> truckId, String id, int amount, String categoryId, String name) {
        this.truckId = truckId;
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.name = name;
    }

    public TruckPartInventory() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public List<String> getTruckId() {
        return truckId;
    }

    public void setTruckId(List<String> truckId) {
        this.truckId = truckId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof TruckPartInventory)) {
            return false;
        }
        TruckPartInventory truckItem = (TruckPartInventory) obj;
        return this.amount == truckItem.amount && this.categoryId == truckItem.categoryId && this.id == truckItem.id
                && this.truckId == truckItem.truckId;
    }
}