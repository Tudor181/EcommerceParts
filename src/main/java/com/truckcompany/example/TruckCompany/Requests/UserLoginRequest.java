package com.truckcompany.example.TruckCompany.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserLoginRequest {
    public String userEmail;
    public String userPassword;
}
