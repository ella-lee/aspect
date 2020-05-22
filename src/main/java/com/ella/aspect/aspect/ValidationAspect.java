package com.ella.aspect.aspect;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import com.ella.aspect.model.SampleObject;
import com.ella.aspect.service.SampleService;

@Aspect
@Component
public class ValidationAspect {
	private final SampleService sampleService;

	public ValidationAspect(SampleService sampleService) {
		this.sampleService = sampleService;
	}

	Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * @Around
	 * RequestBody BindingModel Validation methods : create(), update()
	 */
	@Around("execution(* com.ella.aspect.*.*(..))")
	protected Object validateBean(ProceedingJoinPoint joinPoint) throws Throwable {
		logger.info("ValidationAspect - validateBean");
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			if (arg instanceof BindingResult) {
				BindingResult result = (BindingResult) arg;
				if (result.hasErrors()) {
					List<ObjectError> list = result.getAllErrors();
					StringBuilder errorMessage = new StringBuilder();
					for (ObjectError e : list) {
						errorMessage.append(String.format("%s\n", e.getDefaultMessage()));
					}
//					throw new WebException(HttpStatus.BAD_REQUEST, new Exception("invalid data :" + errorMessage));
				}
				break;
			}
		}
		return joinPoint.proceed();
	}

	/**
	 * @Before 
	 * method parameter validation 
	 * additional validation
	 */
	@Before("@annotation(com.ella.aspect.SampleAspect)&&args(id,..)")
	public void aspectBefore(JoinPoint joinPoint, Long id) throws Throwable {
		logger.info("aspectBefore");

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		AspectAnnotation aspectAnnotation = method.getAnnotation(AspectAnnotation.class);
		String key = aspectAnnotation.key();

		Optional<SampleObject> object = sampleService.get(id);

		// ELLA parameter validation
		if (!object.isPresent()) {
			logger.info(method.getName());
//			throw new WebException(HttpStatus.NOT_FOUND, new Exception("id not exists"));
		}

		// ELLA additional validation
		if (key.equals("owner")) {
			String owner = object.get().getOwner();
			if (!owner.equals("ella")) {
				logger.info(method.getName() + " : " + owner);
//			throw new WebException(HttpStatus.FORBIDDEN, new Exception("validation failed"));
			}
		}

	}

	/**
	 * @AfterReturning 
	 * return value validation
	 */
	@AfterReturning(pointcut = "@annotation(com.ella.aspect.AspectAnnotation)", returning = "returnVal")
	public void aspectAfterReturning(JoinPoint joinPoint, Object returnVal) throws Throwable {
		logger.info("aspectAfterReturning");

		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		Method method = signature.getMethod();
		AspectAnnotation aspectAnnotation = method.getAnnotation(AspectAnnotation.class);
		String key = aspectAnnotation.key();

		boolean result = false;

		switch (key) {
		case "A":
			// TODO
			result = true;
			break;
		case "B":
			// TODO
			result = true;
			break;
		default:
			// TODO
		}

		if (!result) {
			// TODO
//			throw new WebException(HttpStatus.FORBIDDEN, new Exception("validation failed"));
		}
	}

}
