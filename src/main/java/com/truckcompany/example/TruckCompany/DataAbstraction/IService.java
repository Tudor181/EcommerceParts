package com.truckcompany.example.TruckCompany.DataAbstraction;

import java.util.List;

public interface IService<T> {
    T get(String id);
    void insert(T item);
    void update(T item);
    void delete(String id);
    List<T> getAll();
}