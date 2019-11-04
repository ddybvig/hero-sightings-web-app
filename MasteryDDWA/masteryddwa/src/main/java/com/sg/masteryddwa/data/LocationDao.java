/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Location;
import java.util.List;

/**
 *
 * @author daler
 */
public interface LocationDao {
    public Location addLocation(Location location);
    public Location getLocationById(int id);
    public List<Location> getAllLocations();
    public void updateLocation(Location location);
    public void deleteLocation(int id);
    public List<Location> getLocationsByHeroId(int id);
}
