package com.truckcompany.example.TruckCompany.swing;

import org.springframework.web.client.RestTemplate;

public class MyRestTemplate {
    private static RestTemplate restTemplate = new RestTemplate();

    public static RestTemplate getRestTemplate() {
        if (restTemplate != null) {
            return restTemplate;
        } else {
            restTemplate = new RestTemplate();

        }
        return restTemplate;
    }

}
