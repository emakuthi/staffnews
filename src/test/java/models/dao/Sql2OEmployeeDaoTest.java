package models.dao;

import models.Department;
import models.Employee;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2OEmployeeDaoTest {
    private Sql2oFoodtypeDao foodtypeDao;
    private Sql2ODepartmentDao restaurantDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2ODepartmentDao(sql2o);
        foodtypeDao = new Sql2oFoodtypeDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        Employee testEmployee = setupNewFoodtype();
        int originalFoodtypeId = testEmployee.getId();
        foodtypeDao.add(testEmployee);
        assertNotEquals(originalFoodtypeId, testEmployee.getId());
    }

    @Test
    public void addedFoodtypesAreReturnedFromGetAll() throws Exception {
        Employee testfoodtype = setupNewFoodtype();
        foodtypeDao.add(testfoodtype);
        assertEquals(1, foodtypeDao.getAll().size());
    }

    @Test
    public void noFoodtypesReturnsEmptyList() throws Exception {
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectFoodtype() throws Exception {
        Employee employee = setupNewFoodtype();
        foodtypeDao.add(employee);
        foodtypeDao.deleteById(employee.getId());
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Employee testEmployee = setupNewFoodtype();
        Employee otherEmployee = setupNewFoodtype();
        foodtypeDao.clearAll();
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void addFoodTypeToRestaurantAddsTypeCorrectly() throws Exception {

        Department testDepartment = setupRestaurant();
        Department altDepartment = setupAltRestaurant();

        restaurantDao.add(testDepartment);
        restaurantDao.add(altDepartment);

        Employee testEmployee = setupNewFoodtype();

        foodtypeDao.add(testEmployee);

        foodtypeDao.addFoodtypeToRestaurant(testEmployee, testDepartment);
        foodtypeDao.addFoodtypeToRestaurant(testEmployee, altDepartment);

        assertEquals(2, foodtypeDao.getAllRestaurantsForAFoodtype(testEmployee.getId()).size());
    }

    // helpers

    public Employee setupNewFoodtype(){
        return new Employee("Sushi");
    }

    public Department setupRestaurant (){
        Department department = new Department("Fish Omena", "214 NE Safaricom", "97232", "254-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(department);
        return department;
    }

    public Department setupAltRestaurant (){
        Department department = new Department("Fish Omena", "214 NE Safaricom", "97232", "254-402-9874");
        restaurantDao.add(department);
        return department;
    }
}
