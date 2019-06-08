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
        Article testArticle = setupReview();
        assertEquals("Great service", testArticle.getContent());
    }

    @Test
    public void setContent() {
        Article testArticle = setupReview();
        testArticle.setContent("No free dessert :(");
        assertNotEquals("Great service", testArticle.getContent());
    }

    @Test
    public void getWrittenBy() {
        Article testArticle = setupReview();
        assertEquals("Kim", testArticle.getWrittenBy());
    }

    @Test
    public void setWrittenBy() {
        Article testArticle = setupReview();
        testArticle.setWrittenBy("Mike");
        assertNotEquals("Kim", testArticle.getWrittenBy());
    }

    @Test
    public void getRating() {
        Article testArticle = setupReview();
        assertEquals(4, testArticle.getRating());
    }

    @Test
    public void setRating() {
        Article testArticle = setupReview();
        testArticle.setRating(1);
        assertNotEquals(4, testArticle.getRating());
    }

    @Test
    public void getRestaurantId() {
        Article testArticle = setupReview();
        assertEquals(1, testArticle.getRestaurantId());
    }

    @Test
    public void setRestaurantId() {
        Article testArticle = setupReview();
        testArticle.setRestaurantId(10);
        assertNotEquals(1, testArticle.getRestaurantId());
    }

    @Test
    public void setId() {
        Article testArticle = setupReview();
        testArticle.setId(5);
        assertEquals(5, testArticle.getId());
    }

    // helper
    public Article setupReview (){
        return new Article("Great service", "Kim", 4, 1);
    }
}