package com.truckcompany.example.TruckCompany.Requests;

import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AddToCartRequest {
    public String userId;

    public String itemId;
}
