package com.ezpay.model;

import javax.persistence.*;

@Entity
@Table(name = "login_data")
public class LoginInfo {
	
	@Id
	@Column(name = "user_id", nullable = false, length = 30)
	private String userId;  // user_id
    
    @Column(name = "password_hash", nullable = false)
	private byte[] passwordHash; 
    
    @Column(name = "customer_id", nullable = false, unique = true)
    private int blockedCode=0;
	

    @OneToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_id", insertable = false, updatable = false)
    private Customer customer;
    

    // Default Constructor
    public LoginInfo()
    {
    	this.userId="user";
    	this.passwordHash = new byte[] {0};
    	this.blockedCode=0;
    	this.customer = new Customer();
    }
    
    
    /**
	 * @param userId
	 * @param passwordHash
	 * @param blockedCode
	 */
	public LoginInfo(String userId, byte[] passwordHash, int blockedCode,Customer cust) {
		super();
		this.userId = userId;
		this.passwordHash = passwordHash;
		this.blockedCode = blockedCode;
		this.customer = cust;
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
