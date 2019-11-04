/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Organization;
import java.util.List;

/**
 *
 * @author daler
 */
public interface OrganizationDao {
    public Organization addOrganization(Organization organization);
    public Organization getOrganizationById(int id);
    public List<Organization> getAllOrganizations();
    public void updateOrganization(Organization organization);
    public void deleteOrganization(int id);
}
