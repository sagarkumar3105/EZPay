package com.ezpay.test.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ezpay.repo.UserManagementRepo;
import com.ezpay.service.UserManagementService;
import com.ezpay.utils.PasswordUtils;

/**
 * Unit tests for UserManagementService class. Tests various aspects of user
 * authentication and registration functionalities.
 */
public class UserManagementServiceTest {

	@InjectMocks
	private UserManagementService userManagementService; // The class under test

	@Mock
	private UserManagementRepo userManagementRepo; // Mock for the UserManagementRepo dependency

	/**
	 * Initializes mocks and sets up common behavior before each test.
	 */
	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this); // Initializes the mocks
	}

	/**
	 * Tests successful authentication by email. Verifies the interaction with the
	 * mocked UserManagementRepo and asserts successful authentication.
	 */
	@Test
	void testAuthenticateUserByEmailSuccess() {
		String email = "test@example.com";
		String userId = "123";
		String password = "password";

		when(userManagementRepo.authenticateByEmail(email, Long.parseLong(userId), password)).thenReturn(true);

		boolean result = userManagementService.authenticateUser(email, userId, password);

		assertEquals(true, result);
		verify(userManagementRepo).authenticateByEmail(email, Long.parseLong(userId), password);
	}

	/**
	 * Tests failed authentication by email. Verifies the interaction with the
	 * mocked UserManagementRepo and asserts failed authentication.
	 */
	@Test
	void testAuthenticateUserByEmailFailure() {
		String email = "test@example.com";
		String userId = "123";
		String password = "password";

		when(userManagementRepo.authenticateByEmail(email, Long.parseLong(userId), password)).thenReturn(false);

		boolean result = userManagementService.authenticateUser(email, userId, password);

		assertEquals(false, result);
		verify(userManagementRepo).authenticateByEmail(email, Long.parseLong(userId), password);
	}

	/**
	 * Tests successful authentication by mobile number. Verifies the interaction
	 * with the mocked UserManagementRepo and asserts successful authentication.
	 */
	@Test
	void testAuthenticateUserByMobileNumberSuccess() {
		String mobileNumber = "1234567890";
		String userId = "123";
		String password = "password";

		when(userManagementRepo.authenticateByMobileNumber(mobileNumber, Long.parseLong(userId), password))
				.thenReturn(true);

		boolean result = userManagementService.authenticateUser(mobileNumber, userId, password);

		assertEquals(true, result);
		verify(userManagementRepo).authenticateByMobileNumber(mobileNumber, Long.parseLong(userId), password);
	}

	/**
	 * Tests failed authentication by mobile number. Verifies the interaction with
	 * the mocked UserManagementRepo and asserts failed authentication.
	 */
	@Test
	void testAuthenticateUserByMobileNumberFailure() {
		String mobileNumber = "1234567890";
		String userId = "123";
		String password = "password";

		when(userManagementRepo.authenticateByMobileNumber(mobileNumber, Long.parseLong(userId), password))
				.thenReturn(false);

		boolean result = userManagementService.authenticateUser(mobileNumber, userId, password);

		assertEquals(false, result);
		verify(userManagementRepo).authenticateByMobileNumber(mobileNumber, Long.parseLong(userId), password);
	}

	/**
	 * Tests successful user registration. Verifies the interaction with the mocked
	 * UserManagementRepo and asserts successful registration.
	 */
	@Test
	void testRegisterSuccess() throws ParseException {
		String name = "UserXYZ";
		String email = "testUserXYZ@example.com";
		String mobileNumber = "9999999999";
		String password = "password";
		String confirmPassword = "password";
		String address = "123 Street";
		String dob = "01-Jan-90";
		String gender = "Male";
		byte[] profilePicture = null;
		int isProfileInfoSet = 0;
		String accountNumber = "accountTest123";
		String ifscCode = "IFSC123";

		byte[] passwordHash = PasswordUtils.hashPassword(password).getBytes();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		Date dateOfBirth = formatter.parse(dob);
		java.sql.Date sqlDob = new java.sql.Date(dateOfBirth.getTime());
		String upiId = mobileNumber + "@ezpay";

		when(userManagementRepo.register(eq(name), eq(email), eq(mobileNumber), any(byte[].class), eq(address),
				eq(sqlDob), eq(gender), eq(profilePicture), eq(isProfileInfoSet), eq(upiId), eq(accountNumber),
				eq(ifscCode))).thenReturn(true);

		String result = userManagementService.register(name, email, mobileNumber, password, confirmPassword, address,
				dob, gender, profilePicture, accountNumber, ifscCode);

		assertEquals("You have successfully registered to EZPay application.", result);
	}

	/**
	 * Tests failed user registration. Verifies the interaction with the mocked
	 * UserManagementRepo and asserts failed registration.
	 */
	@Test
	void testRegisterFailure() throws ParseException {
		String name = "UserXYZ";
		String email = "testUserXYZ@example.com";
		String mobileNumber = "9999999999";
		String password = "password";
		String confirmPassword = "password";
		String address = "123 Street";
		String dob = "01-Jan-90";
		String gender = "Male";
		byte[] profilePicture = null;
		int isProfileInfoSet = 0;
		String accountNumber = "accountTest123";
		String ifscCode = "IFSC123";

		byte[] passwordHash = PasswordUtils.hashPassword(password).getBytes();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yy");
		Date dateOfBirth = formatter.parse(dob);
		java.sql.Date sqlDob = new java.sql.Date(dateOfBirth.getTime());
		String upiId = mobileNumber + "@ezpay";

		when(userManagementRepo.register(name, email, mobileNumber, passwordHash, address, sqlDob, gender,
				profilePicture, isProfileInfoSet, upiId, accountNumber, ifscCode)).thenReturn(false);

		String result = userManagementService.register(name, email, mobileNumber, password, confirmPassword, address,
				dob, gender, profilePicture, accountNumber, ifscCode);
		assertEquals("Registration failed.", result);
	}

	/**
	 * Tests registration with invalid email. Verifies that the service correctly
	 * handles an invalid email address.
	 */
	@Test
	void testRegisterInvalidEmail() {
		String name = "John Doe";
		String email = "invalid-email";
		String mobileNumber = "1234567890";
		String password = "password";
		String confirmPassword = "password";
		String address = "123 Street";
		String dob = "01-Jan-90";
		String gender = "Male";
		byte[] profilePicture = null;
		String accountNumber = "account123";
		String ifscCode = "IFSC123";

		String result = userManagementService.register(name, email, mobileNumber, password, confirmPassword, address,
				dob, gender, profilePicture, accountNumber, ifscCode);

		assertEquals("Please enter a valid email id.", result);
	}

	/**
	 * Tests registration with invalid mobile number. Verifies that the service
	 * correctly handles an invalid mobile number.
	 */
	@Test
	void testRegisterInvalidMobileNumber() {
		String name = "John Doe";
		String email = "john@example.com";
		String mobileNumber = "123456";
		String password = "password";
		String confirmPassword = "password";
		String address = "123 Street";
		String dob = "01-Jan-90";
		String gender = "Male";
		byte[] profilePicture = null;
		String accountNumber = "account123";
		String ifscCode = "IFSC123";

		String result = userManagementService.register(name, email, mobileNumber, password, confirmPassword, address,
				dob, gender, profilePicture, accountNumber, ifscCode);

		assertEquals("Please enter a 10-digit phone number.", result);
	}

	/**
	 * Tests registration with mismatched passwords. Verifies that the service
	 * correctly handles password mismatch errors.
	 */
	@Test
	void testRegisterPasswordMismatch() {
		String name = "John Doe";
		String email = "john@example.com";
		String mobileNumber = "1234567890";
		String password = "password";
		String confirmPassword = "differentPassword";
		String address = "123 Street";
		String dob = "01-Jan-90";
		String gender = "Male";
		byte[] profilePicture = null;
		String accountNumber = "account123";
		String ifscCode = "IFSC123";

		String result = userManagementService.register(name, email, mobileNumber, password, confirmPassword, address,
				dob, gender, profilePicture, accountNumber, ifscCode);

		assertEquals("Passwords do not match!", result);
	}
}
