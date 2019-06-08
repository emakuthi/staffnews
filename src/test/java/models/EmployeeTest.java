package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EmployeeTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }
    @Test
    public void getName() {
        Employee testEmployee = setupFoodtype();
        assertEquals("dessert", testEmployee.getName());
    }

    @Test
    public void setName() {
        Employee testEmployee = setupFoodtype();
        testEmployee.setName("breakfast");
        assertNotEquals("dessert", testEmployee.getName());
    }

    @Test
    public void setId() {
        Employee testEmployee = setupFoodtype();
        testEmployee.setId(5);
        assertEquals(5, testEmployee.getId());
    }

    // helper
    public Employee setupFoodtype(){
        return new Employee("dessert");
    }
}