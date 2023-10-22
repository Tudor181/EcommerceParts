package com.truckcompany.example.TruckCompany.Requests;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NewDriverRequest {
    public String driverName;

    public int driverAge;

    public String truckId;
}
