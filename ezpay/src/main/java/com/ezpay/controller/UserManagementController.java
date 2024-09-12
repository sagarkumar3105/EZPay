package com.ezpay.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.service.UserManagementService;

/**
 * Controller class responsible for handling user-related requests in the EZPay
 * application. It exposes endpoints for user login and registration.
 */
@RestController
public class UserManagementController {

	// Injecting the UserManagementService to handle business logic
	@Autowired
	private UserManagementService userManagementService;

	/**
	 * Handles GET requests to the root URL ("/"). This is a simple welcome message
	 * endpoint.
	 * 
	 * @return A welcome message for the EZPay application.
	 */
	@GetMapping("/")
	public String login() {
		return "Welcome to EZPay Application";
	}

	/**
	 * Handles POST requests to the "/login" URL. This endpoint is used for user
	 * authentication based on email/mobile number and user ID.
	 * 
	 * @param email_mobileNumber The email or mobile number of the user.
	 * @param userId             The user ID for authentication.
	 * @param password           The password for the user.
	 * @return A message indicating whether login was successful or not.
	 */
	@PostMapping("/login")
	public String loginUser(@RequestParam String email_mobileNumber, @RequestParam String userId,
			@RequestParam String password) {
		// Authenticate the user using the provided details
		if (userManagementService.authenticateUser(email_mobileNumber, userId, password)) {
			return "Login successful.";
		} else {
			return "Login failed! Please try again.";
		}
	}

	/**
	 * Handles POST requests to the "/register" URL. This endpoint is used for user
	 * registration with various details.
	 * 
	 * @param name            The name of the user.
	 * @param email           The email address of the user.
	 * @param mobileNumber    The mobile number of the user.
	 * @param password        The password chosen by the user.
	 * @param confirmPassword The password confirmation provided by the user.
	 * @param address         The address of the user.
	 * @param dob             The date of birth of the user.
	 * @param gender          The gender of the user.
	 * @param profilePicture  Optional profile picture of the user.
	 * @param accountNumber   The bank account number of the user.
	 * @param ifscCode        The IFSC code of the user's bank branch.
	 * @return A message indicating the result of the registration attempt.
	 */
	@PostMapping("/register")
	public String registerUser(@RequestParam String name, @RequestParam String email, @RequestParam String mobileNumber,
			@RequestParam String password, @RequestParam String confirmPassword, @RequestParam String address,
			@RequestParam String dob, @RequestParam String gender,
			@RequestParam(required = false) byte[] profilePicture, @RequestParam String accountNumber,
			@RequestParam String ifscCode) {

		// Delegate the registration logic to the UserManagementService
		return userManagementService.register(name, email, mobileNumber, password, confirmPassword, address, dob,
				gender, profilePicture, accountNumber, ifscCode);
	}
}
