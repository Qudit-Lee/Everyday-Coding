package com.project.dto;

import lombok.Data;

@Data
public class InfoForm {
	
	public Long id;
	public String title;	
	public String subjects;	
	public String description;	
	public String publisher;	
	public String contributors;	
	public String date;	
	public String language;	
	public String identifier;	
	public String format;
	
	
//	public Info toEntity() {
//		return Info.builder()
//				.id(id)
//				.title(title)
//				.subjects(subjects)
//				.description(description)
//				.publisher(publisher)
//				.contributors(contributors)
//				.date(date)
//				.language(language)
//				.identifier(identifier)
//				.format(format)
//				.build();
//	}
}

