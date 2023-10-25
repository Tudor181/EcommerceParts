package com.truckcompany.example.TruckCompany.Domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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

    public static final int MAX_SPEED = 90;

    @Id
    private String id;
    @NotBlank
    private String imdbId;
    private String imageId;
    @NotBlank
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

    // daca am sa maresc proiectul am sa fac settere manuale care sa verifice dar
    // cred ca asta voi face in service inainte sa apelez settere..
    // am setter automat asta l am facut pt exemplu

    // public void setManufacturer(String manufacturer){
    // this.manufacturer=manufacturer;
    // }

    // public String getManufacturer() {
    // return manufacturer;
    // }

}
