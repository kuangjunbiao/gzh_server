package com.gaoan.forever.apiServer.interceptor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class MvcConfigurer extends WebMvcConfigurerAdapter {

	@Override
	public void configurePathMatch(PathMatchConfigurer configurer) {
		super.configurePathMatch(configurer);
		configurer.setUseSuffixPatternMatch(false);
	}

	/**
	 * 配置拦截器
	 *
	 * @param registry
	 * @author lance
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(getServerInterceptor()).addPathPatterns("/**") // 拦截所有
				.excludePathPatterns("/api/serverStop").excludePathPatterns("/api/user/loginout")
				.excludePathPatterns("/api/unauthorized").excludePathPatterns("/static_code")
				.excludePathPatterns("/logout");
	}

	// 配置日志拦截器bin
	@Bean
	public ServerInterceptor getServerInterceptor() {
		return new ServerInterceptor();
	}
}