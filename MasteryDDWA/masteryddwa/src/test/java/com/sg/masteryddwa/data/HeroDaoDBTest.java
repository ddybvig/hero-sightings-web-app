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
public class HeroDaoDBTest {

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @Autowired
    HeroDao heroDao;

    public HeroDaoDBTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        List<Hero> heroes = heroDao.getAllHeroes();
        for (Hero h : heroes) {
            heroDao.deleteHero(h.getId());
        }
        List<Location> locations = locDao.getAllLocations();
        for (Location l : locations) {
            locDao.deleteLocation(l.getId());
        }
        List<Sighting> sightings = sightingDao.getAllSightings();
        for (Sighting s : sightings) {
            sightingDao.deleteSighting(s.getId());
        }
        List<Organization> orgs = orgDao.getAllOrganizations();
        for (Organization o : orgs) {
            orgDao.deleteOrganization(o.getId());
        }
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testAddGetHero() {
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
        Hero fromDao = heroDao.getHeroById(testHero.getId());
        assertEquals(testHero, fromDao);
    }

    /**
     * Test of getAllHeroes method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroes() {
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
        Hero testHero2 = new Hero();

        testHero2.setName("testName2");
        testHero2.setDescription("testDescription2");
        testHero2.setSuperpowers("testSuperpowers2");
        testHero2.setOrganizations(orgs);
        testHero2.setSightings(sightings);

        testHero2 = heroDao.addHero(testHero2);
        List<Hero> heroes = heroDao.getAllHeroes();

        assertEquals(2, heroes.size());
        assertTrue(heroes.contains(testHero));
        assertTrue(heroes.contains(testHero2));
    }

    /**
     * Test of updateHero method, of class HeroDaoDB.
     */
    @Test
    public void testUpdateHero() {
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

        testHero.setName("EDITED");
        Location testLoc2 = new Location();
        testLoc2.setAddress("testAddress2");
        testLoc2.setCity("testCity2");
        testLoc2.setState("MN");
        testLoc2.setZip("55555");
        testLoc2.setLatitude(testLatitude);
        testLoc2.setLongitude(testLongitude);
        testLoc2 = locDao.addLocation(testLoc2);

        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc2);
        testSighting2 = sightingDao.addSighting(testSighting2);
        sightings.add(testSighting2);
        testHero.setSightings(sightings);

        Organization testOrg2 = new Organization();
        testOrg2.setName("testName2");
        testOrg2.setDescription("testDescription2");
        testOrg2.setAddress("testAddress2");
        testOrg2.setCity("testCity2");
        testOrg2.setState("MN");
        testOrg2.setZip("55555");
        testOrg2.setPhone("555-555-5555");
        testOrg2 = orgDao.addOrganization(testOrg2);

