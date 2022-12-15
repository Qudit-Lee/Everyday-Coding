/* "Index.HTML"에서 받은 category 값으로 URL 주소를 구분하고, servicekey, page 값으로 완성한 Open-API의 데이터를 불러와
 * "Json"형식의 객체로 분활 시킨 후 "Specialized" 엔티티클래스 모델에 맞춰 매핑 하여 "Repository(SpecializedRepository)"로 저장
 * 그리고 "list"라는 액션 명을 가진 액션 재요청*/

package com.project.controller;

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

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestParam;

import com.project.domain.Specialized;
import com.project.repository.SpecializedRepository;

//import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@org.springframework.stereotype.Controller
public class Controller {
	@Autowired
	private SpecializedRepository Repository;
	
    @GetMapping("/api")
    public String load_save(@RequestParam("serviceKey") String serviceKey,@RequestParam("page") Integer page,@RequestParam("category") String category, Model model) {
    	//@RequestParam을 이용해서 index.html에서 입력값을 받아온다.
    	model.addAttribute("data", "");
    	log.debug("/info start");
    	String result = ""; //result 초기화
    	char quotes = '"'; // 매핑시 ""안에 "을 넣기 위해 선언
    	String Requestcategory= category;
    	String title ="";
    	String subject = "";
    	String description = "";
    	String publisher = "";
    	String contributors = "";
				 
    	String date ="";
    	String language = "";
    	String identifier = "";
    	String format ="";
    	String relation = "";
    	String coverage= "";
    	String right= "";
    	
    	if(Requestcategory.equals("현행법령")){//if 문을 사용하여 데이터에 따라 실행되는 Mapping코드를 나눈다.
	    try {
	    	String RequestserviceKey= serviceKey;// 받아온 Service key
	    	int Requestpage= page;// 받아온 page수
	    	if(page>=263||page<=0) {
	    		model.addAttribute("error_name", "데이터 수집 ERROR~!!");
	    	    model.addAttribute("error_code", "EF_R_003");
	    	    model.addAttribute("error_reason", "정확한 페이지를 입력해주시기 바랍니다.");
	    	    model.addAttribute("error_page", "요청하신 Page :"+page);
	    	    model.addAttribute("error_pagenum", "현행법령의 페이지 수 범위는 1 ~ 262쪽입니다.");
	    	    return "index";	 
	    	}
	    		
	    	
    	    // URI를 URL객체로 저장
	    	
	    		URL url = new URL("http://www.law.go.kr/DRF/lawSearch.do?OC="+RequestserviceKey+"&target=law&type=XML"+ /*url 주소*/
	      				 "&page="+Requestpage);
			
            //https://www.law.go.kr/DRF/lawSearch.do?OC=helena0809&target=law&type=XML&page=1
	        // 버퍼 데이터(응답 메세지)를 읽어서 result에 저장
	        // result에는 XML 형식의 응답 데이터가 String으로 저장되어 있음
	        BufferedReader bf;
	        bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
	        result = bf.readLine();
	        JSONObject jsonObject = XML.toJSONObject(result.toString()); //result 값을 XMLtoJson를 사용하여 json형식으로 바꾼뒤 json형태의 Object(객체)로 분리
	        JSONObject jsonObject2 = jsonObject.getJSONObject("LawSearch");//분리된 객체를 다시 json형태의 Object(객체)로 분리
	        JSONArray jArray = jsonObject2.getJSONArray("law"); //분리된 Object에서 "LawSearch"라는 객체를 Json형태의 리스트로 분리
	        for(int i=0; i<jArray.length(); i++){ //for문을 통해 JsonArray의 수 만큼 리스트 안의 객체를 분리
	        	JSONObject item = (JSONObject)jArray.get(i);
	        	// 원하는 항목명을 Parsing 해서 형식에 맞춰 변수에 저장  
	        	try {
	        	 title = ("{"+quotes+"org"+quotes+":"+quotes+item.get("법령명한글").toString()+quotes+"}");   
	        	}catch(Exception e) {
	        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
	         	    model.addAttribute("error_code", " EF_R_001");
	         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Title");
	         	    return "index";}
	        	try {
	        		subject = (("{"+quotes+"org"+quotes+":"+quotes+item.get("법령약칭명").toString()+quotes+"}"));
	        	}catch(Exception e) {
	        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
	         	    model.addAttribute("error_code", " EF_R_001");
	         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Subject");
	         	    return "index";}
	        	try { 
	        	 description = (("{\"summary\":{\"org\":\""+"\",toc\":\"{org\":\""+"\"}")); 
	        	}catch(Exception e) {
	        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
	         	    model.addAttribute("error_code", " EF_R_001");
	         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Description");
	         	    return "index";}
	        	 try {
	        	 publisher = ("{"+quotes+"org"+quotes+":"+quotes+item.get("소관부처명").toString()+quotes+"}");
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Publisher");
		         	    return "index";}
	        	 try {
	        	 contributors = (("[{\"org\":\""+"\",\"role\":\"author\"}\","+
     				   "\"affiliation\":[{\"org\":\""+"\"}]]")); 
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Contributors");
		         	    return "index";}
	        	 try {
	        	 date = ("{"+quotes+"issued"+quotes+":"+quotes+item.get("시행일자").toString()+","+"created:"+item.get("공포일자").toString()+quotes+"}");
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Date");
		         	    return "index";}
	        	 try {
	        	 language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Language");
		         	    return "index";}
	        	 try {
	        	 identifier = ("{"+quotes+"site"+quotes+":"+quotes+item.get("법령일련번호").toString()+","+"url:"+item.get("법령상세링크").toString()+quotes+"}");
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Identifier");
		         	    return "index";}
	        	 try {
	        	 format = (("{\"org\":\""+"\"}"));
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Format");
		         	    return "index";}
	        	 try {
	        	 relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+item.get("제개정구분명").toString()+quotes+"}");
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Relation");
		         	    return "index";}
	        	 try {
	        	 coverage= (("{\"org\":\""+"\"}"));
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Coverage");
		         	    return "index";}
	        	 try {
	        	 right= (("{\"org\":\""+"\"}"));
	        	 }catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Right");
		         	    return "index";}
	        	 
	        	
	        	// 저장한 변수를 Specialized 객체에 저장
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
    		model.addAttribute("error_name", "데이터 수집 ERROR~!!");
    	    model.addAttribute("error_code", " EF_R_003");
    	    model.addAttribute("error_reason", "정확한 인증키를 입력해주시기 바랍니다.");
    	    model.addAttribute("error_key", "요청하신 Key: "+serviceKey);
    	    model.addAttribute("error_page", "요청하신 Page: "+page);
    	    return "index";  
        
    }
    	}else if(Requestcategory.equals("행정규칙")) {
    		try {
    	    	String RequestserviceKey= serviceKey;
    	    	int Requestpage= page;
    	    	if(page>=968||page<=0) {
    	    		model.addAttribute("error_name", "데이터 수집 ERROR~!!");
    	    	    model.addAttribute("error_code", "EF_R_003");
    	    	    model.addAttribute("error_reason", "정확한 페이지를 입력해주시기 바랍니다.");
    	    	    model.addAttribute("error_page", "요청하신 Page :"+page);
    	    	    model.addAttribute("error_pagenum", "행정규칙의 페이지 수 범위는 1 ~ 967쪽입니다.");
    	    	    return "index";	 
    	    	}
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
    	        	try { 
    	        	 title = ("{"+quotes+"org"+quotes+":"+quotes+item.get("행정규칙명").toString()+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Title");
		         	    return "index";}
    	        	try {
    	        		subject = (("{"+quotes+"org"+quotes+":"+quotes+"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Subject");
		         	    return "index";}
    	        	try { 
    	        		description = (("{\"summary\":{\"org\":\""+"\",toc\":\"{org\":\""+"\"}")); 
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Description");
		         	    return "index";}
    	        	try {
    	        		publisher = ("{"+quotes+"org"+quotes+":"+quotes+item.get("소관부처명").toString()+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Publisher");
		         	    return "index";}
    	        	try {
    	        		contributors = (("[{\"org\":\""+"\",\"role\":\"author\"}\","+"\"affiliation\":[{\"org\":\""+"\"}]]"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Contributors");
		         	    return "index";}
    	        	try {
    	        		date = ("{"+quotes+"issued"+quotes+":"+quotes+item.get("시행일자").toString()+","+"created:"+item.get("생성일자").toString()+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Date");
		         	    return "index";}
    	        	try {
    	        		language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}"); 
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Language");
		         	    return "index";}
    	        	try {
    	        		identifier = ("{"+quotes+"site"+quotes+":"+quotes+item.get("행정규칙ID").toString()+","+"url:"+item.get("행정규칙상세링크").toString()+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Identifier");
		         	    return "index";}
    	        	try {
    	        		format = (("{\"org\":\""+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Format");
		         	    return "index";}
    	        	try {
    	        		relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+item.get("제개정구분명").toString()+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Relation");
		         	    return "index";}
    	        	try {
    	        		coverage= (("{\"org\":\""+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Coverage");
		         	    return "index";}
    	        	try {
    	        		right= (("{\"org\":\""+"\"}"));
    	        	}catch(Exception e) {
    		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
    		         	    model.addAttribute("error_code", " EF_R_001");
    		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Right");
    		         	    return "index";}
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
        		model.addAttribute("error_name", "데이터 수집 ERROR~!!");
        	    model.addAttribute("error_code", " 코드: EF_R_003");
        	    model.addAttribute("error_reason", "사유: 정확한 인증키를 입력해주시기 바랍니다.");
        	    model.addAttribute("error_key", "요청하신 Key: "+serviceKey);
        	    model.addAttribute("error_page", "요청하신 Page: "+page);
        	    return "index";  
        }
    	}else if(Requestcategory.equals("전문자료")) { // @RequestParam로 받아온 Requestcategory의 값으로 Mapping 코드 구분.
    		try {
    			String RequestserviceKey= serviceKey;// 받아온 Service key
    	    	int Requestpage= page;// 받아온 page수
    	    	if(page>=2401||page<=0) {
    	    		model.addAttribute("error_name", "데이터 수집 ERROR~!!");
    	    	    model.addAttribute("error_code", "EF_R_003");
    	    	    model.addAttribute("error_reason", "정확한 페이지를 입력해주시기 바랍니다.");
    	    	    model.addAttribute("error_page", "요청하신 Page :"+page);
    	    	    model.addAttribute("error_pagenum", "전문자료의 페이지 수 범위는 1 ~ 2400쪽입니다.");
    	    	    return "index";	 
    	    	}
        	    // URI를 URL객체로 저장
                URL url = new URL("https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
       				 "?page="+Requestpage+ /*page 수*/
       				 "&perPage=10"+ /*출력할 데이터 수*/
       				 "&serviceKey="+RequestserviceKey /*인증키*/);

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
    	        	try {
    	        	 title = (("\"org\":\""+item.get("전문자료 메인 제목").toString()+"\""));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Title");
		         	    return "index";}
    	        	try {
    	        	 subject = (("[{\"org\":\""+item.get("주제1")+",\""+item.get("주제2")+"\""+
           				 ",\""+item.get("주제3")+"\""+",\""+"alt\":\""+item.get("전문자료 문서 타입")+"\"}]"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Subject");
		         	    return "index";}
    	        	try {
    	        	 description = (("{\"summary\":{\"org\":\""+item.get("전문자료 문서 새요약")+
          					 "\",toc\":\"{org\":\""+item.get("전문자료 문서 목차")+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Description");
		         	    return "index";}
    	        	try {
    	        	 publisher = (("{\"org\":\""+item.get("전문자료 부서 코드")+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Publisher");
		         	    return "index";} 
    	        	try { 
    	        	 contributors = (("[{\"org\":\""+item.get("전문자료 등록자")+"\",\"role\":\"author\"}\","+
         				   "\"affiliation\":[{\"org\":\""+item.get("전문자료 문서 저자")+"\"}]]"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Contributors");
		         	    return "index";}
    	        	try { 
    	        	 date = (("{\"registration\":\""+item.get("전문자료 등록 일자").toString().substring(0, 16)+"\", \"approval\":\""+item.get("전문자료 승인 일자").toString().substring(0, 16)+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Date");
		         	    return "index";}
    	        	try { 
    	        	 language = ("{"+quotes+"org"+quotes+":"+quotes+"ko"+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Language");
		         	    return "index";}
    	        	try { 
    	        	 identifier = (("{\"id\":\""+item.get("전문자료 문서 아이디").toString().substring(0, 4)+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Identifier");
		         	    return "index";}
    	        	try { 
    	        	 format = (("{\"media\":\""+"text"+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Format");
		         	    return "index";}
    	        	try { 
    	        	 relation = ("{"+quotes+"isPartOF"+quotes+":"+quotes+"}");
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Relation");
		         	    return "index";}
    	        	try { 
    	        	 coverage= (("{\"org\":\""+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Coverage");
		         	    return "index";}
    	        	try { 
    	        	 right= (("{\"org\":\""+"\"}"));
    	        	}catch(Exception e) {
		        		model.addAttribute("error_name", "증분 데이터 ERROR~!!");
		         	    model.addAttribute("error_code", " EF_R_001");
		         	    model.addAttribute("error_column", "수집 실패한 데이터항목: Right");
		         	    return "index";}
    	        	
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
    	    model.addAttribute("error_name", "데이터 수집 ERROR~!!");
    	    model.addAttribute("error_code", " EF_R_003");
    	    model.addAttribute("error_reason", "정확한 인증키를 입력해주시기 바랍니다.");
    	    model.addAttribute("error_key", "요청하신 Key: "+serviceKey);
    	    model.addAttribute("error_page", "요청하신 Page :"+page);
    	    return "index";      
    	    
    	}
    	}else {
        	    model.addAttribute("error_name", "매핑 ERROR~!!");
        	    model.addAttribute("error_code", " EF_R_002");
        	    model.addAttribute("error_reason", "현행법령, 행정규칙, 전문자료 이외의 데이터를 입력하셨습니다.");
        	    return "index"; 
    	}
	    return "redirect:/list"; //"list"라는 이름을 가직 액션을 찾아 리턴 
	}
}
