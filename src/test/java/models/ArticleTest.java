package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ArticleTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getContent() {
        Article testArticle = setupArticle();
        assertEquals("Great service", testArticle.getContent());
    }

    @Test
    public void setContent() {
        Article testArticle = setupArticle();
        testArticle.setContent("No free dessert :(");
        assertNotEquals("Great service", testArticle.getContent());
    }

    
    @Test
    public void getDepartmentId() {
        Article testArticle = setupArticle();
        assertEquals(4, testArticle.getDepartmentId());
    }

    @Test
    public void setDepartmentId() {
        Article testArticle = setupArticle();
        testArticle.setDepartmentId(10);
        assertNotEquals(1, testArticle.getDepartmentId());
    }

    @Test
    public void setId() {
        Article testArticle = setupArticle();
        testArticle.setId(5);
        assertEquals(5, testArticle.getId());
    }

    // helper
    public Article setupArticle (){
        return new Article("Great service", 4);
    }
}