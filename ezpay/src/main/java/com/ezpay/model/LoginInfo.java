package com.ezpay.model;

public class LoginInfo {
	private String userId;  // user_id
    private byte[] passwordHash; 
    private int blockedCode=0;
	
    
    public LoginInfo()
    {
    	this.userId="user";
    	this.passwordHash = new byte[] {0};
    	this.blockedCode=0;
    }
    
    /**
	 * @param userId
	 * @param passwordHash
	 * @param blockedCode
	 */
	public LoginInfo(String userId, byte[] passwordHash, int blockedCode) {
		super();
		this.userId = userId;
		this.passwordHash = passwordHash;
		this.blockedCode = blockedCode;
	}
	
	// Getter and Setters
    public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public byte[] getPasswordHash() {
		return passwordHash;
	}
	public void setPasswordHash(byte[] passwordHash) {
		this.passwordHash = passwordHash;
	}
	public int getBlockedCode() {
		return blockedCode;
	}
	public void setBlockedCode(int blockedCode) {
		this.blockedCode = blockedCode;
	}
    
    

}
