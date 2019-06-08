package models.dao;

import models.Department;
import models.Staff;

import java.util.List;

public interface DepartmentDao {
    //create
    void add (Department department);
    void addRestaurantToFoodtype(Department department, Staff staff);

    //read
    List<Department> getAll();
    Department findById(int id);
    List<Staff> getAllFoodtypesByRestaurant(int restaurantId);

    //update
    void update(int id, String name, String address, String zipcode, String phone, String website, String email);

    //delete
    void deleteById(int id);
    void clearAll();
}
