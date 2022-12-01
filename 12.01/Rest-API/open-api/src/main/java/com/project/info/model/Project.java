package com.project.info.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL) // null값이면 출력 하지 않음.
public class Project {
	public String projectName;
	@JsonProperty(value= "project master")
	public String author;
	//@JsonIgnore //결과 창에서 응답값을 숨김.
	@JsonFormat(shape= JsonFormat.Shape.STRING, pattern= "yyyy-MM-dd"
			+ " HH:mm:ss", timezone= "Asia/Seoul")
	public Date createdDate;

}