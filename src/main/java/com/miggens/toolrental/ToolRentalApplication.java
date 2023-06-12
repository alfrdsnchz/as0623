package com.miggens.toolrental;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootApplication
public class ToolRentalApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToolRentalApplication.class, args);
	}

	@Bean
	public ObjectMapper mapper() {
		ObjectMapper mapper = JsonMapper.builder().addModule(new JavaTimeModule()).build();
		return mapper;  
	} 
}
