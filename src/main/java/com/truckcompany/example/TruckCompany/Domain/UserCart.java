package com.truckcompany.example.TruckCompany.Domain;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class UserCart {
    private List<String> TruckPartIds;
    private String id;

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
        TruckPartIds.add(truckPartId);
    }
}

