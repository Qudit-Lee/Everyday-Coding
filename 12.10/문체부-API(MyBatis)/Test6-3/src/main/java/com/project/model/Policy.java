package com.project.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Policy {
	public String title;	
	public String subjects;		
	public String description;		
	public String publisher;	
	public String contributors;	
	public String date;		
	public String language;		
	public String identifier;	
	public String format;
}
