package com.ezpay.controller;

import com.ezpay.service.UpdateMasterDataService;

public class masterDataContoller {
	private UpdateMasterDataService mdUpdateObj;
	
	public masterDataContoller()
	{
		this.mdUpdateObj=new UpdateMasterDataService();
	}
	
	public void updateData(String tableName, String columnName, int customerId, String newValue)
	{
		mdUpdateObj.updateData(tableName,columnName,customerId, newValue);
	}
	
	public void updateData(String tableName, String columnName, String verificationColumn, int customerId, String newValue, String oldValue)
	{
		mdUpdateObj.updateData(tableName, columnName, verificationColumn, customerId, newValue, oldValue);
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
