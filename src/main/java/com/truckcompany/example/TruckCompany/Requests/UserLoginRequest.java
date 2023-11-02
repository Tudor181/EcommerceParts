package com.truckcompany.example.TruckCompany.Requests;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserLoginRequest {
    public String userEmail;
    public String userPassword;
}
