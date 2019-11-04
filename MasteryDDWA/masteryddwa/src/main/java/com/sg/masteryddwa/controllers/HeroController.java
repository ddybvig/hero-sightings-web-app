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
import com.sg.masteryddwa.entities.Organization;
import com.sg.masteryddwa.entities.Sighting;
import java.util.ArrayList;
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
public class HeroController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("heroes")
    public String displayHeroes(Model model) {
        List<Hero> heroes = heroDao.getAllHeroes();
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Organization> orgs = orgDao.getAllOrganizations();
        model.addAttribute("heroes", heroes);
        model.addAttribute("sightings", sightings);
        model.addAttribute("organizations", orgs);
        Hero blankHero = new Hero();
        model.addAttribute("hero", blankHero);
        return "heroes";
    }

    @PostMapping("addHero")
    public String addHero(@Valid Hero hero, BindingResult result, HttpServletRequest request, Model model) {
        String[] sightingIds = request.getParameterValues("sightingId");
        String[] organizationIds = request.getParameterValues("organizationId");
        //add and set sightings
        List<Sighting> sightings = new ArrayList<>();
        if (sightingIds != null) {
            for (String sightingId : sightingIds) {
                sightings.add(sightingDao.getSightingById(Integer.parseInt(sightingId)));
            }
        } else {
            FieldError error = new FieldError("hero", "sightings", "Please choose at least one sighting. If your hero has no sightings, choose 'No Sightings Yet'.");
            result.addError(error);
        }
        hero.setSightings(sightings);
        //add organizations
        List<Organization> orgs = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                orgs.add(orgDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("hero", "organizations", "Please choose at least one organization. If your hero has no organizations, choose 'No Organizations Yet'.");
            result.addError(error);
        }
        hero.setOrganizations(orgs);
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("organizations", orgDao.getAllOrganizations());
            model.addAttribute("heroes", heroDao.getAllHeroes());
            model.addAttribute("hero", hero);
            return "heroes";
        }
        heroDao.addHero(hero);
        return "redirect:/heroes";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        heroDao.deleteHero(id);

        return "redirect:/heroes";
    }

    @GetMapping("editHero")
    public String editHero(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = heroDao.getHeroById(id);
        List<Sighting> sightings = sightingDao.getAllSightings();
        List<Organization> orgs = orgDao.getAllOrganizations();
        model.addAttribute("hero", hero);
        model.addAttribute("sightings", sightings);
        model.addAttribute("organizations", orgs);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, HttpServletRequest request, Model model) {
        String[] organizationIds = request.getParameterValues("organizationId");
        String[] sightingIds = request.getParameterValues("sightingId");
        List<Sighting> sightings = new ArrayList<>();
        if (sightingIds != null) {
            for (String sightingId : sightingIds) {
                sightings.add(sightingDao.getSightingById(Integer.parseInt(sightingId)));
            }
        } else {
            FieldError error = new FieldError("hero", "sightings", "Please choose at least one sighting. If your hero has no sightings, choose 'No Sightings Yet'.");
            result.addError(error);
        }
        hero.setSightings(sightings);
        //add organizations
        List<Organization> orgs = new ArrayList<>();
        if (organizationIds != null) {
            for (String organizationId : organizationIds) {
                orgs.add(orgDao.getOrganizationById(Integer.parseInt(organizationId)));
            }
        } else {
            FieldError error = new FieldError("hero", "organizations", "Please choose at least one organization. If your hero has no organizations, choose 'No Organizations Yet'.");
            result.addError(error);
        }
        hero.setOrganizations(orgs);
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getAllSightings());
            model.addAttribute("organizations", orgDao.getAllOrganizations());
            model.addAttribute("hero", hero);
            return "editHero";
        }
        heroDao.updateHero(hero);

        return "redirect:/heroes";
    }

    @GetMapping("heroDetail")
    public String heroDetail(Integer id, Model model) {
        Hero hero = heroDao.getHeroById(id);
        model.addAttribute("hero", hero);
        List<Location> locationsForHero = heroDao.getAllLocationsForHero(id);
        model.addAttribute("locations", locationsForHero);
        return "heroDetail";
    }
}
