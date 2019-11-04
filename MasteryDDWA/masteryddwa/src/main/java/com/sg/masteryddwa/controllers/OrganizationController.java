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
import com.sg.masteryddwa.entities.Organization;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
public class OrganizationController {

    @Autowired
    HeroDao heroDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    LocationDao locDao;

    @Autowired
    SightingDao sightingDao;

    @GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = orgDao.getAllOrganizations();
        model.addAttribute("organizations", orgs);
        Organization blankOrg = new Organization();
        model.addAttribute("organization", blankOrg);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Organization> orgs = orgDao.getAllOrganizations();
            model.addAttribute("organizations", orgs);
            return "organizations";
        }
        orgDao.addOrganization(organization);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDao.deleteOrganization(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("organization", org);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, BindingResult result) {
        if (result.hasErrors()) {
            return "editOrganization";
        }
        orgDao.updateOrganization(org);
        return "redirect:/organizations";
    }

    @GetMapping("organizationDetail")
    public String organizationDetail(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("organization", org);
        List<Hero> heroesForOrg = heroDao.getAllHeroesForOrganization(id);
        model.addAttribute("heroes", heroesForOrg);
        return "organizationDetail";
    }

}
