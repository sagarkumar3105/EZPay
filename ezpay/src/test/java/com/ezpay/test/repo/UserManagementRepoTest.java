package com.ezpay.test.repo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ezpay.repo.DatabaseConnection;
import com.ezpay.repo.UserManagementRepo;
import com.ezpay.utils.PasswordUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the UserManagementRepo class. Tests are conducted using
 * Mockito to mock dependencies and verify behavior.
 */
public class UserManagementRepoTest {

	@InjectMocks
	private UserManagementRepo userManagementRepo; // The class under test

	@Mock
	private DatabaseConnection databaseConnection; // Mock for the DatabaseConnection dependency

	@Mock
	private Connection connection; // Mock for SQL Connection

	@Mock
	private PreparedStatement preparedStatement; // Mock for SQL PreparedStatement

	@Mock
	private PreparedStatement preparedStatement1; // Mock for another SQL PreparedStatement

	@Mock
	private PreparedStatement preparedStatement2; // Mock for another SQL PreparedStatement

	@Mock
	private ResultSet resultSet; // Mock for SQL ResultSet

	@Mock
	private PreparedStatement preparedStatement3; // Mock for yet another SQL PreparedStatement

	/**
	 * Initializes mocks and sets up the common behavior before each test.
	 */
	@BeforeEach
	void setUp() throws SQLException {
		MockitoAnnotations.openMocks(this); // Initializes the mocks
		when(databaseConnection.getConnection()).thenReturn(connection); // Mock behavior for getting connection
	}

	/**
	 * Tests successful authentication by email. Verifies the interaction with the
	 * mocked objects and asserts that the authentication succeeds.
	 */
	@Test
	void testAuthenticateByEmailSuccess() throws SQLException {
		String email = "test@example.com";
		Long userId = 123L;
		String password = "password";
		byte[] hashedPassword = PasswordUtils.hashPassword(password).getBytes(); // Simulate stored hash

		when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getBytes("PASSWORD_HASH")).thenReturn(hashedPassword);

