package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UserTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getName() {
        User testUser = setupFoodtype();
        assertEquals("dessert", testUser.getName());
    }

    @Test
    public void setName() {
        User testUser = setupFoodtype();
        testUser.setName("breakfast");
        assertNotEquals("dessert", testUser.getName());
    }

    @Test
    public void setId() {
        User testUser = setupFoodtype();
        testUser.setId(5);
        assertEquals(5, testUser.getId());
    }

    // helper
    public User setupFoodtype(){
        return new User("dessert");
    }
}