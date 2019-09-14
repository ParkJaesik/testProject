package com.kh.spring.common.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
public class AroundLog {
	
	@Around("CommonPointcut.allPointcut()")
	public Object aroundLog(ProceedingJoinPoint pp) throws Throwable{
		
			String methodName = pp.getSignature().getName();
			
			StopWatch stopWatch = new StopWatch();
			
			stopWatch.start();
			
			Object obj = pp.proceed();
			
			stopWatch.stop();
			
			System.out.println(methodName + "() 메소드 수행에 걸린 시간 : " +
			
			stopWatch.getTotalTimeMillis() + "(ms)초");
			
			return obj;
		}

}
