package com.project.info;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;

public class Controller2 {
	public static int INDENT_FACTOR = 4;
    public static void main(String args[]) throws MalformedURLException, IOException {
    	
    	try {
	    	HttpURLConnection conn = (HttpURLConnection) new URL("https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+ /*url 주소*/
					"?page=1"+ /*page 수*/
					"&perPage=10"+ /*출력할 데이터 수*/
					"&returnType=XML"+
					"&serviceKey=serviceKey").openConnection(); /*인증키*/
			
	    	conn.connect();
	        BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
	        BufferedReader reader = new BufferedReader(new InputStreamReader(bis));
	        StringBuffer st = new StringBuffer();
	        String line;
	        while ((line = reader.readLine()) != null) {
	            st.append(line);
	        }
	 
	        JSONObject xmlJSONObj = XML.toJSONObject(st.toString());
	        String jsonPrettyPrintString = xmlJSONObj.toString(INDENT_FACTOR);
	        System.out.println(jsonPrettyPrintString);
	        
	        // 이제부터 json 데이터를 get() 메소드로 쉽게 찾을 수 있음
            JSONObject response = (JSONObject)xmlJSONObj.get("results");
            JSONObject body = (JSONObject)response.get("data");
            JSONObject items = (JSONObject)body.get("item");
            JSONArray itemArr = (JSONArray)items.get("col");
            
            System.out.println("---------------------------");
            for(int i=0; i<itemArr.length(); i++){
            	JSONObject item = (JSONObject)itemArr.get(i);
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
    	}catch(Exception e) {
    		e.printStackTrace();
    	}
	}
}