package models.dao;

import models.Department;
import models.User;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2ODepartmentDaoTest {
    private static Connection conn; //these variables are now static.
    private static Sql2ODepartmentDao departmentDao; //these variables are now static.
    private static Sql2OUserDao userDao; //these variables are now static.
    private static Sql2OArticleDao articleDao; //these variables are now static.

    @BeforeClass                //changed to @BeforeClass (run once before running any tests in this file)
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/dept_news_test";  //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "developer", "elvis"); //changed user and pass to null
        departmentDao = new Sql2ODepartmentDao(DB.sql2o);
        userDao = new Sql2OUserDao(DB.sql2o);
        articleDao = new Sql2OArticleDao(DB.sql2o);
        conn = sql2o.open();        //open connection once before this test file is run
    }

    @After              //run after every test
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll(); //clear all departments after every test
        userDao.clearAll(); //clear all departments after every test
        articleDao.clearAll(); //clear all departments after every test
    }

    @AfterClass     //changed to @AfterClass (run once after all tests in this file completed)
    public static void shutDown() throws Exception{ //changed to static
        conn.close(); // close connection once after this entire test file is finished
        System.out.println("connection closed");
    }


    @Test
    public void addingFoodSetsId() throws Exception {
        Department testDepartment = setupDepartment();
        int originalDepartmentId = testDepartment.getId();
        departmentDao.add(testDepartment);
        assertNotEquals(originalDepartmentId, testDepartment.getId());
    }

    @Test
    public void addedDepartmentsAreReturnedFromGetAll() throws Exception {
        Department testDepartment = setupDepartment();
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void noDepartmentsReturnsEmptyList() throws Exception {
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void findByIdReturnsCorrectDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        assertEquals(testDepartment, departmentDao.findById(testDepartment.getId()));
    }

    @Test
    public void updateCorrectlyUpdatesAllFields() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.update(testDepartment.getId(), "a", "b");
        Department foundDepartment = departmentDao.findById(testDepartment.getId());
        assertEquals("a", foundDepartment.getName());
        assertEquals("b", foundDepartment.getDescription());

    }

    @Test
    public void deleteByIdDeletesCorrectDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }


    @Test
    public void deleteingDepartmentAlsoUpdatesJoinTable() throws Exception {
        User testUser  = new User("Seafood", "ek1234", "engineer");
        userDao.add(testUser);

        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);

        Department altDepartment = setupAltDepartment();
        departmentDao.add(altDepartment);

        departmentDao.addDepartmentToUser(testDepartment,testUser);
        departmentDao.addDepartmentToUser(altDepartment, testUser);

        departmentDao.deleteById(testDepartment.getId());
        assertEquals(0, departmentDao.getAllUsersByDepartment(testDepartment.getId()).size());
    }


    //helpers

    public Department setupDepartment (){
        Department department = new Department("a", "b");
        departmentDao.add(department);
        return department;
    }

    public Department setupAltDepartment (){
        Department department = new Department("b", "214 NE Ngara");
        departmentDao.add(department);
        return department;
    }
}