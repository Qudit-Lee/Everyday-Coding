package com.project.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.project.model.Policy;
import com.project.service.PolicyService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
	
	@Autowired
	PolicyService policyService;
	
	@RequestMapping(value = "/home", method=RequestMethod.GET)
	public ModelAndView goHome(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		
		//Policy 객체 리스트 변수
		//List<Policy> policyList= new ArrayList<Policy>();
		List<Policy> policyList= policyService.getPolicy(); 
		
		//Policy 모델.
		//Policy policyModle= new Policy();
		//Policy policyModel;
		
		// 첫 번째 데이터.
		/*policyModel = Policy.builder()
				.title("\"org\":\"해외경쟁정책동향 (제61호)\"")
				.subjects(null)
				.description(null)
				.publisher("{\"org\":\"공정거래위원회\"}")
				.contributors(null)
				.date(null)
				.language("{\"org\":\"ko\"}")
				.identifier("{\"id\":\"2963\"}")
				.format("{\"media\":\"isHTML\"}")
				.build();*/
		
		/*policyModle.setTitle("\"org\":\"해외경쟁정책동향 (제61호)\"");
		policyModle.setSubjects("[{\"org\":\"공정거래,\"null\",\"null\",\"alt\":\"동향분석\"}]");
		policyModle.setDescription("{\"summary\":{\"org\":\"1. [미국] 법무부(DOJ), 기업결합 시정조치 가이드라인 개정 <BR>▶ 2004년 최초 가이드라인 발간 이후 국경간 M&amp;A 및 복잡한 수직결합이 급증하는 상황을 반영하였으며, 특히 수직결합에 대한 맞춤형 조치의 중요성을 강조 <BR><BR>2. [미국] 연방거래위원회(FTC), 석유분야 반독점행위 조사 착수 <BR>▶ 최근 유가상승에 따라 제기된 석유분야 반독점행위 의혹에 대한 전방위적인 조사를 통해 법위반행위를 엄중제재하고 석유시장에 대한 감시활동도 강화할 계획 <BR><BR>3. [EU] 집행위 부위원장, 유럽의 경쟁력 강화를 위한 경쟁정책의 역할 강조 <BR>▶ 경쟁정책의 강력한 집행은 공정한 경쟁여건을 조성해 유럽의 국제경쟁력을 강화할 수 있는 기반을 제공할 것이라고 언급 <BR>\",toc\":\"{org\":\"null\"}");
		policyModle.setPublisher("{\"org\":\"공정거래위원회\"}");
		policyModle.setContributors("[{\"org\":\"null\",\"role\":\"author\"}\",\"affiliation\":[{\"org\":\"국제협력과\"}]]");
		policyModle.setDate("{\"registration\":\"2011-08-01 00:00\", \"approval\":\"2011-09-14 18:37\"}");
		policyModle.setLanguage("{\"org\":\"ko\"}");
		policyModle.setIdentifier("{\"id\":\"2963\"}");
		policyModle.setFormat("{\"media\":\"isHTML\"}");*/
		
		//policyList.add(policyModel);
		
		mav.addObject("policyList", policyList);
		mav.setViewName("content/home.html");
		return mav;
	}
}