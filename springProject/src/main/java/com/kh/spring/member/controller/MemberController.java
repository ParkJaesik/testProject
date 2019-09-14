package com.kh.spring.member.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kh.spring.member.model.service.MemberService;
import com.kh.spring.member.model.vo.Member;

// Model에 담긴 데이터 중 key 값이 loginUser인 데이터를 sessionScope로 추가해라
@SessionAttributes({"loginUser", "msg"})
@Controller
public class MemberController {
	
	// @Autowired 시 
	// 빈 스캐닝을 통해 bean으로 등록된 클래스 중
	// 알맞은 객체를 의존성 주입(DI)를 해줌
	
	@Autowired
	private MemberService mService;
	
	
	
	// ** 파라미터를 전송 받는 방법
	
	// 1. HttpServletRequest를 통해 전송 받기 (기존 servlet/jsp 방식)
	/* 메소드의 매개변수로 HttpServletRequest를 작성하면
	 * 스프링 컨테이너가 자동으로 요청페이지의
	 * HttpServletRequest 객체를 매개변수로 주입해줌
	 * */
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(HttpServletRequest request) {
		
		String id = request.getParameter("id");
		String pwd = request.getParameter("pwd");
		
		System.out.println(id + " / " + pwd);
		return "home";
	}*/
	
	
	// 2. @RequestParam 어노테이션
	/* Request 객체를 이용해서 파라미터를 전달받는 어노테이션
	 * 
	 * @RequestParam("name") String 변수명
	 * 
	 * 요청 페이지의 value가 비어있다면 ""으로 넘어옴 (에러X)
	 * */
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(@RequestParam("id") String id,
							  @RequestParam("pwd") String pwd) {
		System.out.println(id + " / " + pwd);
		return "home";
	}*/
	
	
	// 3. @RequestParam 어노테이션 생략
	/* @RequestParam 어노테이션 생략 시
	 *  매개변수명을 name과 똑같이 작성해야 자동으로 값이 주입된다.
	 * */
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(String id, String pwd) {
		System.out.println(id + " / " + pwd);
		return "home";
	}*/
	
	// 4. @ModelAttribute를 이용한 값 전달 방법
	/*  요청페이지에서 전달할 파라미터가 많은 경우
	 *  객체 타입으로 값을 전달받게 함.
	 *  
	 *  (주의사항) 전달 받을 객체(VO) 내부에
	 *  기본생성자 + setter가 생성이 되어 있어야 함!
	 *  + name 속성명이 VO의 필드명과 같아야함.
	 * 
	 *  --> 커맨드 방식
	 * */
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(@ModelAttribute Member mem) {
		System.out.println(mem.getId() + " / " + mem.getPwd());
		return "home";
	}*/
	
	
	// 5.@ModelAttribute 어노테이션 생략
	/* @ModelAttribute를 생략해도 
	 * 자동으로 커맨드 객체로 매핑이 된다.
	 * 
	 * 어노테이션은 필요에 따라 적어주면 되지만
	 * 충돌과 가독성을 생각하여 작성 해주는 것이 좋음.
	 * 
	 * */
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(Member mem , HttpSession session, Model model) {
		System.out.println(mem.getId() + " / " + mem.getPwd());
		
		Member loginUser = mService.loginMember(mem);
		
		System.out.println(loginUser);
		
		if(loginUser != null) {
			session.setAttribute("loginUser", loginUser);
			return "home";
		}else {
			model.addAttribute("msg", "로그인 실패!");
			return "common/errorPage";
		}
	}*/
	
	// 다량의 데이터를 전달 받을 시에는 
	// @ModelAttribute를 이용한 커맨드 객체 사용
	
	// 원시타입 자료형을 전달받을 경우에는
	// @RequestParam을 이용
	
	
	// @SessionAttributes 사용하기
	@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public String memberLogin(Member mem , Model model) {
		
		Member loginUser = mService.loginMember(mem);
		
		if(loginUser != null) {
			model.addAttribute("loginUser", loginUser);
			//return "home"; // forward 방식
			return "redirect:home.kh"; // redirect 방식
		}else {
			model.addAttribute("msg", "로그인 실패!");
			return "common/errorPage";
		}
		//Model 객체는 전달하고자 하는 데이터를
		// 맵형식(K,V)으로 담아 전달는 객체로
		//scope는 request임
		  
	}
	
	
	/* ModelAndView 객체
	 * 
	 *  Model : 응답 페이지에 값을 전달할 때 맵 형식으로 저장하여 전달하는 객체
	 *  View : requestDispatcher의 forward 시 
	 *         이동할 페이지의 정보(url)을 담는 객체
	 *  
	 *  - Model은 따로 사용 가능하지만 View는 사용 불가능
	 *  	-> 대신 String 반환형으로 url 입력 시 forward 동작을 함.
	 * */
	
