package com.truckcompany.example.TruckCompany;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "drivers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Driver {
    @Id
    private String id;
    private String name;
    private int age;

    public Driver(String name, int age) {
        // this.name.append(name);
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Driver: " + name + " age: " + age;
    }

    // public void setName(String newName) {
    // if (name.length() > 0) {
    // name.delete(0, name.length());
    // }
    // name.append(newName);
    // }

}
