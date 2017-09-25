package edu.harvard.textsms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;


@PropertySource("classpath:application.properties")
@SpringBootApplication
public class TextSmsApplication {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Value("${config.location.origin.prod}")
	private String originPROD;
	
	@Value("${config.location.origin.qa}")
	private String originQA;
	
	@Value("${config.location.origin.dev}")
	private String originDEV;

	public static void main(String[] args) {
		
		SpringApplication.run(TextSmsApplication.class, args);
	}
	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
	     return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	        		
	        		logger.debug("Origin request allow from Prod : " + originPROD);
	        		logger.debug("Origin request allow from QA : " + originQA);
	        		logger.debug("Origin request allow from DEV : " + originDEV);
	        	
	            registry.addMapping("/**/**").allowedOrigins(originPROD, originQA, originDEV)
	            .allowCredentials(true)
	            .allowedMethods("GET","POST","DELETE");
	
	        }
	     };
	 }
}
