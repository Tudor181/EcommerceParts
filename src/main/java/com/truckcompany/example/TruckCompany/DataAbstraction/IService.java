package com.truckcompany.example.TruckCompany.DataAbstraction;

import java.util.List;

public interface IService<T> {
    T get(String id);
    Boolean insert(T item);
    Boolean update(T item);
    Boolean delete(String id);
    List<T> getAll();
}