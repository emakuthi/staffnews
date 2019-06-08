package models.dao;

import models.Department;
import models.User;
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
    private Sql2ODepartmentDao departmentDao;
    private Sql2OUserDao userDao;
    private Sql2OArticleDao articleDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        departmentDao = new Sql2ODepartmentDao(sql2o);
        userDao = new Sql2OUserDao(sql2o);
        articleDao = new Sql2OArticleDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        Department testDepartment = setupDepartment();
        assertNotEquals(0, testDepartment.getId());
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
        Department otherDepartment = setupDepartment();
        departmentDao.deleteById(testDepartment.getId());
        assertEquals(1, departmentDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment();
        departmentDao.clearAll();
        assertEquals(0, departmentDao.getAll().size());
    }

    @Test
    public void DepartmentReturnsUsersCorrectly() throws Exception {
        User testUser = new User("Seafood","ek213","engineer");
        userDao.add(testUser);

        User otherUser = new User("Bar Food", "ek167", "principal");
        userDao.add(otherUser);

        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        departmentDao.addDepartmentToUser(testDepartment, testUser);
        departmentDao.addDepartmentToUser(testDepartment, otherUser);

        User[] users = {testUser, otherUser}; //oh hi what is this?

        assertEquals(Arrays.asList(users), departmentDao.getAllUsersByDepartment(testDepartment.getId()));
    }


    //helpers

    public Department setupDepartment (){
        Department department = new Department("Fish Omena", "214 NE Ngara");
        departmentDao.add(department);
        return department;
    }

    public Department setupAltDepartment (){
        Department department = new Department("Fish Omena", "214 NE Ngara");
        departmentDao.add(department);
        return department;
    }
}