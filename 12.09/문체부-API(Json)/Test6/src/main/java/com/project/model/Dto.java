package com.project.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dto {
	private String title;	
	private String subjects;
	private String description;
	private String publisher;
	private String contributors;
	private String date;	
	private String language;	
	private String identifier;
	private String format;
}