	/*@RequestMapping(value="login.kh", method=RequestMethod.POST)
	public ModelAndView memberLogin(Member mem , ModelAndView mv) {
		
		Member loginUser = mService.loginMember(mem);
		
		if(loginUser != null) {
			mv.addObject("loginUser", loginUser);
			mv.setViewName("home");
		}else {
			mv.addObject("msg", "로그인 실패!");
			mv.setViewName("common/errorPage");
		}
		
		return mv;
	}*/
	
	// 로그아웃
	@RequestMapping("logout.kh")
	public String memberLogout(SessionStatus status,
								HttpSession session) {
		// 세션의 상태를 관리할 수 있는 객체
		
		// @SessionAttributes를 사용하였을 때
		// Session을 만료 시키기 위해서는
		// status.setComplete()를 호출해야 함.
		status.setComplete();
		//session.invalidate();
		return "redirect:home.kh";
	}
	
	/* forward와 redirect 차이
	 * 
	 * forward  
	 *  요청을 한 페이지의 request, response 정보를 
	 *  그대로 클라이언트로 전달하고 주소값이 변하지 않음.
	 *  
	 * redirect
	 *  기존 request, response 객체가 삭제되고
	 *  지정한 주소(url)로 새로 요청을 보내어
	 *  새로운 request, response 객체를 생성하고
	 *  주소값이 바뀜.
	 * 
	 * */
	
	
	// ---------------------------------------------------------------------
	// 회원 가입
	
	// 회원가입 페이지로 뷰를 이동시키는 메소드 추가
	@RequestMapping("enrollView.kh")
	public String enrollView() {
		return "member/memberJoin";
	}
	
	
	// 회원가입 폼 작성 내용을 받아 회원가입 진행
	@RequestMapping(value="minsert.kh", method=RequestMethod.POST)
	public String memberInsert(Member mem, Model model,
							String post, String address1, String address2) {
		
		System.out.println(mem);
		
		mem.setAddress(post+","+address1+","+address2);
		
		System.out.println(mem);
		
		/* 1. 비밀번호를 평문으로 저장하면 안되나?
		 *   -> 평문으로 비밀번호 저장은 범죄행위 
		 * 
		 * 2. SHA 512 방식의 암호화
		 *  -> 단방향 해쉬함수
		 *     (암호화  O,  복호화 X)
		 *  문제점 : 같은 비밀번호는 암호화 내용(다이제스트)도 같음
		 *   -> 다이제스트가 많이 모이면 검색해서 찾을 수 있는 
		 *      경우가 발생함(해킹에 취약)
		 *   
		 *   - 일반적인 장비(개발자용)의 성능으로
		 *     1초에 56억개의 다이제스트르 비교할 수 있다.
		 *   
		 *   3. bcrypt 방식의 암호화 (salting 기법)
		 *  입력된 문자열을 암호화를 할때 
		 *  바로 해쉬함수에 대입하는것이 아닌
		 *  임의의 값(salt)을 문자열에 추가하여
		 *  암호화를 진행함. 
		 * */
		
		
		int result = mService.insertMember(mem);
		
		if(result > 0) {
			return "redirect:home.kh";
		}else {
			model.addAttribute("msg", "회원 가입 실패");
			return "common/errorPage";
		}
		
	}
	
	// 아이디 중복검사
	//@ResponseBody
	@RequestMapping("dupid.kh")
	@ResponseBody
	public String idDuplicateCheck(String id) {
		boolean isUsable 
		 	= mService.checkIdDup(id) == 0 ? true : false;
		System.out.println(isUsable);
		return isUsable+"";
		
	}
	
	
	
	// 회원 정보 페이지로 이동
	@RequestMapping("myinfo.kh")
	public String myInfoView() {
		return "member/mypage";
	}
	
	
	
	// 회원 정보 수정
	@RequestMapping("mupdate.kh")
	public String memberUpdate(Member mem, Model model,
							   String post,
							   String address1,
							   String address2,
							   RedirectAttributes rd) {
		// RedirectAttributes 
		// Redirect 시 데이터를 전달할 수 있는 객체
		
		// RedirectAttributes.addFlashAttribute()
		// Redirect로 데이터 전달 시 URL에 데이터가 노출되지 않게하는 메소드
		
		mem.setAddress(post+","+address1+","+address2);
		
		int result = mService.updateMember(mem);
		
		if(result > 0) {
			model.addAttribute("loginUser", mem);
			rd.addFlashAttribute("msg", "정보 수정 성공");
			return "redirect:home.kh";
		}else {
			model.addAttribute("msg", "정보 수정 실패");
			return "common/errorPage";
		}
		
	}
	
	// 회원 탈퇴
	@RequestMapping("mdelete.kh")
	public String memberDelete(String id, SessionStatus status, RedirectAttributes rdAttr, Model model) {
		int result = mService.deleteMember(id);
		
		if(result > 0) {
			rdAttr.addFlashAttribute("msg", "회원 탈퇴 성공");
			status.setComplete();
			return "redirect:home.kh";
		}else {
			model.addAttribute("msg", "회원 탈퇴 실패");
			return "common/errorPage";
		}
	}
	
}


