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

