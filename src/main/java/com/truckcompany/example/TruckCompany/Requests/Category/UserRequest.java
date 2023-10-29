package com.truckcompany.example.TruckCompany.Requests.Category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class UserRequest {
    public String name;
    public String email;
    public String password;    
}

