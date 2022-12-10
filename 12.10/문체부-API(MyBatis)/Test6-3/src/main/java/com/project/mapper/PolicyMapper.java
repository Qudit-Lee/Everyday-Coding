package com.project.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.project.model.Policy;

@Repository
@Mapper
public interface PolicyMapper {
	List<Policy> getPolicy();
}
