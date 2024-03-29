package models.dao;

import models.Article;
import models.Department;
import org.junit.*;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Sql2OArticleDaoTest {
    private static Connection conn; //these variables are now static.
    private static Sql2ODepartmentDao departmentDao; //these variables are now static.
    private static Sql2OUserDao userDao; //these variables are now static.
    private static Sql2OArticleDao articleDao; //these variables are now static.

    @BeforeClass
    public static void setUp() throws Exception {
        String connectionString = "jdbc:postgresql://localhost:5432/dept_news_test";  //connect to postgres test database
        Sql2o sql2o = new Sql2o(connectionString, "developer", "elvis");
        departmentDao = new Sql2ODepartmentDao(DB.sql2o);
        userDao = new Sql2OUserDao(DB.sql2o);
        articleDao = new Sql2OArticleDao(DB.sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("clearing database");
        departmentDao.clearAll(); //clear all departments after every test
        userDao.clearAll(); //clear all departments after every test
        articleDao.clearAll();
    }

    @AfterClass
    public static void shutDown() throws Exception{ //changed to static
        conn.close();
        System.out.println("connection closed");
    }

    @Test
    public void addingArticleSetsId() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        Article testArticle = new Article("Captain Kirk", 3);
        int originalArticleId = testArticle.getId();
        articleDao.add(testArticle);
        assertNotEquals(originalArticleId,testArticle.getId());
    }

    @Test
    public void getAll() throws Exception {
        Article article1 = setupArticle();
        Article article2 = setupArticle();
        assertEquals(2, articleDao.getAll().size());
    }

    @Test
    public void getAllArticlesByDepartment() throws Exception {
        Department testDepartment = setupDepartment();
        Department otherDepartment = setupDepartment(); //add in some extra data to see if it interferes
        Article article1 = setupArticleForDepartment(testDepartment);
        Article article2 = setupArticleForDepartment(testDepartment);
        Article articleForOtherDepartment = setupArticleForDepartment(otherDepartment);
        assertEquals(2, articleDao.getAllArticlesByDepartment(testDepartment.getId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        Article testArticle = setupArticle();
        Article otherArticle = setupArticle();
        assertEquals(2, articleDao.getAll().size());
        articleDao.deleteById(testArticle.getId());
        assertEquals(1, articleDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Article testArticle = setupArticle();
        Article otherArticle = setupArticle();
        articleDao.clearAll();
        assertEquals(0, articleDao.getAll().size());
    }

    @Test
    public void timeStampIsReturnedCorrectly() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        Article testArticle = new Article("Captain Kirk",testDepartment.getId());
        articleDao.add(testArticle);

    }

    @Test
    public void articlesAreReturnedInCorrectOrder() throws Exception {
        Department testDepartment = setupDepartment();
        departmentDao.add(testDepartment);
        Article testArticle = new Article("Captain Kirk", testDepartment.getId());
        articleDao.add(testArticle);
        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Article testSecondArticle = new Article("Mr. Spock", testDepartment.getId());
        articleDao.add(testSecondArticle);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Article testThirdArticle = new Article("Scotty", testDepartment.getId());
        articleDao.add(testThirdArticle);

        try {
            Thread.sleep(2000);
        }
        catch (InterruptedException ex){
            ex.printStackTrace();
        }

        Article testFourthArticle = new Article("Mr. Sulu", testDepartment.getId());
        articleDao.add(testFourthArticle);

        assertEquals(4, articleDao.getAllArticlesByDepartment(testDepartment.getId()).size()); //it is important we verify that the list is the same size.

    }

    //helpers

    public Article setupArticle() {
        Article article = new Article("great", 4);
        articleDao.add(article);
        return article;
    }

    public Article setupArticleForDepartment(Department department) {
        Article article = new Article("great", department.getId());
        articleDao.add(article);
        return article;
    }

    public Department setupDepartment() {
        Department department = new Department("Fish Witch", "214 NE Broadway");
        departmentDao.add(department);
        return department;
    }
}
