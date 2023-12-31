package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truckcompany.example.TruckCompany.DataAbstraction.IUserCartService;
import com.truckcompany.example.TruckCompany.DataAbstraction.IUserService;
import com.truckcompany.example.TruckCompany.DataAbstraction.MyException;
import com.truckcompany.example.TruckCompany.Domain.TruckPartInventory;
import com.truckcompany.example.TruckCompany.Domain.User;
import com.truckcompany.example.TruckCompany.Domain.UserCart;
import com.truckcompany.example.TruckCompany.Requests.UserLoginRequest;

import java.util.Date;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;

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
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("register/")
    public ResponseEntity<Void> insert(@RequestBody User user) {
        try {
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            userService.insert(user);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("DeleteCart/{userId}")
    public ResponseEntity<Boolean> deleteUserCart(@PathVariable String userId) {
        try {
            boolean response = this.userCartService.deleteUserCart(userId);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable String id, @RequestBody User user) {

        try {
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ObjectId objectId = new ObjectId(id);
            User existingUser = userService.get(objectId.toHexString());
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            user.setId(objectId.toHexString());
            userService.update(user);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> generateToken(@RequestBody UserLoginRequest request) {
        try {
            User user = userService.getByEmail(request.getUserEmail(), request.getUserPassword());
            if (user == null) {
                return ResponseEntity.badRequest().build();
            }

            Algorithm algorithm = Algorithm.HMAC256("secret");
            String token = JWT.create()
                    .withIssuer("auth0")
                    .withClaim("userId", user.getId())
                    .withClaim("username", user.getEmail())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 3600 * 1000)) // 1 hour
                    .sign(algorithm);
            return ResponseEntity.ok(token);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (JWTCreationException e) {
            return ResponseEntity.internalServerError().build();
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            ObjectId objectId = new ObjectId(id);
            User existingUser = userService.get(objectId.toHexString());
            if (existingUser == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.delete(objectId.toHexString());
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getAll() {
        try {
            List<User> users = userService.getAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (MyException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/AddToCart/{userId}/{itemId}")
    public ResponseEntity<Boolean> addToCart(@PathVariable String userId, @PathVariable String itemId) {
        try {
            UserCart userCart = this.userCartService.addItemToUserCart(userId, itemId);
            if (userCart == null) {
                throw new MyException("Failed to add item to cart", "ADD_TO_CART_FAILED");
            }
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (MyException ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/GetCart/{userId}")
    public ResponseEntity<List<TruckPartInventory>> getCart(@PathVariable String userId) {
        try {
            List<TruckPartInventory> userCart = this.userCartService.getCartByUserId(userId);
            return new ResponseEntity<>(userCart, HttpStatus.OK);
        } catch (MyException ex) {
                return new ResponseEntity<>(null, HttpStatus.OK);

        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

/**
 * UserController is a REST controller that handles HTTP requests related to
 * User entities.
 * It uses the IUserService and IUserCartService to perform operations on the
 * User entities and their carts.
 * 
 * The class has the following methods:
 * 
 * - get(String id): Retrieves a User by its id. Returns 404 if the User is not
 * found, 400 if the id is not a valid ObjectId, and 200 along with the User if
 * it is found.
 * 
 * - insert(User user): Inserts a new User. The request body should contain the
 * details of the User to be inserted. Returns 201 if the insertion is
 * successful, and throws a MyException if the User is null.
 * 
 * - update(String id, User user): Updates an existing User. The request body
 * should contain the User to be updated, including its id. Returns 204 if the
 * update is successful, and throws a MyException if the User is null or not
 * found.
 * 
 * - delete(String id): Deletes a User by its id. Returns 204 if the deletion is
 * successful, and throws a MyException if the User is not found.
 * 
 * - getAll(): Retrieves all Users. Returns 200 along with the list of Users.
 * 
 * - generateToken(String UserEmail, String UserPassword): Generates a JWT token
 * for a User. Returns the token if the generation is successful, and 400 if the
 * User is not found or the token generation fails.
 * 
 * - addToCart(String userId, String itemId): Adds an item to a User's cart.
 * Returns 200 if the addition is successful, and 400 or 500 if it fails.
 * 
 * - getCart(String userId): Retrieves a User's cart. Returns 200 along with the
 * cart if the retrieval is successful, and 400 or 500 if it fails.
 */