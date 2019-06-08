package models.dao;

import models.Department;
import models.Staff;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2OStaffDaoTest {
    private Sql2OStaffDao foodtypeDao;
    private Sql2ODepartmentDao restaurantDao;
    private Connection conn;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        restaurantDao = new Sql2ODepartmentDao(sql2o);
        foodtypeDao = new Sql2OStaffDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        Staff testStaff = setupNewFoodtype();
        int originalFoodtypeId = testStaff.getId();
        foodtypeDao.add(testStaff);
        assertNotEquals(originalFoodtypeId, testStaff.getId());
    }

    @Test
    public void addedFoodtypesAreReturnedFromGetAll() throws Exception {
        Staff testfoodtype = setupNewFoodtype();
        foodtypeDao.add(testfoodtype);
        assertEquals(1, foodtypeDao.getAll().size());
    }

    @Test
    public void noFoodtypesReturnsEmptyList() throws Exception {
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void deleteByIdDeletesCorrectFoodtype() throws Exception {
        Staff staff = setupNewFoodtype();
        foodtypeDao.add(staff);
        foodtypeDao.deleteById(staff.getId());
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Staff testStaff = setupNewFoodtype();
        Staff otherStaff = setupNewFoodtype();
        foodtypeDao.clearAll();
        assertEquals(0, foodtypeDao.getAll().size());
    }

    @Test
    public void addFoodTypeToRestaurantAddsTypeCorrectly() throws Exception {

        Department testDepartment = setupRestaurant();
        Department altDepartment = setupAltRestaurant();

        restaurantDao.add(testDepartment);
        restaurantDao.add(altDepartment);

        Staff testStaff = setupNewFoodtype();

        foodtypeDao.add(testStaff);

        foodtypeDao.addFoodtypeToRestaurant(testStaff, testDepartment);
        foodtypeDao.addFoodtypeToRestaurant(testStaff, altDepartment);

        assertEquals(2, foodtypeDao.getAllRestaurantsForAFoodtype(testStaff.getId()).size());
    }

    // helpers

    public Staff setupNewFoodtype(){
        return new Staff("Sushi");
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
