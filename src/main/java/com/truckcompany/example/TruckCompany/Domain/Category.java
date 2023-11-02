package com.truckcompany.example.TruckCompany.Domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private String name;

    private String userId;

    @Id
    private String id;


    public void setUserId(String id) {
        this.id = id;
    }

    public String getUserId()
    {
        return this.userId;
    }
    public Category(String name) {
        this.name = name;
    }

    public void setId(String Id) {
        this.id = Id;
    }

    public String getId() {
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

/**
 * Category is a domain object that represents a category in the system.
 * It is annotated with @Document to indicate that it is a MongoDB document.
 * 
 * The class has the following fields:
 * 
 * - name: The name of the category.
 * - userId: The ID of the user associated with the category.
 * - id: The ID of the category.
 * 
 * The class has the following methods:
 * 
 * - setUserId(String id): Sets the ID of the user associated with the category.
 * 
 * - getUserId(): Returns the ID of the user associated with the category.
 * 
 * - Category(String name): Constructor that initializes the name field.
 * 
 * - setId(String Id): Sets the ID of the category.
 * 
 * - getId(): Returns the ID of the category.
 * 
 * - getName(): Returns the name of the category.
 * 
 * - setName(String name): Sets the name of the category.
 * 
 * - equals(Object obj): Checks if the category is equal to another object. Two categories are considered equal if their name and id are the same.
 */