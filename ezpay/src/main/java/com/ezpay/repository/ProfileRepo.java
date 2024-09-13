package com.ezpay.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ezpay.entity.Profile;

public interface ProfileRepo extends JpaRepository<Profile,Long> {
	    Profile findByName(String name);
}
