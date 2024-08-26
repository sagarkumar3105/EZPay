package com.ezpay.service;

import com.ezpay.repo.MasterDataRepository;

import java.util.*;


public class UpdateMasterDataService {
	
	static Scanner sc=new Scanner(System.in);

	
	private MasterDataRepository mdRepositoryObj;

	public UpdateMasterDataService() {
		this.mdRepositoryObj = new MasterDataRepository();
	}
	
	public void updateData(String tableName, String columnName, int customerId, String newValue)
	{
		mdRepositoryObj.updateData(tableName,columnName,customerId, newValue);
	}
	
	public void updateData(String tableName, String columnName, String verificationColumn, int customerId, String newValue, String oldValue)
	{
		mdRepositoryObj.updateData(tableName, columnName,verificationColumn,customerId,newValue, oldValue );
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
