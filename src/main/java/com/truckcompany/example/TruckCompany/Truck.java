package com.truckcompany.example.TruckCompany;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "trucks")
@Data // generate automated getter & setter for each field
@AllArgsConstructor
@NoArgsConstructor
public class Truck {
    @Id
    private ObjectId id;
    private String imdbId;
    private String title;
    private String releaseDate;
    private String trailerLink;
    private String poster;
    private List<String> genres;
    private List<String> backdrops;
    // private String dateOfManufacture; //mine
    private String manufacturer; // mine

    @DocumentReference
    private List<Driver> driverIds; // mine

}
