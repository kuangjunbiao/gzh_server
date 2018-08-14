package com.gaoan.forever;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.github.tobato.fastdfs.FdfsClientConfig;

/**
 * Created by keymean on 2016/4/16.
 */
@EnableAutoConfiguration
@SpringBootApplication
@ServletComponentScan
@Import(FdfsClientConfig.class)
@EnableTransactionManagement // 添加事务支持
@ComponentScan(basePackages = { "com.gaoan", "com.gaoan.forever", "com.gaoan.forever.apiServer",
		"com.gaoan.forever.apiServer.controller.**" })
public class APIServerApplication {

	/**
	 * 初始日志类
	 */
	private static final Logger logger = LoggerFactory.getLogger(APIServerApplication.class);

	static {

	}

	public static void main(String[] args) {
		logger.info("系统启动...");
		SpringApplication.run(APIServerApplication.class, args);
	}
}
