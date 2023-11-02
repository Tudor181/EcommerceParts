package com.truckcompany.example.TruckCompany.Services;


import java.util.ArrayList;
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
import com.truckcompany.example.TruckCompany.Domain.User;
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
        UserCart userCart = userCartRepository.findByUserId(userId);
         TruckPartInventory item = truckpartService.get(itemId);
        if (item == null) {
            return null;
        }
        if(userCart == null)
        {
            userCart = new UserCart();
            userCart.setUserId(userId);
            userCart.addTruckPart(itemId);
            userCartRepository.insert(userCart);
        }
        else{
        
        userCart.addTruckPart(itemId);
        userCartRepository.save(userCart);
        }
        return userCart;      
    }

    @Override
    public List<TruckPartInventory> getCartByUserId(String id) {
        UserCart userCart = userCartRepository.findByUserId(id);
        if(userCart == null)
        {
            return null;
        }
        List<TruckPartInventory> inventory = new ArrayList<>();
        List<String> truckPartIds = userCart.getTruckPartIds();
        for (String truckPartId : truckPartIds) {
            TruckPartInventory truckPart = truckpartService.get(truckPartId);
            if (truckPart == null) {
                continue;
            }
            inventory.add(truckPart);
        }
        return inventory;
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
    @Override
    public UserCart get(String id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }
   

}

/**
 * UserCartService is a service class that provides methods for managing user carts.
 * It implements the IUserCartService interface.
 * 
 * The class has the following fields:
 * 
 * - userCartRepository: The repository for accessing the UserCart data.
 * - truckpartService: The service for accessing the TruckPartInventory data.
 * 
 * The class has the following methods:
 * 
 * - UserCartService(IUserCartRepository userCartRepository, ITruckPartInventoryService truckpartService): Constructor that initializes the userCartRepository and truckpartService fields.
 * 
 * - addItemToUserCart(String userId, String itemId): Adds an item to a user's cart. If the user does not have a cart, it creates one and adds the item to it. If the user already has a cart, it just adds the item to it. If the item does not exist, it returns null.
 * 
 * - getCartByUserId(String id): Returns all the items in a user's cart. If the user does not have a cart, it returns null.
 * 
 * - insert(UserCart item): Inserts a new UserCart. If the UserCart is null or its ID is null or empty, it throws an IllegalArgumentException. Otherwise, it inserts the UserCart and returns true.
 * 
 * - update(UserCart item): Updates an existing UserCart. If the UserCart is null or its ID is null or empty, or if no UserCart with the given ID exists, it throws an IllegalArgumentException. Otherwise, it updates the UserCart and returns true.
 * 
 * - delete(String id): Deletes the UserCart with the given ID. If the ID is null or empty, or if no UserCart with the given ID exists, it throws an IllegalArgumentException. Otherwise, it deletes the UserCart and returns true.
 * 
 * - getAll(): Returns a list of all UserCarts.
 * 
 * - get(String id): This method is not implemented yet. It is supposed to get a UserCart from the repository by its id.
 */