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
