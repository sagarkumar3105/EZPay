package com.ezpay.service;

import com.ezpay.repo.MasterDataRepository;

import java.util.*;


public class UpdateMasterDataService {
	
	static Scanner sc=new Scanner(System.in);

	
	private MasterDataRepository mdRepositoryObj;

	public UpdateMasterDataService() {
		this.mdRepositoryObj = new MasterDataRepository();
	}
	
	//--------------Update new name for user
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
	}
	
	
	

}
