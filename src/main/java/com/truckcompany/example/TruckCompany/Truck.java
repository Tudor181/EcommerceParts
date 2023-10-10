package com.truckcompany.example.TruckCompany;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "trucks")
@Data // generate automated getter & setter for each field
@AllArgsConstructor
@NoArgsConstructor
public class Truck {

    @Id
    private String id;
    @NotBlank
    private String imdbId;
    private String imageId;
    private String manufacturer; // mine
    private String nrOfRegistration;
    private int manufactureYear;
    private String buyDate;

    @DocumentReference
    private List<Driver> driverIds; // mine

    public Truck(String imageId, String manufacturer, Optional<String> nrOfRegistration, int manufactureYear,
            String buyDate) {
        this.imageId = imageId;
        this.manufacturer = manufacturer;
        this.manufactureYear = manufactureYear;
        this.buyDate = buyDate;
        if (nrOfRegistration != null && nrOfRegistration.isPresent()) {
            this.nrOfRegistration = nrOfRegistration.get();
        } else {
            this.nrOfRegistration = "Not registered yet";
        }
        this.driverIds = Collections.emptyList();
    }

}
