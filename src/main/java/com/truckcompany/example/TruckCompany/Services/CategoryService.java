package com.truckcompany.example.TruckCompany.Services;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.truckcompany.example.TruckCompany.DataAbstraction.ICategoryService;
import com.truckcompany.example.TruckCompany.Domain.Category;
import com.truckcompany.example.TruckCompany.Repositories.ICategoryRepository;


@Service
public class CategoryService implements ICategoryService{
    private final ICategoryRepository categoryRepository;

    @Autowired
    public CategoryService(ICategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public Category get(String id) {
        Optional<Category> optionalCategory = categoryRepository.findById(new ObjectId(id));
        return optionalCategory.orElse(null);
    }

    public Boolean insert(Category category) {
        if(category.getName().length() < 1)
            return false;
        return categoryRepository.save(category).getId().length()>1;
    }

    public Boolean update(Category category) {
        if(category.getName().length() < 1)
            return false;
        Category categoryToUpdate = this.categoryRepository.findById(new ObjectId(category.getId())).orElse(null);
        if(categoryToUpdate == null)
            return false;

        categoryToUpdate.setName(category.getName());
        categoryRepository.save(category);
        return categoryToUpdate.equals(category);
    }

    public Boolean delete(String id) {
        Category category = this.categoryRepository.findById(new ObjectId(id)).orElse(null);
        if(category == null)
            return false;
        categoryRepository.deleteById(new ObjectId(id));
        return true;
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}

/**
 * CategoryService is a service class that provides methods for managing categories.
 * It implements the ICategoryService interface.
 * 
 * The class has the following fields:
 * 
 * - categoryRepository: The repository for accessing the Category data.
 * 
 * The class has the following methods:
 * 
 * - CategoryService(ICategoryRepository categoryRepository): Constructor that initializes the categoryRepository field.
 * 
 * - get(String id): Returns the Category with the given ID. If no such Category exists, it returns null.
 * 
 * - insert(Category category): Inserts a new Category. If the name of the Category is less than 1 character long, it returns false. Otherwise, it returns true if the Category was inserted successfully.
 * 
 * - update(Category category): Updates an existing Category. If the name of the Category is less than 1 character long or if no Category with the given ID exists, it returns false. Otherwise, it updates the Category and returns true if the update was successful.
 * 
 * - delete(String id): Deletes the Category with the given ID. If no Category with the given ID exists, it returns false. Otherwise, it deletes the Category and returns true.
 * 
 * - getAll(): Returns a list of all Categories.
 */