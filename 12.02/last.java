package com.project;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class last {
	
	@GetMapping("/api3")
	public String callApi() throws IOException {
		StringBuilder result = new StringBuilder();
		
			String urlStr= "https://api.odcloud.kr/api/15092231/v1/uddi:f485c10f-f5d2-4a00-a993-b85d929565ec"+
					"/?page=1"+
					"/&perpage=10"+
					"/&returnType=XML"+
					"/&serviceKey=인증키";
		
			URL url = new URL(urlStr);
		
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestMethod("GET");
			
			BufferedReader br;
			
			br= new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
			
			String returnLine;
			
			while((returnLine = br.readLine()) != null) {
				result.append(returnLine+"\n\r");
			}
			urlConnection.disconnect();
			
		return result.toString();
	}
	@GetMapping(value = ".json")
    public Map<String, Object> exam1GET() {
        Map<String, Object> map = new HashMap<>();
        try {
            // 소셜 로그인 , 공공api 등의 url
            String url = "http://localhost:8080/api3";

            // Spring boot에서 제공하는 RestTemplate
            RestTemplate restTemplate = new RestTemplate();
            
            // 1. api호출하여 결과를 가져오기
            // RestTemplate.getForObject(URI url, Class<T> responseType) => (호출하는 url, 반환타입)
            String response = restTemplate.getForObject(url, String.class);

            // 2. 가공된 데이터를 반환
            System.out.println(response);

            // 3. DB에 저장
            // {"ret" : "y", "data":"123"}
            JSONParser parser = new JSONParser();
            JSONObject jobj =  (JSONObject)parser.parse(response.toString());
            JSONObject results = (JSONObject)jobj.get("results");
            JSONObject body = (JSONObject)results.get("data");
            JSONArray itemArr = (JSONArray)body.get("item");

            for(int i=0; i<itemArr.size(); i++){
            	JSONObject item = (JSONObject)itemArr.get(i);
            	System.out.println("title: "+"{org:"+item.get("전문자료메인제목"));
                System.out.println("subjects : "+"{org:"+item.get("주제1")+"{org:"+item.get("주제2")+
                      "{org:"+item.get("주제3")+"{org:"+item.get("전문자료문서타입"));
                System.out.println("description : "+"{summary_org:"+item.get("전문자료문서요약")+"{toc_org:"+item.get("전문자료문서목차")); // api 내 오타?
                System.out.println("publisher : "+"{org:"+item.get("전문자료문서코드"));
                System.out.println("contributors : "+"{org(author):"+item.get("전문자료문서저자")+"{org(donator):"+item.get("전문자료등록자"));
                System.out.println("date : "+item.get("전문자료등록일자").toString().substring(0, 16)+
                      item.get("전문자료승인일자").toString().substring(0, 16));
                System.out.println("identifier : "+"{ID:"+item.get("전문자료문서아이디").toString().substring(0, 4)+"%");
                System.out.println("---------------------------");

            }

//            String ret1 = jobj.getString("ret"); // y
//            String ret2 = jobj.getString("data"); // 123

//            System.out.println(ret1 + "," + ret2);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}