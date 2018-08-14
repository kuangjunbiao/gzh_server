package com.gaoan.forever.apiServer.config;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Predicate;

import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2 // 启用 Swagger
public class SwaggerConfig {

    @Bean
    public Docket createRestApi() {
	Predicate<RequestHandler> predicate = new Predicate<RequestHandler>() {
	    @Override
	    public boolean apply(RequestHandler input) {
		Class<?> declaringClass = input.declaringClass();
		if (declaringClass == BasicErrorController.class)// 排除
		    return false;
		if (declaringClass.isAnnotationPresent(RestController.class)) // 被注解的类
		    return true;
		if (input.isAnnotatedWith(ResponseBody.class)) // 被注解的方法
		    return true;
		return false;
	    }
	};
	return new Docket(DocumentationType.SWAGGER_2)
		.apiInfo(apiInfo())
		.useDefaultResponseMessages(false)
		.select()
		.apis(predicate)
		//.paths(PathSelectors.any())
		.build();
    }

    private ApiInfo apiInfo() {
	return new ApiInfoBuilder()
		.title("Forever2进销存系统接口提供")// 大标题
		.description("简单优雅的restful风格")
		.version("1.0")// 版本
		.build();
    }
}
