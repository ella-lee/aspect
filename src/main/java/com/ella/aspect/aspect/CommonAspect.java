package com.ella.aspect.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CommonAspect {
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * logging start/end of controller return Object(Required)
	 */
	@Around("execution(* com.ella.aspect.controller.*.*(..))")
	public Object serviceAround(ProceedingJoinPoint pjp) throws Throwable {
		logger.info("start - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());
		Object result = pjp.proceed();
		logger.info("finished - " + pjp.getSignature().getDeclaringTypeName() + " / " + pjp.getSignature().getName());

		return result;
	}
	
	
	/**
	 * @Pointcut 생성 샘플
	 * */
	@Pointcut("execution(public * *(..))") // the execution of any public method
	private void allTarget(){
	};
	
	@Around("allTarget()") 
	public Object testAround(ProceedingJoinPoint pjp) throws Throwable {
		// TODO
		return null;
	}

}
