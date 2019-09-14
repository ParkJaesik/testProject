package com.kh.spring.common.aop;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class BeforeAdviceOutAspect {
	
	
	@Before("CommonPointcut.allPointcut()")
	public void beforeLog(JoinPoint jp) {
		
		String methodName = jp.getSignature().getName();
		Object[] args = jp.getArgs();
		
		String printLog = "[전처리 :"  + methodName  + "()";
		
		if(args != null && args.length !=0) {
			printLog += " / args정보 : " + args[0].toString();
			
			
		}
		
		
		System.out.println(printLog + "실행");
		
	}
	
	
}
