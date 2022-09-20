package com.animal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, // spring boot 에서 default 사용하는 거 뺌
		MongoDataAutoConfiguration.class,  // 현재 하이버 네이트 방언이 Mysql 로 되어 있음.
		MultipartAutoConfiguration.class}) // Tomcat 의 Multipart Library 사용하기 때문에 spring boot 의 Multipart 기능을 exclude 함.
public class AnimalAdoptionApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(AnimalAdoptionApplication.class, args);
	}

	@Override protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(AnimalAdoptionApplication.class);
	}
}
