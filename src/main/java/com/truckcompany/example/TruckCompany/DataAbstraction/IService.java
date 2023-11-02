package com.truckcompany.example.TruckCompany.DataAbstraction;

import java.util.List;

public interface IService<T> {
    T get(String id) throws MyException;
    Boolean insert(T item) throws MyException;
    Boolean update(T item) throws MyException;
    Boolean delete(String id) throws MyException;
    List<T> getAll() throws MyException;
}