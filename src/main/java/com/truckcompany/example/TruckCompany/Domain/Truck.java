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

}

/**
 * Truck is a domain object that represents a truck in the system.
 * It is annotated with @Document to indicate that it is a MongoDB document.
 * 
 * The class has the following fields:
 * 
 * - MAX_SPEED: The maximum speed of the truck. This is a constant and is set to 90.
 * - id: The ID of the truck.
 * - imdbId: The IMDB ID of the truck.
 * - imageId: The ID of the image of the truck.
 * - manufacturer: The manufacturer of the truck.
 * - nrOfRegistration: The number of registration of the truck.
 * - manufactureYear: The year the truck was manufactured.
 * - buyDate: The date the truck was bought.
 * - driverIds: A list of IDs of the drivers of the truck.
 * 
 * The class has the following methods:
 * 
 * - Truck(String imageId, String manufacturer, Optional<String> nrOfRegistration, int manufactureYear, String buyDate): Constructor that initializes the imageId, manufacturer, manufactureYear, and buyDate fields. If nrOfRegistration is present, it also initializes the nrOfRegistration field. Otherwise, it sets nrOfRegistration to "Not registered yet". It also initializes driverIds to an empty list.
 */