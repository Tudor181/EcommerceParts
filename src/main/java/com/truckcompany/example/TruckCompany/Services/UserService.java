package com.truckcompany.example.TruckCompany.Services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.IUserService;
import com.truckcompany.example.TruckCompany.Domain.User;
import com.truckcompany.example.TruckCompany.Repositories.IUserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService implements IUserService {
    private final IUserRepository userService;

    public boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Autowired
    public UserService(IUserRepository userService) {
        this.userService = userService;
    }

    @Override
    public User get(String id) {
        return this.userService.findById(new ObjectId(id)).orElse(null);
    }

    @Override
    public Boolean insert(User item) {
        return this.userService.save(item).getId().length() > 0;
    }

    @Override
    public Boolean update(User item) {
        User user = this.userService.findById(new ObjectId(item.getId())).orElse(null);
        if (user == null) {
            return null;
        }
        if (!this.validateEmail(item.getEmail())) {
            return null;
        }
        if (item.getName().length() < 1) {
            return null;
        }

        user.setEmail(item.getEmail());
        user.setName(item.getName());
        User usersaved = this.userService.save(user);
        return item.equals(usersaved);
    }

    @Override
    public Boolean delete(String id) {
        User user = this.userService.findById(new ObjectId(id)).orElse(null);
        if (user == null) {
            return null;
        }
        this.userService.deleteById(new ObjectId(id));
        return true;
    }

    @Override
    public List<User> getAll() {
        return this.userService.findAll();
    }

    @Override
    public User getByEmail(String email, String password) {
        return this.userService.findByEmailAndPassword(email, password);
    }

}

/**
 * UserService is a service class that provides methods for managing users.
 * It implements the IUserService interface.
 * 
 * The class has the following fields:
 * 
 * - userService: The repository for accessing the User data.
 * 
 * The class has the following methods:
 * 
 * - validateEmail(String email): Checks if the given email is valid. Returns true if the email is valid, false otherwise.
 * 
 * - UserService(IUserRepository userService): Constructor that initializes the userService field.
 * 
 * - get(String id): Returns the User with the given ID. If no such User exists, it returns null.
 * 
 * - insert(User item): Inserts a new User. If the User is successfully inserted, it returns true. Otherwise, it returns false.
 * 
 * - update(User item): Updates an existing User. If the User does not exist, or if the User's email is not valid, or if the User's name is empty, it returns null. Otherwise, it updates the User and returns true if the update was successful, false otherwise.
 * 
 * - delete(String id): Deletes the User with the given ID. If the User does not exist, it returns null. Otherwise, it deletes the User and returns true.
 * 
 * - getAll(): Returns a list of all Users.
 * 
 * - getByEmail(String email, String password): Returns the User with the given email and password. If no such User exists, it returns null.
 */
