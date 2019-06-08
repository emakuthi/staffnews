package models.dao;

import models.Employee;
import models.Department;

import java.util.List;

public interface FoodtypeDao {
    //create
    void add(Employee employee);
    void addFoodtypeToRestaurant(Employee employee, Department department);

    //read
    List<Employee> getAll();
    List<Department> getAllRestaurantsForAFoodtype(int id);
    Employee findById(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

