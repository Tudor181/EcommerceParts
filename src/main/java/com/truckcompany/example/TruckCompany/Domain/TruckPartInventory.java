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
    private Double price;



    public TruckPartInventory(List<String> truckId, String id, int amount, String categoryId, String name) {
        this.truckId = truckId;
        this.id = id;
        this.amount = amount;
        this.categoryId = categoryId;
        this.name = name;
    }

    public TruckPartInventory() {
    }


    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getPrice() {
        return price;
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

/**
 * TruckPartInventory is a domain object that represents an inventory of truck parts in the system.
 * It is annotated with @Document to indicate that it is a MongoDB document.
 * 
 * The class has the following fields:
 * 
 * - truckId: A list of IDs of the trucks associated with the parts in the inventory.
 * - id: The ID of the inventory.
 * - amount: The amount of parts in the inventory.
 * - categoryId: The ID of the category of the parts in the inventory.
 * - name: The name of the inventory.
 * - price: The price of the parts in the inventory.
 * 
 * The class has the following methods:
 * 
 * - TruckPartInventory(List<String> truckId, String id, int amount, String categoryId, String name): Constructor that initializes the truckId, id, amount, categoryId, and name fields.
 * 
 * - TruckPartInventory(): Default constructor.
 * 
 * - setPrice(Double price): Sets the price of the parts in the inventory.
 * 
 * - getPrice(): Returns the price of the parts in the inventory.
 * 
 * - setName(String name): Sets the name of the inventory.
 * 
 * - getName(): Returns the name of the inventory.
 * 
 * - getTruckId(): Returns the list of IDs of the trucks associated with the parts in the inventory.
 * 
 * - setTruckId(List<String> truckId): Sets the list of IDs of the trucks associated with the parts in the inventory.
 * 
 * - getId(): Returns the ID of the inventory.
 * 
 * - setId(String id): Sets the ID of the inventory.
 * 
 * - getAmount(): Returns the amount of parts in the inventory.
 * 
 * - setAmount(int amount): Sets the amount of parts in the inventory.
 * 
 * - getCategoryId(): Returns the ID of the category of the parts in the inventory.
 * 
 * - setCategoryId(String categoryId): Sets the ID of the category of the parts in the inventory.
 * 
 * - equals(Object obj): Checks if the inventory is equal to another object. Two inventories are considered equal if their amount, categoryId, id, and truckId are the same.
 */