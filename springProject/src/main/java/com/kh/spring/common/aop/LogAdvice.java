package com.kh.spring.common.aop;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect //Advice +  Pointcut
public class LogAdvice {
	
	
	//Pointcut 지정 : advice 적용 부분 지정
	//			접근제한자
	/*
	 * @Pointcut("execution(* com.kh.spring..*Impl.*(..))") public void
	 * allPointcut() {}
	 */
	
	@Before("CommonPointcut.allPointcut()")
	public void printLog() {
		/* System.out.println("[공통 Log] : 비즈니스 로직"); */
	}
	@After("CommonPointcut.allPointcut()")
	public void finallyLog() {
		//@After는 예외 발생여부 관계 없이 무조건 실행됨.
		/* System.out.println("[공통 Log] : 비즈니스 로직 종료"); */
	}
}
