package models.dao;

import models.Staff;
import models.Department;

import java.util.List;

public interface StaffDao {
    //create
    void add(Staff staff);
    void addFoodtypeToRestaurant(Staff staff, Department department);

    //read
    List<Staff> getAll();
    List<Department> getAllRestaurantsForAFoodtype(int id);
    Staff findById(int id);

    //update
    //omit for now

    //delete
    void deleteById(int id);
    void clearAll();
}

