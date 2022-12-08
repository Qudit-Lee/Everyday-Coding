package com.project.info;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.model.Dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class InfoController {
	private InfoService infoService;
	
	@GetMapping("/info")
	public String projectInfo() {
		return "null";
	}
	
	@Autowired  // spring 4.3 버전 이상부터는 생략 가능
	public InfoController(InfoService infoService) {
		this.infoService = infoService;
	}
	@GetMapping("/list")
	public Object cityList() {
		log.debug("/cityList start");
		List<Dto> cityList = infoService.getCityList();
		return cityList;
	}
}

