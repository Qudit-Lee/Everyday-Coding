package com.project.info;

import java.util.Date;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
//import com.mysql.cj.log.Log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.info.model.City;
import com.project.info.model.Project;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("info")
public class InfoController {
	
	@Autowired
	private InfoService infoService;
	
	@GetMapping("/project")
	public Object projectInfo() {
		log.debug("/info start");
		
		Project project= infoService.getProjectInfo();
		
		return project;
		}
	
		@GetMapping("/custom")
		public String customJson() {
			JsonObject jo = new JsonObject();
			
			jo.addProperty("projectName", "open-api");
			jo.addProperty("author", "hello");
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
		
		@GetMapping("/cityList")
		public Object cityList() {
			//log.debug("/cityList start");
			List<City> cityList = infoService.getCityList();
			return cityList;
		}
	
	}


