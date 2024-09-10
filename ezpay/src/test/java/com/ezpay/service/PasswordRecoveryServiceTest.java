package com.ezpay.service;

import com.ezpay.entity.Customer;
import com.ezpay.entity.LoginData;
import com.ezpay.entity.PasswordRecoveryDetails;
import com.ezpay.repository.CustomerRepository;
import com.ezpay.repository.LoginDataRepository;
import com.ezpay.repository.PasswordRecoveryDetailsRepository;
import com.ezpay.service.PasswordRecoveryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for the PasswordRecoveryService class.
 * These tests ensure that the password recovery functionality behaves as expected.
 */
public class PasswordRecoveryServiceTest {

    @InjectMocks
    private PasswordRecoveryService passwordRecoveryService;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private PasswordRecoveryDetailsRepository passwordRecoveryDetailsRepository;

    @Mock
    private LoginDataRepository loginDataRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JavaMailSender mailSender;

    /**
     * Initializes the mocks before each test.
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the creation of a new password reset token when no existing token is present.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testCreatePasswordResetToken_NewToken() {
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);

        // Mocking the repository to return a customer for the given email
        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        // Mocking the repository to return no existing recovery details for the customer
        when(passwordRecoveryDetailsRepository.findByCustomer(customer)).thenReturn(Optional.empty());
        
        PasswordRecoveryDetails recoveryDetails = new PasswordRecoveryDetails();
        recoveryDetails.setToken(UUID.randomUUID().toString());  // Generating a new unique token
        recoveryDetails.setTokenCreationDate(LocalDateTime.now());  // Setting token creation time
        recoveryDetails.setTokenExpirationDate(LocalDateTime.now().plusHours(24));  // Token expires in 24 hours
        recoveryDetails.setIsTokenUsed(false);  // Token is not used yet

        // Mocking the save operation to return the newly created recovery details
        when(passwordRecoveryDetailsRepository.save(any(PasswordRecoveryDetails.class))).thenReturn(recoveryDetails);

        // Mocking the mail sender to do nothing when a mail is sent
        doNothing().when(mailSender).send(any(SimpleMailMessage.class));

        // Calling the service method
        String result = passwordRecoveryService.createPasswordResetToken(email);

        // Asserting that the result message is as expected
        assertEquals("Password reset link sent to test@example.com", result);
        // Verifying that the mail sender's send method was called once
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }

    /**
     * Tests the scenario where an existing valid token is present for the customer.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testCreatePasswordResetToken_ExistingValidToken() {
        String email = "test@example.com";
        Customer customer = new Customer();
        customer.setEmail(email);

        // Mocking the repository to return a customer for the given email
        PasswordRecoveryDetails existingDetails = new PasswordRecoveryDetails();
        existingDetails.setToken(UUID.randomUUID().toString());  // Existing token
        existingDetails.setTokenCreationDate(LocalDateTime.now().minusHours(1));  // Token created 1 hour ago
        existingDetails.setTokenExpirationDate(LocalDateTime.now().plusHours(23));  // Token still valid
        existingDetails.setIsTokenUsed(false);  // Token has not been used yet

        when(customerRepository.findByEmail(email)).thenReturn(Optional.of(customer));
        // Mocking the repository to return the existing recovery details for the customer
        when(passwordRecoveryDetailsRepository.findByCustomer(customer)).thenReturn(Optional.of(existingDetails));

        // Calling the service method
        String result = passwordRecoveryService.createPasswordResetToken(email);

        // Asserting that the result message indicates a token has already been sent
        assertEquals("Password reset link has already been sent to " + email, result);
        // Verifying that the mail sender's send method was never called
        verify(mailSender, never()).send(any(SimpleMailMessage.class));
    }

    /**
     * Tests the successful password reset using a valid token.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testResetPassword_Success() {
        String token = "valid-token";
        String newPassword = "newPassword";
        Customer customer = new Customer();
        LoginData loginData = new LoginData();
        loginData.setPassword("oldPassword");  // Initial password

        PasswordRecoveryDetails recoveryDetails = new PasswordRecoveryDetails();
        recoveryDetails.setToken(token);
        recoveryDetails.setTokenCreationDate(LocalDateTime.now().minusHours(1));  // Token created 1 hour ago
        recoveryDetails.setTokenExpirationDate(LocalDateTime.now().plusHours(23));  // Token still valid
        recoveryDetails.setIsTokenUsed(false);  // Token has not been used yet
        recoveryDetails.setCustomer(customer);

        // Mocking repository methods for existing recovery details and login data
        when(passwordRecoveryDetailsRepository.findByToken(token)).thenReturn(Optional.of(recoveryDetails));
        when(loginDataRepository.findByCustomer(customer)).thenReturn(Optional.of(loginData));
        // Mocking the password encoder to return the encoded password
        when(passwordEncoder.encode(newPassword)).thenReturn("encodedPassword");

        // Calling the service method
        String result = passwordRecoveryService.resetPassword(token, newPassword);

        // Asserting that the result message indicates success
        assertEquals("Password reset successfully.", result);
        // Asserting that the password has been updated to the encoded value
        assertEquals("encodedPassword", loginData.getPasswordHash());
        // Verifying that the recovery details and login data were saved
        verify(passwordRecoveryDetailsRepository, times(1)).save(recoveryDetails);
        verify(loginDataRepository, times(1)).save(loginData);
    }

    /**
     * Tests the scenario where the provided token is expired.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testResetPassword_TokenExpired() {
        String token = "expired-token";
        String newPassword = "newPassword";

        PasswordRecoveryDetails recoveryDetails = new PasswordRecoveryDetails();
        recoveryDetails.setToken(token);
        recoveryDetails.setTokenCreationDate(LocalDateTime.now().minusDays(2));  // Token created 2 days ago
        recoveryDetails.setTokenExpirationDate(LocalDateTime.now().minusDays(1));  // Token expired 1 day ago
        recoveryDetails.setIsTokenUsed(false);

        // Mocking the repository to return the expired token details
        when(passwordRecoveryDetailsRepository.findByToken(token)).thenReturn(Optional.of(recoveryDetails));

        // Asserting that an exception is thrown due to the expired token
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                passwordRecoveryService.resetPassword(token, newPassword));
        
        assertEquals("The reset link has expired.", thrown.getMessage());
    }

    /**
     * Tests the scenario where the provided token has already been used.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testResetPassword_TokenUsed() {
        String token = "used-token";
        String newPassword = "newPassword";

        PasswordRecoveryDetails recoveryDetails = new PasswordRecoveryDetails();
        recoveryDetails.setToken(token);
        recoveryDetails.setTokenCreationDate(LocalDateTime.now().minusHours(1));  // Token created 1 hour ago
        recoveryDetails.setTokenExpirationDate(LocalDateTime.now().plusHours(23));  // Token still valid
        recoveryDetails.setIsTokenUsed(true);  // Token has been used

        // Mocking the repository to return the used token details
        when(passwordRecoveryDetailsRepository.findByToken(token)).thenReturn(Optional.of(recoveryDetails));

        // Asserting that an exception is thrown due to the token being used
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                passwordRecoveryService.resetPassword(token, newPassword));

        assertEquals("The reset link has already been used.", thrown.getMessage());
    }

    /**
     * Tests the scenario where no account is found with the provided email.
     * 
     * @throws Exception if any error occurs
     */
    @Test
    void testCreatePasswordResetToken_NoAccountFound() {
        String email = "nonexistent@example.com";

        // Mocking the repository to return no customer for the given email
        when(customerRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Asserting that an exception is thrown due to no account being found
        IllegalArgumentException thrown = assertThrows(IllegalArgumentException.class, () ->
                passwordRecoveryService.createPasswordResetToken(email));

        assertEquals("No account found with the provided email or mobile number.", thrown.getMessage());
    }
}
