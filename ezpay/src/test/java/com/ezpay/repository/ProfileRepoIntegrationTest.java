package com.ezpay.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.ezpay.entity.Profile;
import com.ezpay.repository.ProfileRepo;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional // Ensures database transactions are rolled back after each test
public class ProfileRepoIntegrationTest {

    @Autowired
    private ProfileRepo profileRepo;

    private Profile profile;

    @BeforeEach
    public void setUp() {
        profile = new Profile(1L, "John", "john@example.com", "1234567890", "123 Street");
        profileRepo.save(profile);
    }

    @AfterEach
    public void tearDown() {
        profileRepo.deleteAll();
    }

    @Test
    public void testFindByName() {
        // Author: Snehal
        // Description: Tests finding a profile by name.
        Profile foundProfile = profileRepo.findByName("John");

        assertNotNull(foundProfile);
        assertEquals("John", foundProfile.getName());
    }

    @Test
    public void testFindById() {
        // Author: Snehal
        // Description: Tests finding a profile by ID.

        // First, create and save a new profile
        Profile profile = new Profile(null, "John", "john@example.com", "1234567890", "123 Street");
        profileRepo.save(profile);  // Save the profile to get a generated ID

        // Retrieve the saved profile using the generated ID
        Optional<Profile> foundProfile = profileRepo.findById(profile.getId());

        // Check if the profile is present
        assertTrue(foundProfile.isPresent());

        // Verify that the retrieved profile's name matches the saved profile
        assertEquals("John", foundProfile.get().getName());
    }


    @Test
    public void testSaveProfile() {
        // Author: Snehal
        // Description: Tests saving a new profile.
        Profile newProfile = new Profile(null, "Jane", "jane@example.com", "0987654321", "456 Street");

        Profile savedProfile = profileRepo.save(newProfile);

        assertNotNull(savedProfile);
        assertEquals("Jane", savedProfile.getName());
    }

    @Test
    public void testDeleteProfile() {
        // Author: Snehal
        // Description: Tests deleting a profile by ID.
        profileRepo.deleteById(1L);

        Optional<Profile> deletedProfile = profileRepo.findById(1L);

        assertFalse(deletedProfile.isPresent());
    }
}
