package com.ezpay.entity;

//import jakarta.persistence.*;

import java.time.LocalDateTime;

import javax.persistence.*;

/**
 * Entity class representing a customer in the EzPay application. This class
 * contains various attributes related to customer information, such as name,
 * contact details, account information, and profile metadata.
 */
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long customerId;

	private String name;
	private String email;
	private String mobileNumber;
	private String address;
	private LocalDateTime dob; // Date of Birth
	private String gender;
	private LocalDateTime profileCreationDate;
	private LocalDateTime profileLastUpdatedDate;
	private String profilePictureUrl;
	private Boolean isProfileInfoSet;
	private String upiId; // UPI ID for transactions
	private String accNo; // Bank account number
	private String ifscCode; // IFSC code for bank transactions

	// Getters and Setters
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public LocalDateTime getDob() {
		return dob;
	}

	public void setDob(LocalDateTime dob) {
		this.dob = dob;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDateTime getProfileCreationDate() {
		return profileCreationDate;
	}

	public void setProfileCreationDate(LocalDateTime profileCreationDate) {
		this.profileCreationDate = profileCreationDate;
	}

	public LocalDateTime getProfileLastUpdatedDate() {
		return profileLastUpdatedDate;
	}

	public void setProfileLastUpdatedDate(LocalDateTime profileLastUpdatedDate) {
		this.profileLastUpdatedDate = profileLastUpdatedDate;
	}

	public String getProfilePictureUrl() {
		return profilePictureUrl;
	}

	public void setProfilePictureUrl(String profilePictureUrl) {
		this.profilePictureUrl = profilePictureUrl;
	}

	public Boolean getIsProfileInfoSet() {
		return isProfileInfoSet;
	}

	public void setIsProfileInfoSet(Boolean isProfileInfoSet) {
		this.isProfileInfoSet = isProfileInfoSet;
	}

	public String getUpiId() {
		return upiId;
	}

	public void setUpiId(String upiId) {
		this.upiId = upiId;
	}

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getIfscCode() {
		return ifscCode;
	}

	public void setIfscCode(String ifscCode) {
		this.ifscCode = ifscCode;
	}

}