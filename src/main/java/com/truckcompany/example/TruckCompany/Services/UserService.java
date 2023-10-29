package com.truckcompany.example.TruckCompany.Services;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

import com.truckcompany.example.TruckCompany.DataAbstraction.IUserService;
import com.truckcompany.example.TruckCompany.Domain.User;
import com.truckcompany.example.TruckCompany.Repositories.IUserRepository;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserService implements IUserService{
    private final IUserRepository userService;
    
    public boolean validateEmail(String email) {
        String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    @Autowired
    public UserService(IUserRepository userService){
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
        if(user == null){
           return null;
        }
        if(!this.validateEmail(item.getEmail())){
           return null;
        }
        if(item.getName().length() < 1){
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
        if(user == null){
           return null;
        }
        this.userService.deleteById(new ObjectId(id));
        return true;
    }

    @Override
    public List<User> getAll() {
        return this.userService.findAll();
    }
    
}
