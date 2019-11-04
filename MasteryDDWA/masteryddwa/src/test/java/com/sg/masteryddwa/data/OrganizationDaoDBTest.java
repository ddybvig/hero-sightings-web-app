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
public class OrganizationDaoDBTest {
    
    @Autowired
    OrganizationDao orgDao;
    
    @Autowired
    LocationDao locDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @Autowired
    HeroDao heroDao;
    
    public OrganizationDaoDBTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization o : orgs) {
            orgDao.deleteOrganization(o.getId()); 
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetOrganization() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("MN");
        testOrg.setZip("55555");
        testOrg.setPhone("555-555-5555");
        testOrg = orgDao.addOrganization(testOrg);
        Organization fromDao = orgDao.getOrganizationById(testOrg.getId());
        assertEquals(testOrg, fromDao);
        
    }

    /**
     * Test of getAllOrganizations method, of class OrganizationDaoDB.
     */
    @Test
    public void testGetAllOrganizations() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("MN");
        testOrg.setZip("55555");
        testOrg.setPhone("555-555-5555");
        testOrg = orgDao.addOrganization(testOrg);
        Organization testOrg2 = new Organization();
        testOrg2.setName("testName2");
        testOrg2.setDescription("testDescription2");
        testOrg2.setAddress("testAddress2");
        testOrg2.setCity("testCity2");
        testOrg2.setState("MN");
        testOrg2.setZip("55555");
        testOrg2.setPhone("555-555-5555");
        testOrg2 = orgDao.addOrganization(testOrg2);
        List<Organization> orgs = orgDao.getAllOrganizations();
        assertEquals(2, orgs.size());
        assertTrue(orgs.contains(testOrg));
        assertTrue(orgs.contains(testOrg2));
    }

    /**
     * Test of updateOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testUpdateOrganization() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("MN");
        testOrg.setZip("55555");
        testOrg.setPhone("555-555-5555");
        testOrg = orgDao.addOrganization(testOrg);
        testOrg.setName("EDITED");
        orgDao.updateOrganization(testOrg);
        Organization edited = orgDao.getOrganizationById(testOrg.getId());
        assertEquals("EDITED", edited.getName());
    }

    /**
     * Test of deleteOrganization method, of class OrganizationDaoDB.
     */
    @Test
    public void testDeleteOrganization() {
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
        testOrg = orgDao.addOrganization(testOrg);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
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
        testSighting = sightingDao.addSighting(testSighting);
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(testSighting);
        testHero.setSightings(sightings);

        testHero = heroDao.addHero(testHero);
        
        orgDao.deleteOrganization(testOrg.getId());
        //test the deletion of the hero_org bridge table entry
        testHero = heroDao.getHeroById(testHero.getId());
        assertTrue(testHero.getOrganizations().isEmpty());
        //test the deletion of the org itself
        Organization fromDao = orgDao.getOrganizationById(testOrg.getId());
        assertNull(fromDao);
    }
    
}
