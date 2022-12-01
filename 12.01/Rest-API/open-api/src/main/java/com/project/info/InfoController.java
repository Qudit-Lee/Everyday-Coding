package com.project.info;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.project.info.model.City;
import com.project.info.model.Project;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {

	private InfoService infoService;
	
	@Autowired
	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	
	
	@GetMapping("project")
	public Object projectInfo() {
		log.debug("/info start");
		Project project = infoService.getProjectInfo();
		return project;
	}
    
	@GetMapping("custom")
	public String customJson() {
		JsonObject jo = new JsonObject();
		
		jo.addProperty("projectName", "preword");
		jo.addProperty("author", "hello-bryan");
		jo.addProperty("createdDate", new Date().toString());
		
		JsonArray ja = new JsonArray();
		for(int i=0; i<5; i++) {
			JsonObject jObj = new JsonObject();
			jObj.addProperty("prop"+i, i);
			ja.add(jObj);
		}
		
		jo.add("follower", ja);
		
		return jo.toString();
	}
	
	@GetMapping("cityList")
	public Object cityList() {
		log.debug("/cityList start");
		List<City> cityList = infoService.getCityList();
		return cityList;
	}
	
	// select 기능
	@GetMapping("cityListByCode")
	public Object cityByCountryCode(@RequestParam("countryCode") String ctCode
			, @RequestParam(value="population", required = false, defaultValue = "0") int population) {
		log.debug("countryCode = {}, population = {}", ctCode, population);
		List<City> cityList = infoService.findCityByCodeAndPopulation(ctCode, population);
		return cityList;
	}
	
	// insert 기능
	@PostMapping(value="cityAdd")
	public ResponseEntity<City> cityAdd(@RequestBody City city) {
		try {
			log.debug("city = {}", city.toString());
			return new ResponseEntity<>(infoService.insert(city), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// update 기능
	@PostMapping(value="cityEdit")
	public ResponseEntity<String> cityEdit(@RequestBody City city) {
		try {
			log.debug("city = {}", city.toString());
			Integer updatedCnt = infoService.updateById(city);
			return new ResponseEntity<>(String.format("%d updated", updatedCnt), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	// delete 기능
	@ResponseBody
	@PostMapping(value="cityDelete")
	public ResponseEntity<String> cityDelete(@RequestParam(value="id") Integer id) {
		try {
			log.debug("city id = {}", id);
			Integer deletedCnt = infoService.deleteById(id);
			return new ResponseEntity<>(String.format("%d deleted", deletedCnt), HttpStatus.OK);
		}catch (Exception e) {
			log.error(e.toString());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
