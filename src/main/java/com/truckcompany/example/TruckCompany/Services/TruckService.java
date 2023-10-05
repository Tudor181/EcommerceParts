package com.truckcompany.example.TruckCompany.Services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.Truck;
import com.truckcompany.example.TruckCompany.Repositories.ITruckRepository;

@Service
public class TruckService {
    // let the framework know to
    @Autowired // instanciate this class here
    private ITruckRepository truckRepository;

    public List<Truck> allTrucks() {
        return truckRepository.findAll();
    }

    public Optional<Truck> truckById(ObjectId id) {
        return truckRepository.findById(id);
    }

    public Optional<Truck> truckByImdbId(String imdbId) {
        return truckRepository.findTruckByImdbId(imdbId);
    }

}
