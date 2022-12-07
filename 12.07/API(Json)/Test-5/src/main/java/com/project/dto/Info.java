package com.project.dto;

//import com.project.dto.InfoForm;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
//import lombok.Builder;
import lombok.Data;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.ToString;


@Entity
@Data
@NoArgsConstructor
@Table(name= "if_dk_item_전문")
public class Info {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	
	public Info (Long id, String title, String subjects, String description, String publisher, 
    		String contributors, String date, String language, String identifier, String format) {
        this.id = id;
		this.title = title;
        this.subjects = subjects;
        this.description = description;
        this.publisher = publisher;
        this.contributors = contributors;
        this.date = date;
        this.language = language;
        this.identifier = identifier;
        this.format = format;
	}
}