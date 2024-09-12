package com.ezpay.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezpay.repo.UserManagementRepo;
import com.ezpay.utils.PasswordUtils;

/**
 * Service class for handling user management operations including
 * authentication and registration.
 */
@Service
public class UserManagementService {

	// Injected repository for database operations related to user management
	@Autowired
	private UserManagementRepo userManagementRepo;

	/**
	 * Authenticates a user based on their email or mobile number, user ID, and
	 * password.
	 * 
	 * @param userName The email or mobile number of the user.
	 * @param user_Id  The user ID.
	 * @param password The plaintext password to verify.
	 * @return True if authentication is successful, false otherwise.
	 */
	public boolean authenticateUser(String userName, String user_Id, String password) {

		// Trim and parse userId
		Long userId = Long.parseLong(user_Id.trim());
		userName = userName.trim();

		// Determine authentication method based on userName format
		if (userName.contains("@")) {
			// Authenticate using email
			return userManagementRepo.authenticateByEmail(userName, userId, password);
		} else {
			// Authenticate using mobile number
			return userManagementRepo.authenticateByMobileNumber(userName, userId, password);
		}
	}

	/**
	 * Registers a new user with the provided details.
	 * 
	 * @param name            The user's name.
	 * @param email           The user's email address.
	 * @param mobileNumber    The user's mobile number.
	 * @param password        The plaintext password.
	 * @param confirmPassword The password confirmation.
	 * @param address         The user's address.
	 * @param dob             The user's date of birth in dd-MMM-yy format.
	 * @param gender          The user's gender.
	 * @param profilePicture  Optional profile picture as a byte array.
	 * @param accountNumber   The user's account number.
	 * @param ifscCode        The user's IFSC code.
	 * @return A message indicating the success or failure of the registration.
	 */
	public String register(String name, String email, String mobileNumber, String password, String confirmPassword,
			String address, String dob, String gender, byte[] profilePicture, String accountNumber, String ifscCode) {

		// Trim input values for consistency
		email = email.trim();
		mobileNumber = mobileNumber.trim();
		dob = dob.trim();

		// Validate email format
		if (!email.contains("@")) {
			return "Please enter a valid email id.";
		}

		// Validate mobile number format
		if (!(mobileNumber.length() == 10 && Pattern.matches("[0-9]{10}", mobileNumber))) {
			return "Please enter a 10-digit phone number.";
		}

		// Validate that passwords match
		if (!password.equalsIgnoreCase(confirmPassword)) {
			return "Passwords do not match!";
		}

		// Hash the password for storage
		byte[] passwordHash = PasswordUtils.hashPassword(password).getBytes();

		// Parse date of birth
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		Date dateOfBirth;
		java.sql.Date sqlDob = null;
		try {
			dateOfBirth = formatter.parse(dob);
			sqlDob = new java.sql.Date(dateOfBirth.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
			return "Invalid date format. Please use dd-MMM-yy.";
		}

		// Generate UPI ID and set profile info status
		String upiId = mobileNumber + "@ezpay";
		int isProfileInfoSet = (profilePicture != null) ? 1 : 0;

		// Register the user via the repository
		if (userManagementRepo.register(name, email, mobileNumber, passwordHash, address, sqlDob, gender,
				profilePicture, isProfileInfoSet, upiId, accountNumber, ifscCode)) {
			return "You have successfully registered to EZPay application.";
		} else {
			return "Registration failed.";
		}
	}
}
