package com.truckcompany.example.TruckCompany.DataAbstraction;
import java.util.List;

import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Domain.UserCart;

public interface IUserCartService extends IService<UserCart> {
    public UserCart addItemToUserCart(String userId, String itemId) throws MyException;
    public List<TruckPartInventory> getCartByUserId(String id)  throws MyException; 
    public Boolean deleteUserCart(String userId) throws MyException;
}
