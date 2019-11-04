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
public class LocationDaoDBTest {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    public LocationDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Location> locs = locDao.getAllLocations();
        for (Location l : locs) {
            locDao.deleteLocation(l.getId());
        }

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetLocation() {
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
        Location fromDao = locDao.getLocationById(testLoc.getId());
        assertEquals(testLoc, fromDao);
    }

    /**
     * Test of getAllLocations method, of class LocationDaoDB.
     */
    @Test
    public void testGetAllLocations() {
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
        Location testLoc2 = new Location();
        testLoc2.setAddress("testAddress2");
        testLoc2.setCity("testCity2");
        testLoc2.setState("MN");
        testLoc2.setZip("55555");
        testLoc2.setLatitude(testLatitude);
        testLoc2.setLongitude(testLongitude);
        testLoc2 = locDao.addLocation(testLoc2);
        List<Location> locs = locDao.getAllLocations();
        assertEquals(2, locs.size());
        assertTrue(locs.contains(testLoc));
        assertTrue(locs.contains(testLoc2));
    }

    /**
     * Test of updateLocation method, of class LocationDaoDB.
     */
    @Test
    public void testUpdateLocation() {
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
        testLoc.setAddress("EDITED");
        locDao.updateLocation(testLoc);
        Location edited = locDao.getLocationById(testLoc.getId());
        assertEquals("EDITED", edited.getAddress());
    }

    /**
     * Test of deleteLocation method, of class LocationDaoDB.
     */
    @Test
    public void testDeleteLocation() {
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
        //create a sighting to go with this location to test the delete more thoroughly
        Sighting s = new Sighting();
        s.setName("testName");
        s.setDescription("testDescription");
        s.setDateOfSighting(LocalDate.EPOCH);
        s.setLocation(testLoc);
        s = sightingDao.addSighting(s);
        //create a hero with this sighting to check that bridge table delete
        Hero h = new Hero();
        h.setName("testHero");
        h.setSuperpowers("testPowers");
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(s);
        h.setSightings(sightings);
        Organization o = new Organization();
        o.setName("testOrg");
        o = orgDao.addOrganization(o);
        List<Organization> orgs = new ArrayList<>();
        orgs.add(o);
        h.setOrganizations(orgs);
        h = heroDao.addHero(h);
        locDao.deleteLocation(testLoc.getId());
        s = sightingDao.getSightingById(s.getId());
        h = heroDao.getHeroById(h.getId());
        //check that the hero_sighting bridge table entry is cleared
        assertTrue(h.getSightings().isEmpty());
        //this next line shows that the sighting s no longer exists in the sighting table. this is the middle step of the delete
        assertNull(s);
        //finally check that the location itself is null
        Location fromDao = locDao.getLocationById(testLoc.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getLocationsByHeroId method, of class LocationDaoDB.
     */
    @Test
    public void testGetLocationsByHeroId() {
        Hero testHero = new Hero();
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpowers("testPowers");
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("MN");
        testOrg.setZip("55555");
        testOrg.setPhone("555-555-5555");
        orgDao.addOrganization(testOrg);
        List<Organization> orgs = orgDao.getAllOrganizations();
        testHero.setOrganizations(orgs);
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
        sightingDao.addSighting(testSighting);
        List<Sighting> sightings = sightingDao.getAllSightings();
        testHero.setSightings(sightings);
        testHero = heroDao.addHero(testHero);
        List<Location> locationsForHero = locDao.getLocationsByHeroId(testHero.getId());
        assertTrue(locationsForHero.contains(testLoc));
    }

}
