package com.gs.EnergiShare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnergiShareApplication {

	public static void main(String[] args) {
		SpringApplication.run(EnergiShareApplication.class, args);
	}

	public String home(){
		return "TPC - Global Solution 2024";
	}

}
