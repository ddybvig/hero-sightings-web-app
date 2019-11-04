/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.controllers;

import com.sg.masteryddwa.data.LocationDao;
import com.sg.masteryddwa.data.SightingDao;
import com.sg.masteryddwa.entities.Location;
import com.sg.masteryddwa.entities.Sighting;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author daler
 */
@Controller
public class MainController {
    
    @Autowired
    LocationDao locDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("/")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getTenMostRecent();
        List<Location> locations = locDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        return "index";
    }
    
}
