package com.ezpay.repo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Component responsible for managing the database connection. It initializes
 * the connection when the application context is refreshed and provides methods
 * to get and close the connection.
 */
@Component
public class DatabaseConnection {

	// Database connection properties injected from application properties
	@Value("${spring.datasource.url}")
	private String url;

	@Value("${spring.datasource.username}")
	private String username;

	@Value("${spring.datasource.password}")
	private String password;

	@Value("${spring.datasource.driver-class-name}")
	private String driverClassName;

	// Static connection instance for shared use
	private static Connection connection;

	/**
	 * Initializes the database connection when the application context is
	 * refreshed. This method is automatically invoked by Spring when the
	 * application context is fully initialized.
	 */
	@EventListener(ContextRefreshedEvent.class)
	public void initialize() {
		try {
			// Load the database driver class
			Class.forName(driverClassName);

			// Establish the connection to the database
			connection = DriverManager.getConnection(url, username, password);
			System.out.println("Database connection established successfully.");
		} catch (ClassNotFoundException e) {
			// Handle the case where the database driver class is not found
			System.err.println("Database driver class not found: " + e.getMessage());
		} catch (SQLException e) {
			// Handle SQL errors during connection establishment
			System.err.println("SQL error: " + e.getMessage());
		}
	}

	/**
	 * Provides access to the database connection.
	 * 
	 * @return The current database connection.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Closes the database connection if it is open. This method should be called
	 * when the application is shutting down or when the connection is no longer
	 * needed.
	 */
	public void closeConnection() {
		if (connection != null) {
			try {
				// Close the connection to free up database resources
				connection.close();
				System.out.println("Database connection closed.");
			} catch (SQLException e) {
				// Handle errors during connection closure
				System.err.println("Error closing connection: " + e.getMessage());
			}
		}
	}
}
