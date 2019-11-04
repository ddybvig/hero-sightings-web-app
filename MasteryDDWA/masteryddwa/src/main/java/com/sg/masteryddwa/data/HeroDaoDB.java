/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.data.LocationDaoDB.LocationMapper;
import com.sg.masteryddwa.data.OrganizationDaoDB.OrganizationMapper;
import com.sg.masteryddwa.data.SightingDaoDB.SightingMapper;
import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Location;
import com.sg.masteryddwa.entities.Organization;
import com.sg.masteryddwa.entities.Sighting;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author daler
 */
@Repository
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {
            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperpowers(rs.getString("superpowers"));
            return hero;
        }
    }

    private void insertHeroOrganization(Hero hero) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_organization"
                + "(heroId, organizationId) VALUES(?,?)";
        for (Organization o : hero.getOrganizations()) {
            jdbc.update(INSERT_HERO_ORGANIZATION, hero.getId(), o.getId());
        }
    }

    private void insertHeroSighting(Hero hero) {
        final String INSERT_HERO_SIGHTING = "INSERT INTO hero_sighting"
                + "(heroId, sightingId) VALUES(?,?)";
        for (Sighting s : hero.getSightings()) {
            jdbc.update(INSERT_HERO_SIGHTING, hero.getId(), s.getId());
        }
    }

    private List<Organization> getOrganizationsForHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_FOR_HERO = "SELECT o.* FROM organization o "
                + "JOIN hero_organization ho ON o.id = ho.organizationId WHERE ho.heroId = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_HERO, new OrganizationMapper(), hero.getId());
    }

    private List<Sighting> getSightingsForHero(Hero hero) {
        final String SELECT_SIGHTINGS_FOR_HERO = "SELECT s.* FROM sighting s "
                + "JOIN hero_sighting hs ON s.id = hs.sightingId WHERE hs.heroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_HERO, new SightingMapper(), hero.getId());
        for (Sighting s : sightings) {
            s.setLocation(getLocationForHeroSighting(s));
        }
        return sightings;
    }

    private Location getLocationForHeroSighting(Sighting s) {
        final String SELECT_LOCATION_FOR_HERO_SIGHTING = "SELECT l.* FROM location l "
                + "JOIN sighting s on s.locationId = l.id WHERE s.id = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_HERO_SIGHTING, new LocationMapper(), s.getId());
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {
        final String INSERT_HERO = "INSERT INTO hero(name, description, superpowers) VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpowers());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);

        insertHeroOrganization(hero);
        insertHeroSighting(hero);

        return hero;
    }

    @Override
    public Hero getHeroById(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);
            hero.setOrganizations(getOrganizationsForHero(hero));
            hero.setSightings(getSightingsForHero(hero));
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Hero> getAllHeroes() {
        final String SELECT_ALL_HEROES = "SELECT * FROM hero";
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES, new HeroMapper());
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero "
                + "SET name = ?, description = ?, superpowers = ? WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperpowers(),
                hero.getId());

        final String DELETE_HERO_ORGANIZATION = "DELETE FROM hero_organization WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, hero.getId());
        insertHeroOrganization(hero);
        final String DELETE_HERO_SIGHTING = "DELETE FROM hero_sighting WHERE heroId = ?";
        jdbc.update(DELETE_HERO_SIGHTING, hero.getId());
        insertHeroSighting(hero);
    }

    @Override
    @Transactional
    public void deleteHero(int id) {
        //delete from hero_organization
        final String DELETE_HERO_ORGANIZATION_BY_HERO = "DELETE ho.* FROM hero_organization ho "
                + "JOIN hero h ON ho.heroId = h.id WHERE h.id = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION_BY_HERO, id);
        //delete from hero_sighting
        final String DELETE_HERO_SIGHTING_BY_HERO = "DELETE hs.* FROM hero_sighting hs "
                + "JOIN hero h on hs.heroId = h.id WHERE h.id = ?";
        jdbc.update(DELETE_HERO_SIGHTING_BY_HERO, id);
        //delete from hero
        final String DELETE_HERO = "DELETE FROM hero WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    //all heroes at a particular location
    //put in location detail link
    //DONE - in locationDetail.html and locationController
    @Override
    public List<Hero> getHeroesByLocationId(int id) {
        final String GET_HEROES_BY_LOCATION = "SELECT h.* FROM hero h "
                + "JOIN hero_sighting hs on h.id = hs.heroId "
                + "JOIN sighting s on hs.sightingId = s.id "
                + "JOIN location l on s.locationId = l.id WHERE l.id = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_BY_LOCATION, new HeroMapper(), id);
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
    }

    //all locations for a hero
    //put in heroes detail link
    @Override
    public List<Location> getAllLocationsForHero(int id) {
        final String GET_LOCATIONS_FOR_HERO = "SELECT l.* FROM location l "
                + "JOIN sighting s on s.locationId = l.id "
                + "JOIN hero_sighting hs on hs.sightingId = s.id "
                + "JOIN hero h on hs.heroId = h.id WHERE h.id = ?";
        return jdbc.query(GET_LOCATIONS_FOR_HERO, new LocationMapper(), id);
    }

    //all heroes in a given organization
    //front end done for this one -- in organizationController and orgDetail.html
    @Override
    public List<Hero> getAllHeroesForOrganization(int id) {
        final String GET_HEROES_FOR_ORGANIZATION = "SELECT h.* FROM hero h "
                + "JOIN hero_organization ho ON h.id = ho.heroId "
                + "JOIN organization o on ho.organizationId = o.id WHERE o.id = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FOR_ORGANIZATION, new HeroMapper(), id);
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
    }

    //all orgs a hero is in
    //put in heroes detail link
    //actually probably don't even need this!!!
    @Override
    public List<Organization> getAllOrganizationsForHero(int id) {
        final String GET_ORGANIZATIONS_FOR_HERO = "SELECT o.* FROM organization o "
                + "JOIN hero_organization ho on o.id = ho.organizationId "
                + "JOIN hero h on ho.heroId = h.id WHERE h.id = ?";
        return jdbc.query(GET_ORGANIZATIONS_FOR_HERO, new OrganizationMapper(), id);
    }
    
    //front end done -- in sightingDetail.html and sightingController
    @Override
    public List<Hero> getAllHeroesForSighting(int id) {
        final String SELECT_ALL_HEROES_FOR_SIGHTING = "SELECT * FROM hero h "
                + "JOIN hero_sighting hs on h.id = hs.heroId WHERE hs.sightingId = ?";
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES_FOR_SIGHTING, new HeroMapper(), id);
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
    }

}
