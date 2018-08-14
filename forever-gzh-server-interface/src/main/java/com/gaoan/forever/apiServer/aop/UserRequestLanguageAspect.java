package com.gaoan.forever.apiServer.aop;

import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.alibaba.druid.support.http.WebStatFilter.StatHttpServletResponseWrapper;
import com.alibaba.fastjson.JSON;

/**
 * 实现Web层的日志切面
 * 
 * @author Angel(QQ:412887952)
 * @version v.0.1
 */
@Aspect
@Component
@Order(-5)
public class UserRequestLanguageAspect {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// spring单例,并发时成员变量是公用的,所以这里使用ThreadLocal,每个线程会有一个独立的变量
	private ThreadLocal<Long> startTimeLocal = new ThreadLocal<Long>();

	/**
	 * 定义一个切入点. 解释下：
	 *
	 * ~ 第一个 * 代表任意修饰符及任意返回值. ~ 第二个 * 任意包名 ~ 第三个 * 代表任意方法. ~ 第四个 * 定义在web包或者子包 ~
	 * 第五个 * 任意方法 ~ .. 匹配任意数量的参数.
	 */
	@Pointcut("execution(public * com.gaoan.forever.apiServer.controller..*.*.*(..))")
	public void webReq() {
	}

	@Before("webReq()")
	public void doBefore(JoinPoint joinPoint) {
		// 接收到请求，记录请求内容
		logger.info("UserRequestLanguageAspect.doBefore()");
		String classmethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();

		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();

		Locale locale = LocaleContextHolder.getLocale();
		String language = locale.getLanguage();

		// 记录下请求内容
		logger.info("URL : " + request.getRequestURL().toString());
		logger.info("HTTP_METHOD : " + request.getMethod());
		logger.info("IP : " + request.getRemoteAddr());
		logger.info("CLASS_METHOD : " + classmethod);
		logger.info("LANGUAGE: " + language);

		String remoteAddr = request.getHeader("forwarded-ip");
		logger.info("Header info ： --> headerName:{} --> headerValue:{}", "forwarded-ip", remoteAddr);

		if (joinPoint.getArgs() != null && joinPoint.getArgs().length > 0) {
			for (Object obj : joinPoint.getArgs()) {
				if (!(obj instanceof ShiroHttpServletRequest) && !(obj instanceof StatHttpServletResponseWrapper)
						&& !(obj instanceof StandardMultipartHttpServletRequest)) {
					logger.info("Request Url = {}, Body Param: {} ", request.getRequestURL().toString(),
							JSON.toJSONString(obj));
				}
			}
		}

		Enumeration<String> enu = request.getParameterNames();
		while (enu.hasMoreElements()) {
			String paraName = (String) enu.nextElement();
			logger.info("Request Url = {}, Query Param:{}", request.getRequestURL().toString(),
					paraName + ": " + request.getParameter(paraName));
		}

		startTimeLocal.set(System.currentTimeMillis());
	}

	@AfterReturning("webReq()")
	public void doAfterReturning(JoinPoint joinPoint) {
		// 处理完请求，返回内容
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Long endTime = System.currentTimeMillis();
		logger.info("Request Url: {}, Cost Time = {}", request.getRequestURL().toString(),
				(endTime - startTimeLocal.get()) + "ms");

		logger.info("UserRequestLanguageAspect.doAfterReturning()");
	}

}
