/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Location;
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
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location l = new Location();
            l.setId(rs.getInt("id"));
            l.setAddress(rs.getString("address"));
            l.setCity(rs.getString("city"));
            l.setState(rs.getString("state"));
            l.setZip(rs.getString("zip"));
            l.setLatitude(rs.getBigDecimal("latitude"));
            l.setLongitude(rs.getBigDecimal("longitude"));
            return l;
        }

    }

    @Override
    @Transactional
    public Location addLocation(Location location) {
        final String INSERT_LOCATION = "INSERT INTO location(address, city, state, zip, latitude, longitude) VALUES(?,?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            return jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {
        final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
        return jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());
    }

    @Override
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET address = ?, city = ?, state = ?, zip = ?, latitude = ?, longitude = ? WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getAddress(),
                location.getCity(),
                location.getState(),
                location.getZip(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId());
        //any need to do something to associated sighting entries?
    }

    @Override
    @Transactional
    public void deleteLocation(int id) {
        //delete from hero sighting
        final String DELETE_HERO_SIGHTING_BY_SIGHTING = "DELETE hs.* FROM hero_sighting hs "
                + "JOIN sighting s ON hs.sightingId = s.id WHERE s.locationId = ?";
        jdbc.update(DELETE_HERO_SIGHTING_BY_SIGHTING, id);
        //delete from sighting
        final String DELETE_SIGHTING_BY_LOCATION = "DELETE s.* FROM sighting s "
                + "WHERE s.locationId = ?"; //realized i didn't really need a join here
        jdbc.update(DELETE_SIGHTING_BY_LOCATION, id);
        //delete from location
        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    //this is basically a duplicate of a method i put in heroDao
    @Override
    public List<Location> getLocationsByHeroId(int id) {
        final String SELECT_LOCATIONS_BY_HERO = "SELECT l.* FROM location l "
                + "JOIN sighting s on s.locationId = l.id "
                + "JOIN hero_sighting hs on s.id = hs.sightingId WHERE heroId = ?";
        return jdbc.query(SELECT_LOCATIONS_BY_HERO, new LocationMapper(), id);
    }

}
