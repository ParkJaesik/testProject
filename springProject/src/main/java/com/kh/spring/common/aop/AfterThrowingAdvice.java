package com.kh.spring.common.aop;

import java.sql.SQLException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class AfterThrowingAdvice {
	
	
	
	@AfterThrowing(pointcut="CommonPointcut.allPointcut()",throwing="exceptionObj")
	public void exceptionLog(JoinPoint jp,Exception exceptionObj) {
		
		String methodName = jp.getSignature().getName();
		
		String msg = methodName + "() 메소드 수행중 예외 발생 ==>";
		
		if(exceptionObj instanceof IllegalArgumentException) {
			msg += "부적한값 입력";
			
		}else if(exceptionObj instanceof SQLException) {
			msg += "DB 관련 예외 발생";
		}else if(exceptionObj instanceof NullPointerException) {
			msg += "null인 객체를 참조";
		}else {
			msg +="예외 발생";
		}
		
		System.out.println(msg);
	}
	
	
}
