package com.project.info;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Autowired;
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

import com.project.model.Specialized;
import com.project.repository.SpecializedRepository;

//import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

//@RequiredArgsConstructor
@Service
@Slf4j
@org.springframework.stereotype.Controller
public class Controller {

	@Autowired
	private SpecializedRepository Repository;
	
	@GetMapping("/api")
	public String index() {
		log.debug("/info start");
	    return "index";
	}

    @PostMapping("/api")
    public String load_save(@RequestParam("serviceKey") String serviceKey,@RequestParam("page") String page,@RequestParam("category") String category, Model model) {
    	//@RequestParam을 이용해서 index.html에서 입력값을 받아온다.
    	String result = "";
    	char quotes = '"';
    	String Requestcategory= category;
    	if(category.equals("현행법령")){//if 문을 사용하여 데이터에 따라 실행되는 Mapping코드를 나눈다.
	    try {
	    	String RequestserviceKey= serviceKey;// 받아온 Service key
	    	String Requestpage= page;// 받아온 page수
	    	
    	    // URI를 URL객체로 저장
	    	
	    		URL url = new URL("http://www.law.go.kr/DRF/lawSearch.do?OC="+RequestserviceKey+"&target=law&type=XML"+ /*url 주소*/
	      				 "&page="+Requestpage);
			
            //https://www.law.go.kr/DRF/lawSearch.do?OC=helena0809&target=law&type=XML&page=1
	        // 버퍼 데이터(응답 메세지)를 읽어서 result에 저장
	        // result에는 XML 형식의 응답 데이터가 String으로 저장되어 있음
	        BufferedReader bf;
	        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	        result = bf.readLine();
	        JSONObject jsonObject = XML.toJSONObject(result.toString());
	        JSONObject jsonObject2 = jsonObject.getJSONObject("LawSearch");
	        JSONArray jArray = jsonObject2.getJSONArray("law");      
	        for(int i=0; i<jArray.length(); i++){
	        	JSONObject item = (JSONObject)jArray.get(i);
	        	
	        	String title = ("{"+quotes+"org"+quotes+":"+quotes+item.get("법령명한글").toString()+quotes+"}");	        	
	        	String subject = (("{"+quotes+"org"+quotes+":"+quotes+item.get("법령약칭명").toString()+quotes+"}"));
	        	String description = (("{\"summary\":{\"org\":\""+"\",toc\":\"{org\":\""+"\"}")); 
	        	String publisher = ("{"+quotes+"org"+quotes+":"+quotes+item.get("소관부처명").toString()+quotes+"}");
	        	String contributors = (("[{\"org\":\""+"\",\"role\":\"author\"}\","+
     				   "\"affiliation\":[{\"org\":\""+"\"}]]")); 
	        	String date = ("{"+quotes+"issued"+quotes+":"+quotes+item.get("시행일자").toString()+","+"created:"+item.get("공포일자").toString()+quotes+"}");
	        	String language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
	        	String identifier = ("{"+quotes+"site"+quotes+":"+quotes+item.get("법령일련번호").toString()+","+"url:"+item.get("법령상세링크").toString()+quotes+"}");
	        	String format = (("{\"org\":\""+"\"}"));
	        	String relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+item.get("제개정구분명").toString()+quotes+"}");
	        	String coverage= (("{\"org\":\""+"\"}"));
	        	String right= (("{\"org\":\""+"\"}"));
	        	
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

	        }
    	}
    	catch(Exception e) {
        e.printStackTrace();
    }
    	}else if(category.equals("행정규칙")) {
    		try {
    	    	String RequestserviceKey= serviceKey;
    	    	String Requestpage= page;
    	    	
        	    // URI를 URL객체로 저장
    	    	
    	    		URL url = new URL("http://www.law.go.kr/DRF/lawSearch.do?OC="+RequestserviceKey+"&target=admrul&query=학교&type=XML"+ /*url 주소*/
    	      				 "&page="+Requestpage);
    	    		//http://www.law.go.kr/DRF/lawSearch.do?OC=helena0809&target=admrul&query=%ED%95%99%EA%B5%90&type=XML
                //URL url = new URL("http://www.law.go.kr/DRF/lawSearch.do?OC="+serviceKey+"&target=law&type=XML"+ /*url 주소*/
       				 //"&page="+page);
    			
                //https://www.law.go.kr/DRF/lawSearch.do?OC=helena0809&target=law&type=XML&page=1
    	        // 버퍼 데이터(응답 메세지)를 읽어서 result에 저장
    	        // result에는 XML 형식의 응답 데이터가 String으로 저장되어 있음
    	        BufferedReader bf;
    	        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
    	        result = bf.readLine();
    	        JSONObject jsonObject = XML.toJSONObject(result.toString());
    	        JSONObject jsonObject2 = jsonObject.getJSONObject("AdmRulSearch");
    	        JSONArray jArray = jsonObject2.getJSONArray("admrul");      
    	        for(int i=0; i<jArray.length(); i++){
    	        	JSONObject item = (JSONObject)jArray.get(i);
    	        	
    	        	String title = ("{"+quotes+"org"+quotes+":"+quotes+item.get("행정규칙명").toString()+quotes+"}");	        	
    	        	String subject = (("{"+quotes+"org"+quotes+":"+quotes+"}"));
    	        	String description = (("{\"summary\":{\"org\":\""+"\",toc\":\"{org\":\""+"\"}")); 
    	        	String publisher = ("{"+quotes+"org"+quotes+":"+quotes+item.get("소관부처명").toString()+quotes+"}");
    	        	String contributors = (("[{\"org\":\""+"\",\"role\":\"author\"}\","+
         				   "\"affiliation\":[{\"org\":\""+"\"}]]")); 
    	        	String date = ("{"+quotes+"issued"+quotes+":"+quotes+item.get("시행일자").toString()+","+"created:"+item.get("생성일자").toString()+quotes+"}");
    	        	String language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
    	        	String identifier = ("{"+quotes+"site"+quotes+":"+quotes+item.get("행정규칙ID").toString()+","+"url:"+item.get("행정규칙상세링크").toString()+quotes+"}");
    	        	String format = (("{\"org\":\""+"\"}"));
    	        	String relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+item.get("제개정구분명").toString()+quotes+"}");
    	        	String coverage= (("{\"org\":\""+"\"}"));
    	        	String right= (("{\"org\":\""+"\"}"));
    	        	
    	        	Specialized infoObj2= new Specialized(i+(long)1,
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
    	        	
    	        		Repository.save(infoObj2);

    	        }
        	}
        	catch(Exception e) {
            e.printStackTrace();
        }
    	}else if(Requestcategory.equals("전문자료")) { // @RequestParam로 받아온 Requestcategory의 값으로 Mapping 코드 구분.
    		try {
    			String RequestserviceKey= serviceKey;// 받아온 Service key
    	    	String Requestpage= page;// 받아온 page수
    	    	
        	    // URI를 URL객체로 저장
                URL url = new URL("https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
       				 "/?page="+Requestpage+ /*page 수*/
       				 "/&perPage=10"+ /*출력할 데이터 수*/
       				 "/&serviceKey="+RequestserviceKey /*인증키*/);

                /* "https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec/?page=Requestpage
                 * /&perPage=10/&serviceKey=RequestserviceKey" */
                
                //Buffer를 사용하여 url의 데이터를 출력
                BufferedReader bf;
    	        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
    	        result = bf.readLine(); //출력한 데이터 한 줄 씩 읽기
    	        
    	        JSONObject obj = new JSONObject(result.toString()); //result 값을 Json 형태의 Object(객체)로 분리
    	        JSONArray dataArr = (JSONArray) obj.get("data"); //분리된 Object에서 "data"라는 객체를 Json형태의 리스트로 분리
    	        
    	        for(int i=0; i<dataArr.length(); i++){ //
    	        	JSONObject item = (JSONObject)dataArr.get(i); //for문을 통해 JsonArray의 수 만큼 리스트 안의 객체를 분리
    	        	
    	        	String title = (("\"org\":\""+item.get("전문자료 메인 제목").toString()+"\""));	        	
    	        	String subject = (("[{\"org\":\""+item.get("주제1")+",\""+item.get("주제2")+"\""+
           				 ",\""+item.get("주제3")+"\""+",\""+"alt\":\""+item.get("전문자료 문서 타입")+"\"}]"));
    	        	String description = (("{\"summary\":{\"org\":\""+item.get("전문자료 문서 새요약")+
          					 "\",toc\":\"{org\":\""+item.get("전문자료 문서 목차")+"\"}")); 
    	        	String publisher = (("{\"org\":\""+item.get("전문자료 부서 코드")+"\"}"));
    	        	String contributors = (("[{\"org\":\""+item.get("전문자료 등록자")+"\",\"role\":\"author\"}\","+
         				   "\"affiliation\":[{\"org\":\""+item.get("전문자료 문서 저자")+"\"}]]")); 
    	        	String date = (("{\"registration\":\""+item.get("전문자료 등록 일자").toString().substring(0, 16)+"\", \"approval\":\""+item.get("전문자료 승인 일자").toString().substring(0, 16)+"\"}"));
    	        	String language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
    	        	String identifier = (("{\"id\":\""+item.get("전문자료 문서 아이디").toString().substring(0, 4)+"\"}"));
    	        	String format = (("{\"media\":\""+"text"+"\"}"));
    	        	String relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+"}");
    	        	String coverage= (("{\"org\":\""+"\"}"));
    	        	String right= (("{\"org\":\""+"\"}"));
    	        	
    	        	Specialized infoObj3= new Specialized(i+(long)1, //분리한 객체에서 "전문자료 메인 제목" 등에 해당되는 객체를 우리가 원하는 값으로 매핑.
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
    	        	
    	        	Repository.save(infoObj3); //매핑한 값을 Repository에 저장
    	        }
    		}catch(Exception e) {
                e.printStackTrace();
    	}
    	}
	    return "redirect:/list"; //"list"라는 이름을 가직 디렉토리를 찾아 리턴 
	}
}
