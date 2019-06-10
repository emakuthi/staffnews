package models.dao;

import models.Department;
import models.User;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2OUserDaoTest {
    private static Connection conn;
    private static Sql2ODepartmentDao departmentDao;
    private static Sql2OUserDao userDao;
    private static Sql2OArticleDao articleDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/dept_news_test";  //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "developer", "elvis"); //changed user and pass to null
        departmentDao = new Sql2ODepartmentDao(DB.sql2o);
        userDao = new Sql2OUserDao(DB.sql2o);
        articleDao = new Sql2OArticleDao(DB.sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        departmentDao.clearAll();
        articleDao.clearAll();
        userDao.clearAll();
        System.out.println("clearing database");
    }

    @AfterClass
    public static void shutDown() throws Exception{ //changed to static
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingFoodSetsId() throws Exception {
        User testUser = setupNewUser();
        int originalUserId = testUser.getId();
        userDao.add(testUser);
        assertNotEquals(originalUserId,testUser.getId());
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
    public void deletingDepartmentAlsoUpdatesJoinTable() throws Exception {
        User testUser  = new User("Seafood","Ek200","technician");
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

    @Test
    public void deletingUserAlsoUpdatesJoinTable() throws Exception {

        Department testDepartment = setupDepartment();

        departmentDao.add(testDepartment);

        User testUser = setupNewUser();
        User otherUser = new User("Japanese", "EK0006", "engineer");

        userDao.add(testUser);
        userDao.add(otherUser);

        userDao.addUserToDepartment(testUser, testDepartment);
        userDao.addUserToDepartment(otherUser,testDepartment);

        userDao.deleteById(testDepartment.getId());
        assertEquals(0, userDao.getAllDepartmentsForAUser(testUser.getId()).size());
    }

    // helpers

    public User setupNewUser(){
        return new User("Sushi", "ek200", "engineer");
    }

    public Department setupDepartment (){
        return new Department("Regional operations", "for telecoms deployment");
    }

    public Department setupAltDepartment (){
        return new Department("Digital IT", "For it operations");
    }
}
