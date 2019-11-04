/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Location;
import com.sg.masteryddwa.entities.Organization;
import com.sg.masteryddwa.entities.Sighting;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author daler
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SightingDaoDBTest {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    public SightingDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting s : sightings) {
            sightingDao.deleteSighting(s.getId());
        }
        List<Location> locations = locDao.getAllLocations();
        for (Location l : locations) {
            locDao.deleteLocation(l.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        Sighting fromDao = sightingDao.getSightingById(testSighting.getId());
        assertEquals(testSighting, fromDao);
    }

    /**
     * Test of getAllSightings method, of class SightingDaoDB.
     */
    @Test
    public void testGetAllSightings() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);
        List<Sighting> sightings = sightingDao.getAllSightings();
        assertEquals(2, sightings.size());
        assertTrue(sightings.contains(testSighting));
        assertTrue(sightings.contains(testSighting2));
    }

    /**
     * Test of updateSighting method, of class SightingDaoDB.
     */
    @Test
    public void testUpdateSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        testSighting.setName("EDITED");
        sightingDao.updateSighting(testSighting);
        Sighting edited = sightingDao.getSightingById(testSighting.getId());
        assertEquals("EDITED", edited.getName());
    }

    /**
     * Test of deleteSighting method, of class SightingDaoDB.
     */
    @Test
    public void testDeleteSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        sightingDao.deleteSighting(testSighting.getId());
        Sighting fromDao = sightingDao.getSightingById(testSighting.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getSightingsByDate method, of class SightingDaoDB.
     */
    @Test
    public void testGetSightingsByDate() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);
        List<Sighting> sightingsForDate = sightingDao.getSightingsByDate(LocalDate.EPOCH);
        assertEquals(2, sightingsForDate.size());
        assertTrue(sightingsForDate.contains(testSighting));
        assertTrue(sightingsForDate.contains(testSighting2));
    }
    
    @Test
    public void testGetTenMostRecent() {
        //location to reuse
        Location testLoc = new Location();
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("MN");
        testLoc.setZip("55555");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locDao.addLocation(testLoc);
        //first sighting
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.parse("2000-01-01"));
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        //second sighting
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.now());
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);
        //third
        Sighting testSighting3 = new Sighting();
        testSighting3.setName("testName3");
        testSighting3.setDescription("testDescription3");
        testSighting3.setDateOfSighting(LocalDate.now());
        testSighting3.setLocation(testLoc);
        testSighting3 = sightingDao.addSighting(testSighting3);
        //4
        Sighting testSighting4 = new Sighting();
        testSighting4.setName("testName4");
        testSighting4.setDescription("testDescription4");
        testSighting4.setDateOfSighting(LocalDate.now());
        testSighting4.setLocation(testLoc);
        testSighting4 = sightingDao.addSighting(testSighting4);
        //5
        Sighting testSighting5 = new Sighting();
        testSighting5.setName("testName5");
        testSighting5.setDescription("testDescription5");
        testSighting5.setDateOfSighting(LocalDate.now());
        testSighting5.setLocation(testLoc);
        testSighting5 = sightingDao.addSighting(testSighting5);
        //6
        Sighting testSighting6 = new Sighting();
        testSighting6.setName("testName6");
        testSighting6.setDescription("testDescription6");
        testSighting6.setDateOfSighting(LocalDate.now());
        testSighting6.setLocation(testLoc);
        testSighting6 = sightingDao.addSighting(testSighting6);
        //7
        Sighting testSighting7 = new Sighting();
        testSighting7.setName("testName7");
        testSighting7.setDescription("testDescription7");
        testSighting7.setDateOfSighting(LocalDate.now());
        testSighting7.setLocation(testLoc);
        testSighting7 = sightingDao.addSighting(testSighting7);
        //8
        Sighting testSighting8 = new Sighting();
        testSighting8.setName("testName8");
        testSighting8.setDescription("testDescription8");
        testSighting8.setDateOfSighting(LocalDate.now());
        testSighting8.setLocation(testLoc);
        testSighting8 = sightingDao.addSighting(testSighting8);
        //9
        Sighting testSighting9 = new Sighting();
        testSighting9.setName("testName9");
        testSighting9.setDescription("testDescription9");
        testSighting9.setDateOfSighting(LocalDate.now());
        testSighting9.setLocation(testLoc);
        testSighting9 = sightingDao.addSighting(testSighting9);
        //10
        Sighting testSighting10 = new Sighting();
        testSighting10.setName("testName10");
        testSighting10.setDescription("testDescription10");
        testSighting10.setDateOfSighting(LocalDate.now());
        testSighting10.setLocation(testLoc);
        testSighting10 = sightingDao.addSighting(testSighting10);
        //11
        Sighting testSighting11 = new Sighting();
        testSighting11.setName("testName11");
        testSighting11.setDescription("testDescription11");
        testSighting11.setDateOfSighting(LocalDate.EPOCH);
        testSighting11.setLocation(testLoc);
        testSighting11 = sightingDao.addSighting(testSighting11);
        
        List<Sighting> tenMostRecent = sightingDao.getTenMostRecent();
        
        assertEquals(10, tenMostRecent.size());
        assertTrue(tenMostRecent.contains(testSighting));
        assertFalse(tenMostRecent.contains(testSighting11));
    }

}
