package models.dao;

import models.Foodtype;
import models.Department;

import java.util.List;

public interface FoodtypeDao {
    //create
    void add(Foodtype foodtype);
    void addFoodtypeToRestaurant(Foodtype foodtype, Department department);

    //read
    List<Foodtype> getAll();
    List<Department> getAllRestaurantsForAFoodtype(int id);
    Foodtype findById(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

