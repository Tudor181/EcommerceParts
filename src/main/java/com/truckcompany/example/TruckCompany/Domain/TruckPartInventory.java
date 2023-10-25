package com.truckcompany.example.TruckCompany.Domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "truckpartinventory")
public class TruckPartInventory {
    private List<String> truckId;
    private String id;
    private int amount;
    private String categoryId;

    public TruckPartInventory(List<String> truckId, String id, int amount, String categoryId) {
        this.truckId = truckId;
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
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
}