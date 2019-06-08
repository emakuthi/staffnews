package models.dao;

import models.Article;
import models.Department;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.sql2o.Connection;
import org.sql2o.Sql2o;

import static org.junit.Assert.assertEquals;

public class Sql2OArticleDaoTest {
    private Connection conn;
    private Sql2oReviewDao reviewDao;
    private Sql2ODepartmentDao restaurantDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        reviewDao = new Sql2oReviewDao(sql2o);
        restaurantDao = new Sql2ODepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingReviewSetsId() throws Exception {
        Article testArticle = setupReview();
        assertEquals(1, testArticle.getId());
    }

    @Test
    public void getAll() throws Exception {
        Article article1 = setupReview();
        Article article2 = setupReview();
        assertEquals(2, reviewDao.getAll().size());
    }

    @Test
    public void getAllReviewsByRestaurant() throws Exception {
        Department testDepartment = setupRestaurant();
        Department otherDepartment = setupRestaurant(); //add in some extra data to see if it interferes
        Article article1 = setupReviewForRestaurant(testDepartment);
        Article article2 = setupReviewForRestaurant(testDepartment);
        Article articleForOtherRestaurant = setupReviewForRestaurant(otherDepartment);
        assertEquals(2, reviewDao.getAllReviewsByRestaurant(testDepartment.getId()).size());
    }

    @Test
    public void deleteById() throws Exception {
        Article testArticle = setupReview();
        Article otherArticle = setupReview();
        assertEquals(2, reviewDao.getAll().size());
        reviewDao.deleteById(testArticle.getId());
        assertEquals(1, reviewDao.getAll().size());
    }

    @Test
    public void clearAll() throws Exception {
        Article testArticle = setupReview();
        Article otherArticle = setupReview();
        reviewDao.clearAll();
        assertEquals(0, reviewDao.getAll().size());
    }

    //helpers

    public Article setupReview() {
        Article article = new Article("great", "Kim", 4, 555);
        reviewDao.add(article);
        return article;
    }

    public Article setupReviewForRestaurant(Department department) {
        Article article = new Article("great", "Kim", 4, department.getId());
        reviewDao.add(article);
        return article;
    }

    public Department setupRestaurant() {
        Department department = new Department("Fish Witch", "214 NE Broadway", "97232", "503-402-9874", "http://fishwitch.com", "hellofishy@fishwitch.com");
        restaurantDao.add(department);
        return department;
    }
}
