package com.project.repository;

import java.util.List;

//import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.EmptySqlParameterSource;
//import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.project.model.Dto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class DtoRepository {
	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	private final DtoRowMapper dtoRowMapper;
	
	public DtoRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
		this.dtoRowMapper= new DtoRowMapper();
	}
	
	public List<Dto> findList(){
		log.debug("findList query= {}", DtoSql.SELECT);
		
		
		return namedParameterJdbcTemplate.query(DtoSql.SELECT, EmptySqlParameterSource.INSTANCE
				, this.dtoRowMapper);
	}
}
