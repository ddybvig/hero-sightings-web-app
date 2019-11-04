/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Sighting;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author daler
 */
public interface SightingDao {
    public Sighting addSighting(Sighting sighting);
    public Sighting getSightingById(int id);
    public List<Sighting> getAllSightings();
    public void updateSighting(Sighting sighting);
    public void deleteSighting(int id);
    public List<Sighting> getSightingsByDate(LocalDate date);
    public List<Sighting> getTenMostRecent();
   
}
