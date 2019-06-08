package models.dao;

import models.Department;
import models.Foodtype;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);
    void addRestaurantToFoodtype(Department department, Foodtype foodtype);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<Foodtype> getAllFoodtypesByRestaurant(int restaurantId);

    //update
    void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    //delete
    void deleteById(int id);
    void clearAll();
}
