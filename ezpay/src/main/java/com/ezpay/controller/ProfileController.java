package com.ezpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ezpay.entity.Profile;
import com.ezpay.service.ProfileService;

@RestController
public class ProfileController {
	
	@Autowired
	private ProfileService service;
	@PostMapping("/addProfile")
	public Profile addProfile(@RequestBody Profile profile)
	{
		return service.saveProfile(profile);
	}
	@PostMapping("/addProfiles")
    public List<Profile> addProfiles(@RequestBody List<Profile> profiles) {
        return service.saveProfiles(profiles);
    }
  
    @GetMapping("/Profiles")
    public List<Profile> findAllProfiles() {
        return service.getProfiles();
    }
	@GetMapping("/ProfileById/{id}")
    public Profile findProfileById(@PathVariable Long id) {
        return service.getProfileById(id);
    }
	@GetMapping("/Profile/{name}")
	public Profile getProfileByName(@PathVariable String name)
	{
		return service.getProfileByName(name);
	}
	@PutMapping("/update")
    public Profile updateProfile(@RequestBody Profile profile) {
        return service.updateProfile(profile);
    }
  
    @DeleteMapping("/delete/{id}")
    public String deleteProfile(@PathVariable Long id) {
        return service.deleteProfile(id);
    }

}
