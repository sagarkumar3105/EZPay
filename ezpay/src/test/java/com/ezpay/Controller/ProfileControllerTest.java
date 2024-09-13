package com.ezpay.Controller;

import com.ezpay.controller.ProfileController;


import com.ezpay.entity.Profile;
import com.ezpay.service.ProfileService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class ProfileControllerTest {

    private MockMvc mockMvc;

    @Mock
    private ProfileService profileService;

    @InjectMocks
    private ProfileController profileController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(profileController).build();
    }

    @Test
    void testAddProfile() throws Exception {
        // Author: Snehal
        // Description: Tests adding a new profile using POST request.
        Profile profile = new Profile(1L, "John", "john@example.com", "1234567890", "123 Street");

        when(profileService.saveProfile(Mockito.any(Profile.class))).thenReturn(profile);

        mockMvc.perform(post("/addProfile")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"John\", \"email\":\"john@example.com\", \"mobile\":\"1234567890\", \"address\":\"123 Street\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testGetProfileById() throws Exception {
        // Author: Snehal
        // Description: Tests retrieving a profile by ID.
        Profile profile = new Profile(1L, "John", "john@example.com", "1234567890", "123 Street");

        when(profileService.getProfileById(1L)).thenReturn(profile);

        mockMvc.perform(get("/ProfileById/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testDeleteProfile() throws Exception {
        // Author: Snehal
        // Description: Tests deleting a profile by ID.
        when(profileService.deleteProfile(1L)).thenReturn("Profile removed !! 1");

        mockMvc.perform(delete("/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Profile removed !! 1"));
    }

    @Test
    void testUpdateProfile() throws Exception {
        // Author: Snehal
        // Description: Tests updating an existing profile.
        Profile updatedProfile = new Profile(1L, "Jane", "jane@example.com", "0987654321", "456 Street");

        when(profileService.updateProfile(Mockito.any(Profile.class))).thenReturn(updatedProfile);

        mockMvc.perform(put("/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1, \"name\":\"Jane\", \"email\":\"jane@example.com\", \"mobile\":\"0987654321\", \"address\":\"456 Street\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Jane"));
    }
}
