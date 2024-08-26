package com.ezpay.model;

public class Customer {
    private int customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private String address;
    private String dob;
    private String gender;
    private String profileCreationDate;
    private String profileLastUpdatedDate;
    private String profilePictureUrl;
    private boolean isProfileInfoSet;
    private String upiId;       // VARCHAR2(10) - not null, unique
    private String accNo;       // VARCHAR2(16) - not null, unique
    private String ifscCode;    // VARCHAR2(11) - not null
    private int accountType = 1;    // NUMBER(1) - not null

    // Constructors, getters, and setters
    public Customer()
    {
    	
    }
    public Customer(int customerId, String name, String email, String mobileNumber, String address, String dob, String gender, String profileCreationDate, String profileLastUpdatedDate, String profilePictureUrl, boolean isProfileInfoSet,String upiId, String accNo, String ifscCode, int accountType) {
        this.customerId = customerId;
        this.name = name;
        this.email = email;
        this.mobileNumber = mobileNumber;
        this.address = address;
        this.dob = dob;
        this.gender = gender;
        this.profileCreationDate = profileCreationDate;
        this.profileLastUpdatedDate = profileLastUpdatedDate;
        this.profilePictureUrl = profilePictureUrl;
        this.isProfileInfoSet = isProfileInfoSet;
        this.upiId = upiId;
        this.accNo = accNo;
        this.ifscCode = ifscCode;
        this.accountType = accountType;
    }

    // Getters and setters for all fields
    public int getCustomerId() { return customerId; }
    public void setCustomerId(int customerId) { this.customerId = customerId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMobileNumber() { return mobileNumber; }
    public void setMobileNumber(String mobileNumber) { this.mobileNumber = mobileNumber; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDob() { return dob; }
    public void setDob(String dob) { this.dob = dob; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getProfileCreationDate() { return profileCreationDate; }
    public void setProfileCreationDate(String profileCreationDate) { this.profileCreationDate = profileCreationDate; }
    public String getProfileLastUpdatedDate() { return profileLastUpdatedDate; }
    public void setProfileLastUpdatedDate(String profileLastUpdatedDate) { this.profileLastUpdatedDate = profileLastUpdatedDate; }
    public String getProfilePictureUrl() { return profilePictureUrl; }
    public void setProfilePictureUrl(String profilePictureUrl) { this.profilePictureUrl = profilePictureUrl; }
    public boolean getIsProfileInfoSet() { return isProfileInfoSet; }
    public void setIsProfileInfoSet(boolean profileInfoSet) { isProfileInfoSet = profileInfoSet; }
    public String getUpiId() {
        return upiId;
    }

    public void setUpiId(String upiId) {
        this.upiId = upiId;
    }

    // Getter and Setter for accNo
    public String getAccNo() {
        return accNo;
    }

    public void setAccNo(String accNo) {
        this.accNo = accNo;
    }

    // Getter and Setter for ifscCode
    public String getIfscCode() {
        return ifscCode;
    }

    public void setIfscCode(String ifscCode) {
        this.ifscCode = ifscCode;
    }

    // Getter and Setter for accountType
    public int getAccountType() {
        return accountType;
    }

    public void setAccountType(int accountType) {
        this.accountType = accountType;
    }
}
