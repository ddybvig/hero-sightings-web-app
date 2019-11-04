/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Hero;
import com.sg.masteryddwa.entities.Location;
import com.sg.masteryddwa.entities.Organization;
import java.util.List;

/**
 *
 * @author daler
 */
public interface HeroDao {
    public Hero addHero(Hero hero);
    public Hero getHeroById(int id);
    public List<Hero> getAllHeroes();
    public void updateHero(Hero hero);
    public void deleteHero(int id);
    public List<Hero> getHeroesByLocationId(int id);
    public List<Location> getAllLocationsForHero(int id);
    public List<Hero> getAllHeroesForOrganization(int id);
    public List<Organization> getAllOrganizationsForHero(int id);
    public List<Hero> getAllHeroesForSighting(int id);
}
