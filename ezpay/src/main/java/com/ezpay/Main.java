package com.ezpay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/**
 * Main entry point for the EzPayApplication. This class bootstraps the Spring Boot application
 * and initializes all the necessary components.
 */
@SpringBootApplication
public class EzPayApplication {
	/**
     * Main method that serves as the entry point of the Spring Boot application.
     * It starts the application by invoking {@link SpringApplication#run(Class, String[])}.
     *
     * @param args command-line arguments passed to the application
     */
	public static void main(String[] args) {
		SpringApplication.run(EzPayApplication.class, args);
	}

}
