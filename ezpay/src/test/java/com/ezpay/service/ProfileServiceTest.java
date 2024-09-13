package com.ezpay.service;

import com.ezpay.entity.Profile;
import com.ezpay.repository.ProfileRepo;
import com.ezpay.service.ProfileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class ProfileServiceTest {

    @Mock
    private ProfileRepo profileRepo;

    @InjectMocks
    private ProfileService profileService;

    private Profile profile;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        profile = new Profile(1L, "John", "john@example.com", "1234567890", "123 Street");
    }

    @Test
    public void testSaveProfile() {
        // Author: Snehal
        // Description: Tests saving a profile.
        when(profileRepo.save(Mockito.any(Profile.class))).thenReturn(profile);

        Profile savedProfile = profileService.saveProfile(profile);

        assertNotNull(savedProfile);
        assertEquals("John", savedProfile.getName());
    }

    @Test
    public void testGetProfileById() {
        // Author: Snehal
        // Description: Tests retrieving profile by ID.
        when(profileRepo.findById(1L)).thenReturn(Optional.of(profile));

        Profile foundProfile = profileService.getProfileById(1L);

        assertNotNull(foundProfile);
        assertEquals("John", foundProfile.getName());
    }

    @Test
    public void testDeleteProfile() {
        // Author: Snehal
        // Description: Tests deleting profile by ID.
        Mockito.doNothing().when(profileRepo).deleteById(1L);

        String result = profileService.deleteProfile(1L);

        assertEquals("Profile removed !! 1", result);
    }

    @Test
    public void testUpdateProfile() {
        // Author: Snehal
        // Description: Tests updating a profile.
        Profile updatedProfile = new Profile(1L, "Jane", "jane@example.com", "0987654321", "456 Street");

        when(profileRepo.findById(1L)).thenReturn(Optional.of(profile));
        when(profileRepo.save(Mockito.any(Profile.class))).thenReturn(updatedProfile);

        Profile result = profileService.updateProfile(updatedProfile);

        assertNotNull(result);
        assertEquals("Jane", result.getName());
    }
}
