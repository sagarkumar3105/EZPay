package com.ezpay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ezpay.entity.Profile;
import com.ezpay.repository.ProfileRepo;

@Service
public class ProfileService {
	@Autowired
	private ProfileRepo repo;
	public Profile saveProfile(Profile profile)
	{
		return repo.save(profile);
	}
	public List<Profile> saveProfiles(List<Profile> profiles) {
        return repo.saveAll(profiles);
    }
	public List<Profile> getProfiles() {
        return repo.findAll();
    }
	public Profile getProfileById(Long id)
	{
		return repo.findById(id).orElse(null);
	}
	public Profile getProfileByName(String name)
	{
		return repo.findByName(name);
	}
	public String deleteProfile(Long id) {
        repo.deleteById(id);
        return "Profile removed !! " + id;
    }
	public Profile updateProfile(Profile profile) {
        Profile existingProfile = repo.findById(profile.getId()).orElse(null);
        existingProfile.setName(profile.getName());
        existingProfile.setEmail(profile.getEmail());
        existingProfile.setMobile(profile.getMobile());
        existingProfile.setAddress(profile.getAddress());
        return repo.save(existingProfile);
    }

}
