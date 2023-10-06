package com.truckcompany.example.TruckCompany.Requests;

import java.util.Optional;

import jakarta.validation.constraints.NotNull;

public class NewTruckRequest {
    @NotNull
    public String manufacturer;
    public Optional<String> title;
}
