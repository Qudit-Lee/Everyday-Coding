package com.project.info;

import java.util.List;

import org.springframework.stereotype.Service;

import com.project.model.Dto;
import com.project.repository.DtoRepository;

@Service
public class InfoService {
	private final DtoRepository dtoRepository; //DtoRepository 클래스의 함수를 사용하기 위해 임폴트
	
	public InfoService(DtoRepository dtoRepository) {
		this.dtoRepository = dtoRepository;
	}
	public List<Dto> getDtoList() {
		return this.dtoRepository.findList();//"dtoRepository.findList()"라는 함수를 Dto 모델의 리스트를 getDtoList()이라는 함수로 지성
	}
}
