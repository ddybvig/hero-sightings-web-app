/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.controllers;

import com.sg.masteryddwa.data.HeroDao;
import com.sg.masteryddwa.data.LocationDao;
import com.sg.masteryddwa.data.OrganizationDao;
import com.sg.masteryddwa.data.SightingDao;
import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Location;
import java.math.BigDecimal;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author daler
 */
@Controller
public class LocationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locs = locDao.getAllLocations();
        model.addAttribute("locations", locs);
        Location blankLocation = new Location();
        model.addAttribute("location", blankLocation);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Location> locs = locDao.getAllLocations();
            model.addAttribute("locations", locs);
            return "locations";
        }
        locDao.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        locDao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locDao.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if (result.hasErrors()) {
            return "editLocation";
        }
        locDao.updateLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String courseDetail(Integer id, Model model) {
        Location location = locDao.getLocationById(id);
        model.addAttribute("location", location);
        List<Hero> heroesForLocation = heroDao.getHeroesByLocationId(id);
        model.addAttribute("heroes", heroesForLocation);
        return "locationDetail";
    }

}
