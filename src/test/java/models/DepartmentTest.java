package models;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DepartmentTest {

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }


    @Test
    public void getNameReturnsCorrectName() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("Fish Witch", testDepartment.getName());
    }

    @Test
    public void getAddressReturnsCorrectAddress() throws Exception {
        Department testDepartment = setupRestaurant();
        assertEquals("214 NE Broadway", testDepartment.getDescription());
    }


    public Department setupRestaurant (){
        return new Department("Fish Witch", "214 NE Broadway");
    }

    public Department setupAltRestaurant (){
        return new Department("Fish Witch", "214 NE Broadway");
    }
}