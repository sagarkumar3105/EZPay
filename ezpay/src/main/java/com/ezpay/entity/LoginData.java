package com.ezpay.entity;

import javax.persistence.*;

/**
 * Entity class representing the login data for a customer in the EzPay
 * application. This class contains information related to the customer's login
 * credentials, such as user ID and password hash.
 */
@Entity
public class LoginData {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "login_data_seq")
	@SequenceGenerator(name = "login_data_seq", sequenceName = "login_data_seq", allocationSize = 1)
	private Long userId;

	@OneToOne
	@JoinColumn(name = "customer_id")
	private Customer customer; // Links login data to the customer entity

	private String email_mobileNumber; // Checks the email or mobile number

	private String passwordHash; // Stores the hashed password for security purposes

	// Getters and Setters
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public String getEmail_mobileNumber() {
		return email_mobileNumber;
	}

	public void setEmail_mobileNumber(String email_mobileNumber) {
		this.email_mobileNumber = email_mobileNumber;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPassword(String encodedPassword) {
		this.passwordHash = encodedPassword; // Set the encoded password
	}
}