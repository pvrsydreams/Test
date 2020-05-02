package com.vpsy._2f;

import org.dozer.DozerBeanMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * @author punith
 * @date 23-Apr-2020
 * @description Start the application by running this file.
 */
@SpringBootApplication
public class Application {
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/* Declares a bean for DozerBeanMapper so that we can Autowire it */
	@Bean
	public DozerBeanMapper dozerBeanMapper() {
		return new DozerBeanMapper();
	}

	/* Declares a bean for MultipartResolver so that we can upload file */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
		/* The max size of the file is 20 MB */
		multipartResolver.setMaxUploadSize(20 * 1024 * 1024);
		return multipartResolver;

	}

	/* Declares a bean for filterMultipartResolver so that we can upload file */
//	@Bean(name = "filterMultipartResolver")
//	public CommonsMultipartResolver filterMultipartResolver() {
//		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//		/* The max size of the file is 20 MB */
//		multipartResolver.setMaxUploadSize(20 * 1024 * 1024);
//		return multipartResolver;
//
//	}
}


