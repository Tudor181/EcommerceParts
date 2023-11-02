package com.truckcompany.example.TruckCompany.Controllers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truckcompany.example.TruckCompany.DataAbstraction.IUserCartService;
import com.truckcompany.example.TruckCompany.DataAbstraction.IUserService;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Domain.User;
import com.truckcompany.example.TruckCompany.Domain.UserCart;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.truckcompany.example.TruckCompany.Domain.User;
import java.io.UnsupportedEncodingException;
import java.util.Date;

@RestController
@RequestMapping("/user")
public class UserController {
    private final IUserService userService;
    private final IUserCartService userCartService;

    public UserController(IUserService userService, IUserCartService userCartService) {
        this.userService = userService;
        this.userCartService = userCartService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> get(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            User user = userService.get(objectId.toHexString());
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("register/")
    public ResponseEntity<Void> insert(@RequestBody User user) {
        try {
            System.out.println(user.toString());
            userService.insert(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody User user) {
        try {
            ObjectId objectId = new ObjectId(id);
            User existingUser = userService.get(objectId.toHexString());
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            user.setId(objectId.toHexString());
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> generateToken(String UserEmail, String UserPassword) {
        User user = userService.getByEmail(UserEmail,UserPassword);
        if (user == null) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getId())
                    .withClaim("username", user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                    .sign(algorithm);
            return ResponseEntity.ok(token);
        } catch (JWTCreationException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            User existingUser = userService.get(objectId.toHexString());
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            userService.delete(objectId.toHexString());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> users = userService.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/AddToCart/{userId}/{itemId}")
    public ResponseEntity<Boolean> addToCart(@PathVariable String userId, @PathVariable String itemId) {
        try {
            UserCart userCart = this.userCartService.addItemToUserCart(userId, itemId);
            if (userCart == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/GetCart/{userId}")
    public ResponseEntity<List<TruckPartInventory>> getCart(@PathVariable String userId) {
        try {
            List<TruckPartInventory> userCart = this.userCartService.getCartByUserId(userId);
            if (userCart == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(userCart, HttpStatus.OK);
        } catch (IllegalArgumentException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}