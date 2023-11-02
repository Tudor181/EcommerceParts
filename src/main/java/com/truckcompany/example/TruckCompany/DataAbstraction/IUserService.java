package com.truckcompany.example.TruckCompany.DataAbstraction;

import com.truckcompany.example.TruckCompany.Domain.User;

public interface IUserService extends IService<User> {
    public User getByEmail(String email, String password) throws MyException;
}
