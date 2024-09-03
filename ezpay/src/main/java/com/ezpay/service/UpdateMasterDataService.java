package com.ezpay.service;

import com.ezpay.repo.MasterDataRepository;

//import com.ezpay.model.Customer;


public class UpdateMasterDataService {
	
	
	private MasterDataRepository mdRepositoryObj;

	 public UpdateMasterDataService() {
	        this.mdRepositoryObj = new MasterDataRepository();
	}

	 public void updateData(String entityName, String columnName, int customerId, String newValue) {
	     mdRepositoryObj.updateData(entityName,columnName, customerId, newValue);
	}
	    //	public void updateData() {

	public void updateData(String entityName, String columnName, String verificationColumn, int customerId, String newValue, String oldValue) {
	    mdRepositoryObj.updateData(entityName, columnName, verificationColumn, customerId, newValue, oldValue);
	}
	
	/*//--------------Update new name for user
	public void updateName(int customerId, String newName)
	{
		
		mdRepositoryObj.updateName(customerId, newName);
	}
	public void updateEmail(int customerId, String newEmail, String OldMobileNumber) {
		mdRepositoryObj.updateEmail(customerId,newEmail, OldMobileNumber);
	}
	
	
	public void updateMobileNumber(int customerId, String newMobileNumber, String oldEmail) {
		mdRepositoryObj.updateMobileNumber(customerId,newMobileNumber, oldEmail);
	}
	
	public void updateProfilePictureUrl(int customerId, String newProfilePictureUrl)
	{
		mdRepositoryObj.updateName(customerId, newProfilePictureUrl);
	}*/
	
	
	

}
