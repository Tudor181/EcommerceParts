package com.truckcompany.example.TruckCompany.Domain;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class User {
    private String name;
    private String email;
    private String password;
    private String id;
    public User(String name, String email, String password, String id) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.id = id;
    }
    public User() {
    }
    public String getName() {
        return this.name;
    }
    public String getEmail() {
        return this.email;
    }
    public String getPassword() {
        return this.password;
    }
    public String getId() {
        return this.id;
    }
    public void setName(String name) {
        this.name=name;
    }
    public void setEmail(String email) {
        this.email=email;
    }
    public void setPassword(String password) {
        this.password=password;
    }
    public void setId(String id) {
        this.id=id;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        User user = (User) obj;
        return this.email.equals(user.getEmail()) && this.name.equals(user.getName()) && this.password.equals(user.getPassword()) && this.id.equals(user.getId());
    }


}


/**
 * User is a domain object that represents a user in the system.
 * It is annotated with @Document to indicate that it is a MongoDB document.
 * 
 * The class has the following fields:
 * 
 * - name: The name of the user.
 * - email: The email of the user.
 * - password: The password of the user.
 * - id: The ID of the user.
 * 
 * The class has the following methods:
 * 
 * - User(String name, String email, String password, String id): Constructor that initializes the name, email, password, and id fields.
 * 
 * - User(): Default constructor.
 * 
 * - getName(): Returns the name of the user.
 * 
 * - getEmail(): Returns the email of the user.
 * 
 * - getPassword(): Returns the password of the user.
 * 
 * - getId(): Returns the ID of the user.
 * 
 * - setName(String name): Sets the name of the user.
 * 
 * - setEmail(String email): Sets the email of the user.
 * 
 * - setPassword(String password): Sets the password of the user.
 * 
 * - setId(String id): Sets the ID of the user.
 * 
 * - equals(Object obj): Checks if the user is equal to another object. Two users are considered equal if their email, name, password, and id are the same.
 */