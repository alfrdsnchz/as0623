package com.miggens.toolrental;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.miggens.toolrental.controller.MainController;
import com.miggens.toolrental.controller.RentalController;

@SpringBootTest
class ToolRentalApplicationTests {

	@Autowired
	MainController mainController; 

	@Autowired
	RentalController rentalController; 

	@Test
	void contextLoads() {
		Assertions.assertThat(mainController).isNotNull(); 
		Assertions.assertThat(rentalController).isNotNull(); 
	}

}
