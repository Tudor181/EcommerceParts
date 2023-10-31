package com.truckcompany.example.TruckCompany.Services;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.ICategoryService;
import com.truckcompany.example.TruckCompany.DataAbstraction.ITruckPartInventoryService;
import com.truckcompany.example.TruckCompany.DataAbstraction.IUserCartService;
import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Domain.UserCart;
import com.truckcompany.example.TruckCompany.Repositories.ICategoryRepository;
import com.truckcompany.example.TruckCompany.Repositories.ITruckPartinventoryRepository;
import com.truckcompany.example.TruckCompany.Repositories.IUserCartRepository;


@Service
public class UserCartService implements IUserCartService{
    private final IUserCartRepository userCartRepository;
    private final ITruckPartInventoryService truckpartService;

    @Autowired
    public UserCartService(IUserCartRepository userCartRepository, ITruckPartInventoryService truckpartService) {
        this.userCartRepository = userCartRepository;
        this.truckpartService = truckpartService;
    }
     public UserCart addItemToUserCart(String userId, String itemId) {
        UserCart userCart = userCartRepository.findById(new ObjectId(userId)).orElseThrow(() -> new IllegalArgumentException("UserCart with id " + userId + " not found"));
        TruckPartInventory item = truckpartService.get(itemId);
        if (item == null) {
            throw new IllegalArgumentException("Item with id " + itemId + " not found");
        }
        userCart.addTruckPart(itemId);
        userCartRepository.save(userCart);
        return userCart;
    }

    @Override
    public UserCart get(String id) {
        Optional<UserCart> userCart = userCartRepository.findById(new ObjectId(id));
        if (!userCart.isPresent()) {
            throw new IllegalArgumentException("UserCart with id " + id + " not found");
        }
        return userCart.get();
    }
    @Override
    public Boolean insert(UserCart item) {
        if (item == null || item.getId() == null || item.getId().isEmpty()) {
            throw new IllegalArgumentException("Invalid UserCart data");
        }
        userCartRepository.save(item);
        return true;
    }

    @Override
    public Boolean update(UserCart item) {
        if (item == null || item.getId() == null || item.getId().isEmpty()) {
            throw new IllegalArgumentException("Invalid UserCart data");
        }
        if (!userCartRepository.existsById(new ObjectId(item.getId()))) {
            throw new IllegalArgumentException("UserCart with id " + item.getId() + " not found");
        }
        userCartRepository.save(item);
        return true;
    }

    @Override
    public Boolean delete(String id) {
        if (id == null || id.isEmpty()) {
            throw new IllegalArgumentException("Invalid id");
        }
        if (!userCartRepository.existsById(new ObjectId(id))) {
            throw new IllegalArgumentException("UserCart with id " + id + " not found");
        }
        userCartRepository.deleteById(new ObjectId(id));
        return true;
    }
    @Override
    public List<UserCart> getAll() {
        return userCartRepository.findAll();
    }

}