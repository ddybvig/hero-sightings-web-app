/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.data.HeroDaoDB.HeroMapper;
import com.sg.masteryddwa.data.LocationDaoDB.LocationMapper;
import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Location;
import com.sg.masteryddwa.entities.Sighting;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {
            Sighting s = new Sighting();
            s.setId(rs.getInt("id"));
            s.setName(rs.getString("name"));
            s.setDescription(rs.getString("description"));
            s.setDateOfSighting(rs.getDate("dateOfSighting").toLocalDate());
            return s;
        }

    }

    @Override
    @Transactional
    public Sighting addSighting(Sighting sighting) {
        final String INSERT_SIGHTING = "INSERT INTO sighting(name, description, dateOfSighting, locationId) VALUES(?,?,?,?)";
        jdbc.update(INSERT_SIGHTING,
                sighting.getName(),
                sighting.getDescription(),
                Date.valueOf(sighting.getDateOfSighting()),
                sighting.getLocation().getId());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        sighting.setId(newId);
        return sighting;
    }
    
    private Location addLocationToSighting(Sighting sighting) {
        final String ADD_LOCATION_TO_SIGHTING = "SELECT l.* from location l "
                + "JOIN sighting s on l.id = s.locationId WHERE s.id = ?";
        return jdbc.queryForObject(ADD_LOCATION_TO_SIGHTING, new LocationMapper(), sighting.getId());
    }

    @Override
    public Sighting getSightingById(int id) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM sighting WHERE id = ?";
            Sighting s = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), id);
            s.setLocation(addLocationToSighting(s));
            return s;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
    }
    
    @Override
    public void updateSighting(Sighting sighting) {
        final String UPDATE_SIGHTING = "UPDATE sighting SET name = ?, description = ?, dateOfSighting = ?, locationId = ? WHERE id = ?";
        jdbc.update(UPDATE_SIGHTING,
                sighting.getName(),
                sighting.getDescription(),
                Date.valueOf(sighting.getDateOfSighting()),
                sighting.getLocation().getId(),
                sighting.getId());
    }

    @Override
    @Transactional
    public void deleteSighting(int id) {
        //delete from hero_sighting
        final String DELETE_HERO_SIGHTING_BY_SIGHTING = "DELETE hs.* FROM hero_sighting hs "
                + "JOIN sighting s ON hs.sightingId = s.id WHERE s.id = ?";
        jdbc.update(DELETE_HERO_SIGHTING_BY_SIGHTING, id);
        //delete from sighting
        final String DELETE_SIGHTING = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_SIGHTING, id);
    }

    @Override
    public List<Sighting> getSightingsByDate(LocalDate date) {
        final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM sighting s where s.dateOfSighting = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date);
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
    }
    
    @Override
    public List<Sighting> getTenMostRecent() {
        final String SELECT_TEN_MOST_RECENT_SIGHTINGS = "SELECT * FROM sighting ORDER BY dateOfSighting DESC LIMIT 10";
        List<Sighting> sightings = jdbc.query(SELECT_TEN_MOST_RECENT_SIGHTINGS, new SightingMapper());
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
    }
    
    

}
