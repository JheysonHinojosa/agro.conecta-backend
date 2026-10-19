package com.agroconecta.estudent;

import org.springframework.boot.SpringApplication;

public class TestEstudentApplication {

	public static void main(String[] args) {
		SpringApplication.from(EstudentApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
