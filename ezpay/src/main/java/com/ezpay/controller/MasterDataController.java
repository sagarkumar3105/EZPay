package com.ezpay.controller;

import com.ezpay.service.UpdateMasterDataService;

public class MasterDataController {
	private UpdateMasterDataService mdUpdateObj;
	
	public MasterDataController()
	{
		this.mdUpdateObj=new UpdateMasterDataService();
	}
	
	public void updateData(String entityName, String columnName, int customerId, String newValue)
	{
		mdUpdateObj.updateData(entityName,columnName,customerId, newValue);
	}
	
	public void updateData(String entityName, String columnName, String verificationColumn, int customerId, String newValue, String oldValue)
	{
		mdUpdateObj.updateData(entityName, columnName, verificationColumn, customerId, newValue, oldValue);
	}
	
	/*public void updateName(int customerId, String Name)
	{
		mdUpdateObj.updateName(customerId, Name);
	}
	public void updateEmail(int customerId, String newEmail, String OldMobileNumber) {
		mdUpdateObj.updateEmail(customerId,newEmail, OldMobileNumber);
	}
	
	
	public void updateMobileNumber(int customerId, String newMobileNumber, String oldEmail) {
		mdUpdateObj.updateMobileNumber(customerId,newMobileNumber, oldEmail);
	}
	
	public void updateProfilePictureUrl(int customerId, String newProfilePictureUrl)
	{
		mdUpdateObj.updateName(customerId, newProfilePictureUrl);
	}*/
	

}
