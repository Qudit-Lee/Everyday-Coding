package com.project.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.project.model.Dto;

public class DtoRowMapper implements RowMapper<Dto> {
	
	@Override
	public Dto mapRow(ResultSet rs, int rowNum) throws SQLException {
		Dto dto = new Dto();
		dto.setTitle("org:"+rs.getString("meta_title"));
		
		dto.setSubjects("org:"+rs.getString("meta_subject1")+", "+rs.getString("meta_subject2")
		+", "+rs.getString("meta_subject3")+", "+"alt: "+rs.getString("meta_subject4"));
		
		dto.setDescription("summary:{"+"org:"+rs.getString("meta_desc1")+"}, {toc:"+rs.getString("meta_desc2")+"}");
		
		dto.setPublisher("org:"+rs.getString("meta_publisher"));
		
		dto.setContributors("role:author,org:"+rs.getString("meta_contributor1")+", affiliation:{org:"+rs.getString("meta_contributor2")+"}");
				
		dto.setDate("registration:"+rs.getString("meta_date1")+", approval:"+rs.getString("meta_date2"));
		
		dto.setLanguage("org:"+"ko"+"");
		
		dto.setIdentifier("id:"+rs.getString("meta_identifier"));
		
		dto.setFormat("media:"+"isHTML");
		
		return dto;
	}
}
