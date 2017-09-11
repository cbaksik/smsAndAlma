package edu.harvard.textsms;

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
	
	@Value("${config.location.origin}")
	private String locationOrigin;

	public static void main(String[] args) {
		
		SpringApplication.run(TextSmsApplication.class, args);
	}
	
	 @Bean
	 public WebMvcConfigurer corsConfigurer() {
	     return new WebMvcConfigurerAdapter() {
	        @Override
	        public void addCorsMappings(CorsRegistry registry) {
	        		
	        		System.out.println(locationOrigin);
	        	
	            registry.addMapping("/**/**").allowedOrigins(locationOrigin)
	            .allowCredentials(true)
	            .allowedMethods("GET","POST","DELETE");
	
	        }
	     };
	 }
}
