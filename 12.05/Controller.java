package com.project.info;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class Controller {
	
	public static void main(String[] args) {
		String urlStr= "https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
				"?page=1"+ /*page 수*/
				"&perPage=10"+ /*출력할 데이터 수*/
				"&serviceKey=serviceKey=="; /*인증키*/
		
        String result = "";
        
        try {
            // URI를 URL객체로 저장
            URL url = new URL(urlStr.toString());
			
            
            // 버퍼 데이터(응답 메세지)를 읽어서 result에 저장
            // result에는 XML 형식의 응답 데이터가 String으로 저장되어 있음
            BufferedReader bf;
            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));
            result = bf.readLine();
	
		    // json 데이터가 한줄로 출력 -> 보기 불편
	        //System.out.println(result);
		
		    // json 데이터를 보기 좋게 출력
            ObjectMapper mapper = new ObjectMapper(); // com.fasterxml.jackson.databind.ObjectMapper;
            mapper.enable(SerializationFeature.INDENT_OUTPUT); // com.fasterxml.jackson.databind.SerializationFeature;
            Object json = mapper.readValue(result.toString(), Object.class);
            String jsonStr = mapper.writeValueAsString(json);
            //System.out.println(jsonStr);
	        
            // 1. 문자열 형태의 JSON을 파싱하기 위한 JSONParser 객체 생성.
            JSONParser parser = new JSONParser();
            // 2. 문자열을 JSON 형태로 JSONObject 객체에 저장. 	
            JSONObject obj = (JSONObject)parser.parse(result.toString());
            // 3. 필요한 리스트 데이터 부분만 가져와 JSONArray로 저장.
            JSONArray dataArr = (JSONArray) obj.get("data");

            //System.out.println(dataArr);
            
            System.out.println("---------------------------");
            for(int i=0; i<dataArr.size(); i++){
            	JSONObject item = (JSONObject)dataArr.get(i);
                System.out.println("title: "+"{\"org\":"+item.get("전문자료 메인 제목")+"-\"}");
                System.out.println("subjects :"+"[{\"org\":\""+item.get("주제1")+",\""+item.get("주제2")+"\""+",\""+item.get("주제3")+"\""+",\""+"alt\":\""+item.get("전문자료 문서 타입")+"\"}]");
                System.out.println("description :"+"{\"summary\":{\"org\":\""+item.get("전문자료 문서 새요약")+"\",toc\":\"{org\":\""+item.get("전문자료 문서 목차")+"\"}"); // api 내 오타?
                System.out.println("publisher :"+"{\"org\":\""+item.get("전문자료 부서 코드")+"\"}");
                System.out.println("contributors :"+"[{\"org\":\""+item.get("전문자료 등록자")+"\",\"role\":\"author\"}\","+
                				   "\"affiliation\":[{\"org\":\""+item.get("전문자료 문서 저자")+"\"}]]");
                System.out.println("date :{\"registration\":\""+item.get("전문자료 등록 일자").toString().substring(0, 16)+"\", \"approval\":\""+item.get("전문자료 승인 일자").toString().substring(0, 16)+"\"}");
                System.out.println("identifier : "+"{\"id\":\""+item.get("전문자료 문서 아이디").toString().substring(0, 4)+"\"}");
                System.out.println("---------------------------");
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}	
}
