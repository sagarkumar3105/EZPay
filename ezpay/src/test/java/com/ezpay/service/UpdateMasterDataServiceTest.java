package com.ezpay.service;
import com.ezpay.model.Customer;
import com.ezpay.repo.MasterDataRepository;
import com.ezpay.service.UpdateMasterDataService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class UpdateMasterDataServiceTest {

    private UpdateMasterDataService updateService;
    private MasterDataRepository mockRepository;

    @Before
    public void setUp() {
        // Create a mock repository
        mockRepository = Mockito.mock(MasterDataRepository.class);

        // Initialize the service with the mock repository
        updateService = new UpdateMasterDataService(mockRepository);
    }

    @Test
    public void testUpdateName_Success() {
        // Given
        int customerId = 1;
        String newName = "Johnathan Doe";
        Customer existingCustomer = new Customer(
            customerId, 
            "OldName", 
            "oldemail@example.com", 
            "1234567890", 
            "123 Elm Street", 
            "1990-01-01", 
            "Male", 
            "2024-08-27 10:00:00", 
            "2024-08-27 10:00:00", 
            null, 
            true, 
            "UPI1234567", 
            "ACC1234567", 
            "IFSC12345",
            1 // Example accountType
        );

        // Mock the repository method
        when(mockRepository.getUserById(customerId)).thenReturn(existingCustomer);

        // When
        updateService.updateName(customerId, newName);

        // Then
        verify(mockRepository).updateName(customerId, newName);
    }

    @Test
    public void testUpdateEmail() {
        // Given
        int customerId = 1;
        String newEmail = "johnathan.doe@example.com";
        String oldMobileNumber = "1234567890";

        // When
        updateService.updateEmail(customerId, newEmail, oldMobileNumber);

        // Then
        verify(mockRepository).updateEmail(customerId, newEmail, oldMobileNumber);
    }

    @Test
    public void testUpdateMobileNumber() {
        // Given
        int customerId = 1;
        String newMobileNumber = "0987654321";
        String oldEmail = "oldemail@example.com";

        // When
        updateService.updateMobileNumber(customerId, newMobileNumber, oldEmail);

        // Then
        verify(mockRepository).updateMobileNumber(customerId, newMobileNumber, oldEmail);
    }

//   

    @Test
    public void testUpdateNonExistentCustomer() {
        // Given
        int nonExistentCustomerId = 999;
        String newName = "Non-existent Customer";

        // When
        updateService.updateName(nonExistentCustomerId, newName);

        // Then
        verify(mockRepository).updateName(nonExistentCustomerId, newName);
        // Further verification might be needed to check how the service handles non-existent customers
    }
}
