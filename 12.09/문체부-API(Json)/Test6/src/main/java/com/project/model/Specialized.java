package com.project.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name= "if_dk_item")
public class Specialized {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
	@Column(name= "Meta_Title", length= 1000)
	public String title;
	@Column(name= "Meta_Subject1")
	public String subjects1;
	@Column(name= "Meta_Subject2")
	public String subjects2;
	@Column(name= "Meta_Subject3")
	public String subjects3;
	@Column(name= "Meta_Subject4")
	public String subjects4;
	
	@Column(name= "Meta_Desc1", length= 1000)
	public String description1;
	@Column(name= "Meta_Desc2", length= 1000)
	public String description2;
	
	@Column(name= "Meta_Publisher")
	public String publisher;
	
	@Column(name= "Meta_Contributor1")
	public String contributors1;
	@Column(name= "Meta_Contributor2")
	public String contributors2;
	
	@Column(name= "Meta_Date1")
	public String date1;
	@Column(name= "Meta_Date2")
	public String date2;
	
	@Column(name= "Meta_Identifier")
	public String identifier;

	
	public Specialized(Long id, String title, String subjects1, String subjects2, String subjects3,
			           String subjects4, String description1, String description2, String publisher, 
	                   String contributors1, String contributors2, String date1, String date2, String identifier) {
		this.id = id;
		this.title = title;
        this.subjects1 = subjects1;
        this.subjects2 = subjects2;
        this.subjects3 = subjects3;
        this.subjects4 = subjects4;
        this.description1 = description1;
        this.description2 = description2;
        this.publisher = publisher;
        this.contributors1 = contributors1;
        this.contributors2 = contributors2; 
        this.date1 = date1;
        this.date2 = date2; 
        this.identifier = identifier;

	}
}