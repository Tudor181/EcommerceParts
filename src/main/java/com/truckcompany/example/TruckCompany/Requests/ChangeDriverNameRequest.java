package com.truckcompany.example.TruckCompany.Requests;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ChangeDriverNameRequest {
    public String newName;

    public String driverId;
}
