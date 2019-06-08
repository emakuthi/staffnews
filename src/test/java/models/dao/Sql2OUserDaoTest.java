package models.dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2OUserDaoTest {
    private Sql2OUserDao userDao;
    private Sql2ODepartmentDao restaurantDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2ODepartmentDao(sql2o);
        userDao = new Sql2OUserDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        User testUser = setupNewUser();
        int originalUserId = testUser.getId();
        userDao.add(testUser);
        assertNotEquals(originalUserId, testUser.getId());
    }

    @Test
    public void addedUsersAreReturnedFromGetAll() throws Exception {
        User testuser = setupNewUser();
        userDao.add(testuser);
        assertEquals(1, userDao.getAll().size());
    }

    @Test
    public void noUsersReturnsEmptyList() throws Exception {
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectUser() throws Exception {
        User user = setupNewUser();
        userDao.add(user);
        userDao.deleteById(user.getId());
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        User testUser = setupNewUser();
        User otherUser = setupNewUser();
        userDao.clearAll();
        assertEquals(0, userDao.getAll().size());
    }

    @Test
    public void addFoodTypeToDepartmentAddsTypeCorrectly() throws Exception {

        Department testDepartment = setupDepartment();
        Department altDepartment = setupAltDepartment();

        restaurantDao.add(testDepartment);
        restaurantDao.add(altDepartment);

        User testUser = setupNewUser();

        userDao.add(testUser);

        userDao.addUserToDepartment(testUser, testDepartment);
        userDao.addUserToDepartment(testUser, altDepartment);

        assertEquals(2, userDao.getAllDepartmentsForAUser(testUser.getId()).size());
    }

    // helpers

    public User setupNewUser(){
        return new User("Sushi","ek129", "enginee");
    }

    public Department setupDepartment (){
        Department department = new Department("Fish Omena", "214 NE Safaricom");
        restaurantDao.add(department);
        return department;
    }

    public Department setupAltDepartment (){
        Department department = new Department("Fish Omena", "214 NE Safaricom");
        restaurantDao.add(department);
        return department;
    }
}
