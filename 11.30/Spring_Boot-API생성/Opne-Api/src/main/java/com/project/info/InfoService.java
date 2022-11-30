package com.project.info;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.project.info.model.City;
import com.project.info.model.Project;
import com.project.info.repository.CityRepository;

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
}
