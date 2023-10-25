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

    public void insert(Category category) {
        categoryRepository.save(category);
    }

    public void update(Category category) {
        categoryRepository.save(category);
    }

    public void delete(String id) {
        categoryRepository.deleteById(new ObjectId(id));
    }

    public List<Category> getAll() {
        return categoryRepository.findAll();
    }
}