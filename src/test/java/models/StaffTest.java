package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StaffTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getName() {
        Staff testStaff = setupFoodtype();
        assertEquals("dessert", testStaff.getName());
    }

    @Test
    public void setName() {
        Staff testStaff = setupFoodtype();
        testStaff.setName("breakfast");
        assertNotEquals("dessert", testStaff.getName());
    }

    @Test
    public void setId() {
        Staff testStaff = setupFoodtype();
        testStaff.setId(5);
        assertEquals(5, testStaff.getId());
    }

    // helper
    public Staff setupFoodtype(){
        return new Staff("dessert");
    }
}