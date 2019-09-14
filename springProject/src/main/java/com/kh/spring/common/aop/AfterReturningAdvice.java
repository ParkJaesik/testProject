package com.kh.spring.common.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import com.kh.spring.member.model.vo.Member;

@Component
@Aspect
public class AfterReturningAdvice {
	
	//로그인 동작에서만 아래 출력문이 출력될 수 있도록 Pointcut 지정하기
	
	
	@AfterReturning(pointcut="CommonPointcut.loginPointcut()",returning="returnObj")
	public void afterLog(JoinPoint jp,Object returnObj) {
		//로그인
		//관리자 - > 관리자님 환영합니다.
		//일반회원 -> Id : XXX로그인
		
		String methodName = jp.getSignature().getName();
		System.out.println("[log] : " + methodName + "()메소드 실행");
		
		if(returnObj instanceof Member) {
			Member m = (Member)returnObj;
			if(m.getId().equals("admin")) {
				System.out.println("[log]: 관리자님 환영합니다.");
			}else {
				System.out.println("[log] : " + m.getId() + "로그인");
			}
		}
	}
	
}
