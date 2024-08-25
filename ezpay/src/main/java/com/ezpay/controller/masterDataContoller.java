package com.ezpay.controller;

import com.ezpay.service.UpdateMasterDataService;

public class masterDataContoller {
	private UpdateMasterDataService mdUpdateObj;
	
	public masterDataContoller()
	{
		this.mdUpdateObj=new UpdateMasterDataService();
	}
	
	public void updateName(int customerId, String Name)
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
	}
	

}
