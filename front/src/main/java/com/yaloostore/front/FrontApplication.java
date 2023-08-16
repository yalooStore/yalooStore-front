package com.yaloostore.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@ConfigurationPropertiesScan
public class FrontApplication {
	public static void main(String[] args) {
		SpringApplication.run(FrontApplication.class, args);
	}

	}

