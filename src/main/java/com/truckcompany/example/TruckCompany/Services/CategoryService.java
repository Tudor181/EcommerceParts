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