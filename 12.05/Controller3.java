package com.project.info;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONObject;
import org.json.XML;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
@RestController
@RequestMapping("/info")
public class Controller3 {
	@GetMapping("/api")
	public static void xmlToJson(String str) {
	
		try {
			String xml= str;
			JSONObject jobject= XML.toJSONObject(xml);
			ObjectMapper mapper= new ObjectMapper();
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			Object json= mapper.readValue(jobject.toString(), Object.class);
			
			String output= mapper.writeValueAsString(json);
			System.out.print(output);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
		
		//SpringApplication app= new SpringApplication(Application.class, args);
		
		String urlStr= "https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
				"?page=1"+ /*page 수*/
				"&perPage=10"+ /*출력할 데이터 수*/
				"&returnType=XML"+
				"&serviceKey=serviceKey";
		
		URL url= new URL(urlStr);
		BufferedReader bf;
		bf= new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
		String result= bf.readLine();
		xmlToJson(result);
	}
}