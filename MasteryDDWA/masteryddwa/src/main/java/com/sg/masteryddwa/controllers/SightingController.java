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
import com.sg.masteryddwa.entities.Sighting;
import java.time.LocalDate;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author daler
 */
@Controller
public class SightingController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    LocalDate dateOfSighting;

    @GetMapping("sightings")
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Location> locations = locDao.getAllLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        Sighting blankSighting = new Sighting();
        model.addAttribute("sighting", blankSighting);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) { 
//binding result has to be directly after the object to work...that's 20 minutes i'll never get back
        String locationId = request.getParameter("locationId");
        Location location = locDao.getLocationById(Integer.parseInt(locationId));
        if (location != null) {
            sighting.setLocation(location);
        } else {
            FieldError error = new FieldError("sighting", "location", "Please select a location.");
            result.addError(error);
        }
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("locations", locDao.getAllLocations());
            return "sightings";
        }
        sightingDao.addSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSighting(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        List<Location> locations = locDao.getAllLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        Location location = locDao.getLocationById(Integer.parseInt(locationId));
        if (location != null) {
            sighting.setLocation(location);
        } else {
            FieldError error = new FieldError("sighting", "location", "Please select a location.");
            result.addError(error);
        }
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("locations", locDao.getAllLocations());
            return "editsighting";
        }
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetail")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        List<Hero> heroes = heroDao.getAllHeroesForSighting(id);
        model.addAttribute("heroes", heroes);
        return "sightingDetail";
    }

    @PostMapping("findSightingsByDate")
    public String findSightingsByDate(HttpServletRequest request) {
        LocalDate dateInput = LocalDate.parse(request.getParameter("dateOfSighting"));
        dateOfSighting = dateInput;
        return "redirect:/sightingsForDate";
    }

    @GetMapping("sightingsForDate")
    public String sightingsForDate(Model model) {
        model.addAttribute("dateOfSighting", dateOfSighting);
        List<Sighting> sightingsForDate = sightingDao.getSightingsByDate(dateOfSighting);
        model.addAttribute("sightings", sightingsForDate);
        return "sightingsForDate";
    }

}
