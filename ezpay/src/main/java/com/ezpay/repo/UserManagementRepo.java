package com.ezpay.repo;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ezpay.utils.PasswordUtils;

/**
 * Repository for managing user-related database operations. This includes
 * authentication and registration of users.
 */
@Repository
public class UserManagementRepo {

	// Injected DatabaseConnection to manage database connections
	@Autowired
	private DatabaseConnection databaseConnection;

	private Connection connection;

	// SQL queries for login and registration operations
	public static final String LOGIN_BY_EMAIL = "SELECT USER_ID, ld.PASSWORD_HASH FROM LOGIN_DATA ld "
			+ "JOIN MASTER_DATA md ON ld.CUSTOMER_ID = md.CUSTOMER_ID " + "WHERE md.EMAIL = ? AND ld.USER_ID = ?";

	public static final String LOGIN_BY_MOBILE_NUMBER = "SELECT USER_ID, ld.PASSWORD_HASH FROM LOGIN_DATA ld "
			+ "JOIN MASTER_DATA md ON ld.CUSTOMER_ID = md.CUSTOMER_ID "
			+ "WHERE md.MOBILE_NUMBER = ? AND ld.USER_ID = ?";

	public static final String REGISTER_MASTER_DATA = "INSERT into MASTER_DATA "
			+ "values(CUSTOMER_ID_SEQ.NEXTVAL, ?, ?, ?, ?, ?, ?, "
			+ "CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL, ?, ?, ?, ?)";

	public static final String REGISTER_LOGIN_DATA = "INSERT into LOGIN_DATA values(?, LOGIN_DATA_SEQ.NEXTVAL, ?)";

	/**
	 * Authenticates a user based on their email, user ID, and password.
	 * 
	 * @param email    The email address of the user.
	 * @param userId   The user ID.
	 * @param password The plaintext password to verify.
	 * @return True if authentication is successful, false otherwise.
	 */
	public boolean authenticateByEmail(String email, Long userId, String password) {
		try {
			connection = databaseConnection.getConnection();
			PreparedStatement stmt = connection.prepareStatement(LOGIN_BY_EMAIL);
			stmt.setString(1, email);
			stmt.setLong(2, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					byte[] storedHashBytes = rs.getBytes("PASSWORD_HASH");
					String storedPasswordHash = new String(storedHashBytes, "UTF-8"); // Convert bytes to string
					return PasswordUtils.verifyPassword(password, storedPasswordHash);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Authenticates a user based on their mobile number, user ID, and password.
	 * 
	 * @param mobileNumber The mobile number of the user.
	 * @param userId       The user ID.
	 * @param password     The plaintext password to verify.
	 * @return True if authentication is successful, false otherwise.
	 */
	public boolean authenticateByMobileNumber(String mobileNumber, Long userId, String password) {
		try {
			connection = databaseConnection.getConnection();
			PreparedStatement stmt = connection.prepareStatement(LOGIN_BY_MOBILE_NUMBER);
			stmt.setString(1, mobileNumber);
			stmt.setLong(2, userId);
			try (ResultSet rs = stmt.executeQuery()) {
				if (rs.next()) {
					byte[] storedHashBytes = rs.getBytes("PASSWORD_HASH");
					String storedPasswordHash = new String(storedHashBytes, "UTF-8"); // Convert bytes to string
					return PasswordUtils.verifyPassword(password, storedPasswordHash);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Registers a new user in the database.
	 * 
	 * @param name             The user's name.
	 * @param email            The user's email.
	 * @param mobileNumber     The user's mobile number.
	 * @param passwordHash     The hashed password for the user.
	 * @param address          The user's address.
	 * @param dob              The user's date of birth.
	 * @param gender           The user's gender.
	 * @param profilePicture   Optional profile picture.
	 * @param isProfileInfoSet Indicates whether profile information is set.
	 * @param upiId            The user's UPI ID.
	 * @param accountNumber    The user's account number.
	 * @param ifscCode         The user's IFSC code.
	 * @return True if registration is successful, false otherwise.
	 */
	public boolean register(String name, String email, String mobileNumber, byte[] passwordHash, String address,
			Date dob, String gender, byte[] profilePicture, int isProfileInfoSet, String upiId, String accountNumber,
			String ifscCode) {
		try {
			connection = databaseConnection.getConnection();
			connection.setAutoCommit(false); // Begin transaction

			// Insert into MASTER_DATA
			PreparedStatement stmt1 = connection.prepareStatement(REGISTER_MASTER_DATA);
			stmt1.setString(1, name);
			stmt1.setString(2, email);
			stmt1.setString(3, mobileNumber);
			stmt1.setString(4, address);
			stmt1.setDate(5, dob);
			stmt1.setString(6, gender);
			stmt1.setInt(7, isProfileInfoSet);
			stmt1.setString(8, upiId);
			stmt1.setString(9, accountNumber);
			stmt1.setString(10, ifscCode);
			if (stmt1.executeUpdate() > 0) {
				// Retrieve the generated customer ID
				String getCustomerIdQuery = "SELECT CUSTOMER_ID_SEQ.CURRVAL FROM dual";
				PreparedStatement getCustomerId = connection.prepareStatement(getCustomerIdQuery);
				ResultSet rs = getCustomerId.executeQuery();
				if (rs.next()) {
					long customerId = rs.getLong(1);

					// Insert into LOGIN_DATA
					PreparedStatement stmt2 = connection.prepareStatement(REGISTER_LOGIN_DATA);
					stmt2.setLong(1, customerId);
					stmt2.setBytes(2, passwordHash);
					if (stmt2.executeUpdate() > 0) {
						connection.commit(); // Commit transaction if successful
						return true;
					} else {
						connection.rollback(); // Rollback transaction if login data insertion fails
					}
				}
			} else {
				connection.rollback(); // Rollback transaction if master data insertion fails
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (connection != null) {
					connection.rollback(); // Ensure rollback in case of error
//                    connection.close(); // Close connection, commented out for testing
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
}
