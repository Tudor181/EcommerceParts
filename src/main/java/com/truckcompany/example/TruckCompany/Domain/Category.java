package com.truckcompany.example.TruckCompany.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "categories")
public class Category {
    private String name;
    @Id
    private String id;

    public Category(String name) {
        this.name = name;
    }

    public void setId(String Id)
    {
        this.id=Id;
    }

    public String getId()
    {
        return this.id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof Category)) {
            return false;
        }
        Category category = (Category) obj;
        return this.name.equals(category.getName()) && this.id.equals(category.getId());
    }
    
}