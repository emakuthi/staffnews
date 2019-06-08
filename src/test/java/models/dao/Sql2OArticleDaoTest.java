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
    private Sql2OArticleDao articleDao;
    private Sql2ODepartmentDao departmentDao;

    @Before
    public void setUp() throws Exception {
        String connectionString = "jdbc:h2:mem:testing;INIT=RUNSCRIPT from 'classpath:db/create.sql'";
        Sql2o sql2o = new Sql2o(connectionString, "", "");
        articleDao = new Sql2OArticleDao(sql2o);
        departmentDao = new Sql2ODepartmentDao(sql2o);
        conn = sql2o.open();
    }

    @After
    public void tearDown() throws Exception {
        conn.close();
    }

    @Test
    public void addingArticleSetsId() throws Exception {
        Article testArticle = setupArticle();
        assertEquals(1, testArticle.getId());
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
        Department otherDepartment = setupDepartment();
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

    //helpers

    public Article setupArticle() {
        Article article = new Article("great", 1);
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
