package com.project.info;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.info.model.City;
import com.project.info.model.Project;
import com.project.info.repository.CityRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class InfoService {
	
	private final CityRepository cityRepository;
	
	
	public InfoService(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}
	
	public Project getProjectInfo() {
		
		Project project= new Project();
		project.projectName= "open-api";
		project.author= "Hello";
		project.createdDate= new Date();
		
		return project;
	}
	
	public List<City> getCityList() {
		return this.cityRepository.findList();
	}

	// select 기능
	public List<City> findCityByCodeAndPopulation(String countryCode, int population) {
		log.debug("countryCode = {}, population = {}", countryCode, population);
		return this.cityRepository.findByCountryCodeAndPopulation(countryCode, population);
	}
	
	// insert 기능
	public City insert(City city) {
		return this.cityRepository.insert(city);
	}
	
	// update 기능
	public Integer updateById(City city) {
		log.debug("city id = {}", city.getId());
		return cityRepository.updateById(city);
	}
	
	// delete 기능
	public Integer deleteById(Integer id) {
		log.debug("city id = {}", id);
		return cityRepository.deleteById(id);
	}
}