        orgs.add(testOrg2);
        testHero.setOrganizations(orgs);
        heroDao.updateHero(testHero);
        Hero edited = heroDao.getHeroById(testHero.getId());
        assertEquals("EDITED", edited.getName());
        //add tests for the size of the orgs and sightings lists?
    }

    /**
     * Test of deleteHero method, of class HeroDaoDB.
     */
    @Test
    public void testDeleteHero() {
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
        heroDao.deleteHero(testHero.getId());
        Hero fromDao = heroDao.getHeroById(testHero.getId());
        assertNull(fromDao);
    }

    /**
     * Test of getHeroesByLocationId method, of class HeroDaoDB.
     */
    @Test
    public void testGetHeroesByLocationId() {
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

        List<Sighting> sightingsForTestHero = new ArrayList<>();
        sightingsForTestHero.add(testSighting);
        testHero.setSightings(sightingsForTestHero);
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("test2");
        testHero2.setDescription("test2");
        testHero2.setSuperpowers("test2");

        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testSighting2");
        testSighting2.setDescription("test");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        //set the same location for this second sighting so the list of all heroes at this location will have both testHero and testHero2
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);

        List<Sighting> sightingsForTestHero2 = new ArrayList<>();
        sightingsForTestHero2.add(testSighting2);
        testHero2.setSightings(sightingsForTestHero2);
        testHero2.setOrganizations(orgs);

        heroDao.addHero(testHero2);

        List<Hero> heroesForLocation = heroDao.getHeroesByLocationId(testLoc.getId());
        assertEquals(2, heroesForLocation.size());
        assertTrue(heroesForLocation.contains(testHero));
        assertTrue(heroesForLocation.contains(testHero2));
    }

    /**
     * Test of getAllLocationsForHero method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllLocationsForHero() {
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
        List<Sighting> sightingsForTestHero = new ArrayList<>();
        sightingsForTestHero.add(testSighting);
        testHero.setSightings(sightingsForTestHero);

        testHero = heroDao.addHero(testHero);

        Location testLoc2 = new Location();
        testLoc2.setAddress("testAddress2");
        testLoc2.setCity("testCity2");
        testLoc2.setState("MN");
        testLoc2.setZip("55555");
        testLoc2.setLatitude(testLatitude);
        testLoc2.setLongitude(testLongitude);
        testLoc2 = locDao.addLocation(testLoc2);

        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc2);
        testSighting2 = sightingDao.addSighting(testSighting2);

        sightingsForTestHero.add(testSighting2);
        testHero.setSightings(sightingsForTestHero);
        //i want to just update the hero to have the additional sighting, but the update method throws a SQL exception.
        //i did not test updating the sightings or organizations in the update test and now i realize i should have lol.
        heroDao.updateHero(testHero);
        List<Location> locationsForTestHero = heroDao.getAllLocationsForHero(testHero.getId());
        assertEquals(2, locationsForTestHero.size());
        assertTrue(locationsForTestHero.contains(testLoc));
        assertTrue(locationsForTestHero.contains(testLoc2));
    }

    /**
     * Test of getAllHeroesForOrganization method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllHeroesForOrganization() {
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

        List<Sighting> sightingsForTestHero = new ArrayList<>();
        sightingsForTestHero.add(testSighting);
        testHero.setSightings(sightingsForTestHero);
        testHero = heroDao.addHero(testHero);

        Hero testHero2 = new Hero();
        testHero2.setName("test2");
        testHero2.setDescription("test2");
        testHero2.setSuperpowers("test2");
        testHero2.setOrganizations(orgs);
        testHero2.setSightings(sightingsForTestHero);
        testHero2 = heroDao.addHero(testHero2);

        List<Hero> heroesForOrg = heroDao.getAllHeroesForOrganization(testOrg.getId());

        assertEquals(2, heroesForOrg.size());
        assertTrue(heroesForOrg.contains(testHero));
        assertTrue(heroesForOrg.contains(testHero2));
    }

    /**
     * Test of getAllOrganizationsForHero method, of class HeroDaoDB.
     */
    @Test
    public void testGetAllOrganizationsForHero() {
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

        Organization testOrg2 = new Organization();
        testOrg2.setName("testName2");
        testOrg2.setDescription("testDescription2");
        testOrg2.setAddress("testAddress2");
        testOrg2.setCity("testCity2");
        testOrg2.setState("MN");
        testOrg2.setZip("55555");
        testOrg2.setPhone("555-555-5555");
        testOrg2 = orgDao.addOrganization(testOrg2);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        orgs.add(testOrg2);
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

        List<Sighting> sightingsForTestHero = new ArrayList<>();
        sightingsForTestHero.add(testSighting);
        testHero.setSightings(sightingsForTestHero);

        testHero = heroDao.addHero(testHero);

        List<Organization> orgsForHero = heroDao.getAllOrganizationsForHero(testHero.getId());

        assertEquals(2, orgsForHero.size());
        assertTrue(orgsForHero.contains(testOrg));
        assertTrue(orgsForHero.contains(testOrg2));
    }

    @Test
    public void testGetAllHeroesForSighting() {
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
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(testSighting);
        //test org
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
        //first hero
        Hero testHero = new Hero();
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpowers("testPowers");
        testHero.setOrganizations(orgs);
        testHero.setSightings(sightings);
        testHero = heroDao.addHero(testHero);
        //second hero
        Hero testHero2 = new Hero();
        testHero2.setName("testName2");
        testHero2.setDescription("testDescription2");
        testHero2.setSuperpowers("testSuperpowers2");
        testHero2.setOrganizations(orgs);
        testHero2.setSightings(sightings);
        testHero2 = heroDao.addHero(testHero2);
        List<Hero> heroesForSighting = heroDao.getAllHeroesForSighting(testSighting.getId());
        assertEquals(2, heroesForSighting.size());
        assertTrue(heroesForSighting.contains(testHero));
        assertTrue(heroesForSighting.contains(testHero2));

    }

}
