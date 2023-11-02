package com.truckcompany.example.TruckCompany.Domain;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "userCart")
@Data
public class UserCart {
    private List<String> TruckPartIds;
    private String id;
    private String UserId;

    public UserCart(List<String> TruckPartIds, String id, String UserId) {
        this.TruckPartIds = TruckPartIds;
        this.id = id;
        this.UserId = UserId;
    }

    public String getUserId()
    {
        return this.UserId;
    }
    public void setUserId(String userId)
    {
        this.UserId = userId;
    }

    public UserCart(List<String> TruckPartIds, String id) {
        this.TruckPartIds = TruckPartIds;
        this.id = id;
    }

    public UserCart() {
    }
    

    public List<String> getTruckPartIds() {
        return TruckPartIds;
    }
    public void setTruckPartIds(List<String> TruckPartIds) {
        this.TruckPartIds = TruckPartIds;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }
    public Boolean contains(String truckPartId) {
        return TruckPartIds.contains(truckPartId);
    }
    public void addTruckPart(String truckPartId) {
        if(this.TruckPartIds == null)
        {
            TruckPartIds = new ArrayList<>();
        }
         if(!TruckPartIds.contains(truckPartId))
        {
            TruckPartIds.add(truckPartId);
        }
    }
}

/**
 * UserCart is a domain object that represents a user's cart in the system.
 * It is annotated with @Document to indicate that it is a MongoDB document.
 * 
 * The class has the following fields:
 * 
 * - TruckPartIds: A list of IDs of the TruckParts in the cart.
 * - id: The ID of the cart.
 * - UserId: The ID of the user who owns the cart.
 * 
 * The class has the following methods:
 * 
 * - UserCart(List<String> TruckPartIds, String id, String UserId): Constructor that initializes the TruckPartIds, id, and UserId fields.
 * 
 * - getUserId(): Returns the ID of the user who owns the cart.
 * 
 * - setUserId(String userId): Sets the ID of the user who owns the cart.
 * 
 * - UserCart(List<String> TruckPartIds, String id): Constructor that initializes the TruckPartIds and id fields.
 * 
 * - UserCart(): Default constructor.
 * 
 * - getTruckPartIds(): Returns the list of IDs of the TruckParts in the cart.
 * 
 * - setTruckPartIds(List<String> TruckPartIds): Sets the list of IDs of the TruckParts in the cart.
 * 
 * - setId(String id): Sets the ID of the cart.
 * 
 * - getId(): Returns the ID of the cart.
 * 
 * - contains(String truckPartId): Checks if the cart contains a TruckPart with the given ID.
 * 
 * - addTruckPart(String truckPartId): Adds a TruckPart with the given ID to the cart.
 */