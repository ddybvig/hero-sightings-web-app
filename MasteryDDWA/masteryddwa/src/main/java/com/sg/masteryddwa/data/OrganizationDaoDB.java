/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.masteryddwa.data;

import com.sg.masteryddwa.entities.Organization;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization o = new Organization();
            o.setId(rs.getInt("id"));
            o.setName(rs.getString("name"));
            o.setDescription(rs.getString("description"));
            o.setAddress(rs.getString("address"));
            o.setCity(rs.getString("city"));
            o.setState(rs.getString("state"));
            o.setZip(rs.getString("zip"));
            o.setPhone(rs.getString("phone"));
            return o;
        }

    }

    @Override
    @Transactional
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name, description, address, city, state, zip, phone) VALUES(?,?,?,?,?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getCity(),
                organization.getState(),
                organization.getZip(),
                organization.getPhone());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            return jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, address = ?, "
                + "city = ?, state = ?, zip = ?, phone = ? WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getCity(),
                organization.getState(),
                organization.getZip(),
                organization.getPhone(),
                organization.getId());
        //need to do anything with the bridge table?
    }
   
    @Override
    @Transactional
    public void deleteOrganization(int id) {
        //delete from hero_organization
        final String DELETE_HERO_ORGANIZATION_BY_ORGANIZATION = "DELETE ho.* FROM hero_organization ho "
                + "JOIN organization o ON ho.organizationId = o.id WHERE ho.organizationId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION_BY_ORGANIZATION, id);
        //delete from organization
        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

}
