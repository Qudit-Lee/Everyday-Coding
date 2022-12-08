package com.project.info;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.model.Dto;
import com.project.repository.DtoRepository;

@Service
public class InfoService {
	private final DtoRepository dtoRepository;
	
	public InfoService(DtoRepository cityRepository) {
		this.dtoRepository = cityRepository;
	}
	public List<Dto> getCityList() {
		return this.dtoRepository.findList();
	}
}