		boolean result = userManagementRepo.authenticateByEmail(email, userId, password);
		assertTrue(result);
		verify(preparedStatement).setString(1, email);
		verify(preparedStatement).setLong(2, userId);
		verify(preparedStatement).executeQuery();
	}

	/**
	 * Tests failed authentication by email. Verifies the interaction with the
	 * mocked objects and asserts that the authentication fails.
	 */
	@Test
	void testAuthenticateByEmailFailure() throws SQLException {
		String email = "test@example.com";
		Long userId = 123L;
		String password = "password";

		when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		boolean result = userManagementRepo.authenticateByEmail(email, userId, password);
		assertFalse(result);
		verify(preparedStatement).setString(1, email);
		verify(preparedStatement).setLong(2, userId);
		verify(preparedStatement).executeQuery();
	}

	/**
	 * Tests successful authentication by mobile number. Verifies the interaction
	 * with the mocked objects and asserts that the authentication succeeds.
	 */
	@Test
	void testAuthenticateByMobileNumberSuccess() throws SQLException {
		String mobileNumber = "1234567890";
		Long userId = 123L;
		String password = "password";
		byte[] hashedPassword = PasswordUtils.hashPassword(password).getBytes(); // Simulate stored hash

		when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getBytes("PASSWORD_HASH")).thenReturn(hashedPassword);

		boolean result = userManagementRepo.authenticateByMobileNumber(mobileNumber, userId, password);
		assertTrue(result);
		verify(preparedStatement).setString(1, mobileNumber);
		verify(preparedStatement).setLong(2, userId);
		verify(preparedStatement).executeQuery();
	}

	/**
	 * Tests failed authentication by mobile number. Verifies the interaction with
	 * the mocked objects and asserts that the authentication fails.
	 */
	@Test
	void testAuthenticateByMobileNumberFailure() throws SQLException {
		String mobileNumber = "1234567890";
		Long userId = 123L;
		String password = "password";

		when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement);
		when(preparedStatement.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(false);

		boolean result = userManagementRepo.authenticateByMobileNumber(mobileNumber, userId, password);
		assertFalse(result);
		verify(preparedStatement).setString(1, mobileNumber);
		verify(preparedStatement).setLong(2, userId);
		verify(preparedStatement).executeQuery();
	}

	/**
	 * Tests successful user registration. Verifies the interactions with the mocked
	 * PreparedStatement objects and asserts that the registration succeeds.
	 */
	@Test
	void testRegisterSuccess() throws SQLException {
		String name = "John Doe";
		String email = "john@example.com";
		String mobileNumber = "1234567890";
		byte[] passwordHash = PasswordUtils.hashPassword("password").getBytes();
		String address = "123 Street";
		Date dob = new Date(System.currentTimeMillis());
		String gender = "Male";
		byte[] profilePicture = null;
		int isProfileInfoSet = 0;
		String upiId = "1234567890@ezpay";
		String accountNumber = "account123";
		String ifscCode = "IFSC123";

		when(connection.prepareStatement(UserManagementRepo.REGISTER_MASTER_DATA)).thenReturn(preparedStatement1);
		when(connection.prepareStatement(UserManagementRepo.REGISTER_LOGIN_DATA)).thenReturn(preparedStatement2);
		when(connection.prepareStatement("SELECT CUSTOMER_ID_SEQ.CURRVAL FROM dual")).thenReturn(preparedStatement3);
		when(preparedStatement3.executeQuery()).thenReturn(resultSet);
		when(resultSet.next()).thenReturn(true);
		when(resultSet.getLong(1)).thenReturn(1L);
		when(preparedStatement1.executeUpdate()).thenReturn(1);
		when(preparedStatement2.executeUpdate()).thenReturn(1);

		boolean result = userManagementRepo.register(name, email, mobileNumber, passwordHash, address, dob, gender,
				profilePicture, isProfileInfoSet, upiId, accountNumber, ifscCode);

		assertTrue(result);
		verify(preparedStatement1).setString(1, name);
		verify(preparedStatement1).setString(2, email);
		verify(preparedStatement1).setString(3, mobileNumber);
		verify(preparedStatement1).setString(4, address);
		verify(preparedStatement1).setDate(5, dob);
		verify(preparedStatement1).setString(6, gender);
		verify(preparedStatement1).setInt(7, isProfileInfoSet);
		verify(preparedStatement1).setString(8, upiId);
		verify(preparedStatement1).setString(9, accountNumber);
		verify(preparedStatement1).setString(10, ifscCode);
		verify(preparedStatement1).executeUpdate();

		verify(preparedStatement2).setLong(1, 1L);
		verify(preparedStatement2).setBytes(2, passwordHash);
		verify(preparedStatement2).executeUpdate();
	}

	/**
	 * Tests failed user registration. Verifies the interactions with the mocked
	 * PreparedStatement objects and asserts that the registration fails.
	 */
	@Test
	void testRegisterFailure() throws SQLException {
		String name = "John Doe";
		String email = "john@example.com";
		String mobileNumber = "1234567890";
		byte[] passwordHash = PasswordUtils.hashPassword("passwordHash").getBytes();
		String address = "123 Street";
		Date dob = new Date(System.currentTimeMillis());
		String gender = "Male";
		byte[] profilePicture = null;
		int isProfileInfoSet = 0;
		String upiId = "1234567890@ezpay";
		String accountNumber = "account123";
		String ifscCode = "IFSC123";

		when(connection.prepareStatement(any(String.class))).thenReturn(preparedStatement1);
		when(preparedStatement1.executeUpdate()).thenReturn(0);

		boolean result = userManagementRepo.register(name, email, mobileNumber, passwordHash, address, dob, gender,
				profilePicture, isProfileInfoSet, upiId, accountNumber, ifscCode);

		assertFalse(result);
		verify(preparedStatement1).setString(1, name);
		verify(preparedStatement1).setString(2, email);
		verify(preparedStatement1).setString(3, mobileNumber);
		verify(preparedStatement1).setString(4, address);
		verify(preparedStatement1).setDate(5, dob);
		verify(preparedStatement1).setString(6, gender);
		verify(preparedStatement1).setInt(7, isProfileInfoSet);
		verify(preparedStatement1).setString(8, upiId);
		verify(preparedStatement1).setString(9, accountNumber);
		verify(preparedStatement1).setString(10, ifscCode);
		verify(preparedStatement1).executeUpdate();
	}
}
