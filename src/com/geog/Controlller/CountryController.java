package com.geog.Controlller;

import java.sql.*;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.bean.*;
import javax.naming.*;
import javax.sql.*;

import com.geog.DAO.CountryDAO;
import com.geog.Model.Country;

@ManagedBean
@SessionScoped
public class CountryController {
	//@EJB
	//The country data source Object
	private CountryDAO dao;
	//The full list of countries
	private List<Country> countryList;
	
	@PostConstruct
	/**
	 * Initilalization of the Country DOA and pre load the country list
	 */
	public void init() {
		System.out.println("Loaded");
		//Initialize the country dao
		this.dao = new CountryDAO();
		//Load the country list
		this.countryList=this.dao.list();
	}
	/**
	 * Method used to aquire the full list of countries
	 * @return List<Country> - Full list of countries
	 */
	public List<Country> getCountryList() {
		return this.countryList;
	}
}
