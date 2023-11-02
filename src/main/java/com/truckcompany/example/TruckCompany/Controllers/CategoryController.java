package com.truckcompany.example.TruckCompany.Controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.truckcompany.example.TruckCompany.DataAbstraction.ICategoryService;
import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Requests.Category.CategoryPost;

@RestController
@RequestMapping("/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(ICategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> get(@PathVariable String id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            ObjectId objectId = new ObjectId(id);
            Category category = categoryService.get(objectId.toHexString());
            if (category == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Void> insert(@RequestBody CategoryPost request) {
        try {
            if (request == null || request.name == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            Category category = new Category(request.name);
            categoryService.insert(category);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody Category category) {
        try {
            if (category == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            categoryService.update(category);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        try {
            if (id == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            categoryService.delete(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAll() {
        try {
            List<Category> categories = categoryService.getAll();
            if (categories == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
/**
 * CategoryController is a REST controller that handles HTTP requests related to Category entities.
 * It uses the ICategoryService to perform operations on the Category entities.
 * 
 * The class has the following methods:
 * 
 * - get(String id): Retrieves a Category by its id. Returns 404 if the Category is not found, 400 if the id is not a valid ObjectId, and 200 along with the Category if it is found.
 * 
 * - insert(CategoryPost request): Inserts a new Category. The request body should contain the details of the Category to be inserted. Returns 201 if the insertion is successful, and 400 if the request is not valid.
 * 
 * - update(Category category): Updates an existing Category. The request body should contain the Category to be updated, including its id. Returns 204 if the update is successful, and 400 if the request is not valid.
 * 
 * - delete(String id): Deletes a Category by its id. Returns 204 if the deletion is successful, and 400 if the id is not a valid ObjectId.
 * 
 * - getAll(): Retrieves all Categories. Returns 200 along with the list of Categories.
 */