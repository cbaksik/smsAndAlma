package edu.harvard.textsms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;


@PropertySource("classpath:application.properties")
@SpringBootApplication
public class TextSmsApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextSmsApplication.class, args);
	}
}
