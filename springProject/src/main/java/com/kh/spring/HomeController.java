package com.kh.spring;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {
	
	
	@RequestMapping(value = "home.kh", method = RequestMethod.GET)
	public String home() {
		// HomeController에는 메인페이지로 돌아가는 기능 또는
		// 개인적으로 테스트 하고 싶은 것이 있을 경우 사용하자!
		return "home";
	}
	
}




