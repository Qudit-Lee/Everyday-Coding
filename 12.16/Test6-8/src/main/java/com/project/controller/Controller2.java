package com.project.controller;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.domain.Specialized;
import com.project.model.Dto;
import com.project.repository.SpecializedRepository;
import com.project.service.InfoService;

@org.springframework.stereotype.Controller
public class Controller2 {
	@Autowired
	private SpecializedRepository Repository;
	
	@GetMapping("/api")
	public String load_save(@RequestParam("serviceKey") String serviceKey,@RequestParam("page") Integer page,
			@RequestParam("category") String category, @RequestParam("startDate") String startDate,
			@RequestParam("endDate") String endDate, Model model) {
    	//@RequestParam을 이용해서 index.html에서 입력값을 받아온다.
		String urlStr= category+ /*url 주소*/    		
	   			"?serviceKey="+serviceKey+/*인증키*/
	   			"&startDate="+startDate+
	   			"&endDate="+endDate;
		StringBuffer result= new StringBuffer();
		char quotes = '"';
        try {

            URL url = new URL(urlStr.toString());

            BufferedReader bf;
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.connect();
            BufferedInputStream bufferedInputStream = new BufferedInputStream(urlConnection.getInputStream());
            bf = new BufferedReader(new InputStreamReader(bufferedInputStream, "UTF-8"));
            String returnLine;
            while((returnLine = bf.readLine()) != null) {
                result.append(returnLine);
            }
		    JSONObject jsonObject = XML.toJSONObject(result.toString());
		    JSONObject jsonObject2 = jsonObject.getJSONObject("response");
		    JSONObject jsonObject3 = jsonObject2.getJSONObject("body");
		    JSONArray jarray= jsonObject3.getJSONArray("NewsItem");
		    for(int i=0; i<jarray.length(); i++){ //for문을 통해 JsonArray의 수 만큼 리스트 안의 객체를 분리
		    	JSONObject item = (JSONObject)jarray.get(i);
		    
	        	String title= item.get("Title").toString();
	        	String subject = (("{"+quotes+"org"+quotes+":"+quotes+"}"));
	        	String description = (item.get("DataContents").toString()); 
	        	String publisher = (item.get("MinisterCode").toString());
	        	String contributors = (item.get("MinisterCode").toString()); 
	        	String date = (item.get("ModifyDate").toString()+", "+item.get("ApproveDate").toString()+", "+item.get("EmbargoDate").toString());
	        	String language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
	        	String identifier = ("{"+quotes+"id"+quotes+":"+quotes+item.get("NewsItemId").toString()+","+"url:"+item.get("OriginalUrl").toString()+", "+item.get("FileUrl").toString()+quotes+"}");
	        	String format = (("{\"org\":\""+"\"}"));
	        	String relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+quotes+"}");
	        	String coverage= (("{\"org\":\""+"\"}"));
	        	String right= (("{\"reference\":\""+item.get("FileName").toString()+"\"}"));
	        	
	        	Specialized infoObj= new Specialized(i+(long)1,
	        			(title.toString()),
                		(subject.toString()),
                		(description.toString()),
                		(publisher.toString()),
                		(contributors.toString()),
                		(date.toString()),
                		(language.toString()),
	        			(identifier.toString()),
	        			(format.toString()),
	        			(relation.toString()),
	        			(coverage.toString()),
	        			(right.toString()));
	        	
	        	Repository.save(infoObj);
	        	//System.out.println(item);
	        	
		    }
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return "redirect:/list";
    }
	@RestController
	public class InfoController {
		private InfoService infoService; //InFoService클래스에서 만든 함수를 사용하기위해 임폴트 
		
		@Autowired  // spring 4.3 버전 이상부터는 생략 가능
		public InfoController(InfoService infoService) {
			this.infoService = infoService;
		}
		@GetMapping("/list")// 액션 list로 보낸것을 받음
		public Object dtoList() {
			//log.debug("/dtoList start"); //로그
			List<Dto> dtoList = infoService.getDtoList(); //InfoService의 getDtoList()라는 함수의 값을 Dto클래스의 모델을 리스트로 생성
			return dtoList; //생성한 리스트 모델을 리턴하여 출력
		}
		
	}
}
        
        

