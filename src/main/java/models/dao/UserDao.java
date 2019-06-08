package models.dao;

import models.User;
import models.Department;

import java.util.List;

public interface UserDao {
    //create
    void add(User user);
    void addUserToDepartment(User user, Department department);

    //read
    List<User> getAll();
    List<Department> getAllDepartmentsForAUser(int id);
    User findById(int id);

    //delete
    void deleteById(int id);
    void clearAll();
}

