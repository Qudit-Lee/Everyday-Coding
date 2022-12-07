package com.project.api;

import java.io.BufferedReader;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

import com.project.dto.Info;
import com.project.dto.InfoForm;

import com.project.inforepository.InfoRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.stereotype.Controller
//@RestController
public class Controller {
	@Autowired
	private InfoRepository infoRepository;

	@GetMapping("/api")
	public String index() {
		log.debug("/info start");
	    return "index";
	}

    @PostMapping("/api")
    public String load_save(@RequestParam("serviceKey") String serviceKey, Model model) {
    	String result = "";
        String ko= "ko";
        String text= "isHTML";
       
        try {
        	String RequestserviceKey= serviceKey;
    	    // URI를 URL객체로 저장
            URL url = new URL("https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
   				 "/?page=1"+ /*page 수*/
   				 "/&perPage=10"+ /*출력할 데이터 수*/
   				 "/&serviceKey="+RequestserviceKey /*인증키*/);
           
            // 버퍼 데이터(응답 메세지)를 읽어서 result에 저장
            // result에는 XML 형식의 응답 데이터가 String으로 저장되어 있음
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();

            // json 데이터가 한줄로 출력 -> 보기 불편
	        //System.out.println(result);
           
            // json 데이터를 보기 좋게 출력
            //ObjectMapper mapper = new ObjectMapper(); // com.fasterxml.jackson.databind.ObjectMapper;
            //mapper.enable(SerializationFeature.INDENT_OUTPUT); // com.fasterxml.jackson.databind.SerializationFeature;
            //Object json = mapper.readValue(result.toString(), Object.class);
            //String jsonStr = mapper.writeValueAsString(json);
            //System.out.println(jsonStr);
	        
            // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
            JSONParser parser = new JSONParser();
            // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장. 	
            JSONObject obj = (JSONObject)parser.parse(result);
            // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
            JSONArray dataArr = (JSONArray) obj.get("data");
            List<InfoForm> rlist= new ArrayList<InfoForm>();
            
            for(int i=0;i<dataArr.size();i++){
            	JSONObject item = (JSONObject)dataArr.get(i);
                Info infoObj = new Info(i+(long)1,
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("주제1").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()),
                		(item.get("전문자료 메인 제목").toString()));
//                ("{\"org\":"+item.get("전문자료 메인 제목")+"-\"}"),            	
//                ("[{\"org\":\""+item.get("주제1")+",\""+item.get("주제2")+"\""+
//             				      ",\""+item.get("주제3")+"\""+",\""+"alt\":\""+item.get("전문자료 문서 타입")+"\"}]"),
//                ("{\"summary\":{\"org\":\""+item.get("전문자료 문서 새요약")+
//             					      "\",toc\":\"{org\":\""+item.get("전문자료 문서 목차")+"\"}"),               
//                ("{\"org\":\""+item.get("전문자료 부서 코드")+"\"}"),               
//                ("[{\"org\":\""+item.get("전문자료 등록자")+"\",\"role\":\"author\"}\","+
//             				           "\"affiliation\":[{\"org\":\""+item.get("전문자료 문서 저자")+"\"}]]"),                
//                ("{\"registration\":\""+item.get("전문자료 등록 일자").toString().substring(0, 16)+"\", \"approval\":\""+item.get("전문자료 승인 일자").toString().substring(0, 16)+"\"}"),
//                ("{\"org\":\""+ko+"\"}"),
//                ("{\"id\":\""+item.get("전문자료 문서 아이디").toString().substring(0, 4)+"\"}"),                
//                ("{\"media\":\""+text+"\"}"));
            	 //rlist.add(info);
                infoRepository.save(infoObj);
            }
        
        }catch(Exception e) {
        	e.printStackTrace();   
        }
        return "redirect:/findname";
    }

}

