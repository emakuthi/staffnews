package models.dao;

import models.Department;
import models.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2ODepartmentDaoTest {
    private Connection conn;
    private Sql2ODepartmentDao restaurantDao;
    private Sql2oFoodtypeDao foodtypeDao;
    private Sql2oReviewDao reviewDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2ODepartmentDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        reviewDao = new Sql2oReviewDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        Department testDepartment = setupRestaurant();
        assertNotEquals(0, testDepartment.getId());
    }

    @Test
    public void addedRestaurantsAreReturnedFromGetAll() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void noRestaurantsReturnsEmptyList() throws Exception {
        assertEquals(0, restaurantDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectRestaurant() throws Exception {
        Department testDepartment = setupRestaurant();
        Department otherDepartment = setupRestaurant();
        assertEquals(testDepartment, restaurantDao.findById(testDepartment.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Department testDepartment = setupRestaurant();
        restaurantDao.update(testDepartment.getId(), "a", "b", "c", "d", "e", "f");
        Department foundDepartment = restaurantDao.findById(testDepartment.getId());
        assertEquals("a", foundDepartment.getName());
        assertEquals("b", foundDepartment.getAddress());
        assertEquals("c", foundDepartment.getZipcode());
        assertEquals("d", foundDepartment.getPhone());
        assertEquals("e", foundDepartment.getWebsite());
        assertEquals("f", foundDepartment.getEmail());
    }

    @Test
    public void deleteByIdDeletesCorrectRestaurant() throws Exception {
        Department testDepartment = setupRestaurant();
        Department otherDepartment = setupRestaurant();
        restaurantDao.deleteById(testDepartment.getId());
        assertEquals(1, restaurantDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupRestaurant();
        Department otherDepartment = setupRestaurant();
        restaurantDao.clearAll();
        assertEquals(0, restaurantDao.getAll().size());
    }

    @Test
    public void RestaurantReturnsFoodtypesCorrectly() throws Exception {
        Employee testEmployee = new Employee("Seafood");
        foodtypeDao.add(testEmployee);

        Employee otherEmployee = new Employee("Bar Food");
        foodtypeDao.add(otherEmployee);

        Department testDepartment = setupRestaurant();
        restaurantDao.add(testDepartment);
        restaurantDao.addRestaurantToFoodtype(testDepartment, testEmployee);
        restaurantDao.addRestaurantToFoodtype(testDepartment, otherEmployee);

        Employee[] employees = {testEmployee, otherEmployee}; //oh hi what is this?

        assertEquals(Arrays.asList(employees), restaurantDao.getAllFoodtypesByRestaurant(testDepartment.getId()));
    }


    //helpers

    public Department setupRestaurant (){
        Department department = new Department("Fish Omena", "214 NE Ngara", "97232", "254-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(department);
        return department;
    }

    public Department setupAltRestaurant (){
        Department department = new Department("Fish Omena", "214 NE Ngara", "97232", "254-402-9874");
        restaurantDao.add(department);
        return department;
    }
}