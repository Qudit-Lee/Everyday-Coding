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

@Data //@Getter, @Setter 등을 한꺼번에 설정해주는 어노테이션 
@NoArgsConstructor //기본 생성자를 설정.
@Getter //접근자를 자동으로 생성
@Setter //설정자를 자동으로 생성
@Entity //Jpa를 이용하여 Table과 매핑
@Table(name= "if_dk_item") //DB에서 생성할 Table의 이름
public class Specialized {
	@GeneratedValue(strategy = GenerationType.IDENTITY) //pk값을 지정하기 위해 @ID 어노테이션을 사용하여 생성 전략을 재정의
	@Id //pk를 나타내기 위해 @ID 어노테이션 사용
	public Long META_ID;
	@Column(name= "META_CLASSIFI", length= 1000) //테이블의 컬럼 이름을 "META_CLASSIFI"로 컬럼의 크기를 1000으로 지정 
	public Long classifi;
	@Column(name= "META_TYPE", length= 1000) //이하 동문
	public Long type;
	@Column(name= "META_TITLE", length= 50000)
	public String title;
	@Column(name= "META_SUBJECT", length= 50000)
	public String subject;
	@Column(name= "META_DESC", length= 50000)
	public String description;
	@Column(name= "META_PUBLISHER")
	public String publisher;
	@Column(name= "META_CONTRIBUTOR", length= 50000)
	public String contributors;
	@Column(name= "META_DATE")
	public String date;
	@Column(name= "META_LANGUAGE")
	public String language;
	@Column(name= "META_IDENTIFIER")
	public String identifier;
	@Column(name= "META_FORMAT")
	public String format;
	@Column(name= "META_RELATION")
	public String relation;
	@Column(name= "META_COVERAGE")
	public String coverage;
	@Column(name= "META_RIGHT")
	public String right;
	
	public Specialized(Long id, String title, String subject, String description, String publisher, 
			String contributors, String date,String language, String identifier, String format, 
			String relation, String coverage, String right) {
		this.META_ID = id;
		this.title = title;
        this.subject = subject;
        this.description = description; 
        this.publisher = publisher;
        this.contributors = contributors; 
        this.date = date;
        this.language =language; 
        this.identifier = identifier;
        this.relation = relation;
        this.format= format;
        this.coverage= coverage;
        this.right= right;
	}
}