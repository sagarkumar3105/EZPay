package com.ezpay.repo;

import com.ezpay.model.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.ezpay.model.*;

// Class to interact with all the tables in Usecase 01

public class MasterDataRepository {
	
	// Global Session object
    private Session session;
	
	public MasterDataRepository()
	{
        session = HibernateUtil.getSessionFactory().openSession();

	}
	
	/**
	 * @param customerId -> Primary Key
	 * @return object of Customer type
	 */
	public Customer getCustomerById(int customerId) //by customer ID
	{
		return session.get(Customer.class, customerId);
	}
	
	
	/**
	 * @param tableName -> Table name to fetch data from
	 * @param columnName -> Column to look for the value
	 * @param customerId -> Primary key
	 * @param newValue 	-> 	New Value to add in the table
	 */
	public void updateData(String tableName, String columnName, int customerId, String newValue) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            

            String hql = String.format("UPDATE %s e SET e.%s = :newValue WHERE e.customerId = :customerId",tableName, columnName);

// Begin transaction
            Query query = session.createQuery(hql);
            query.setParameter("newValue", newValue);
            query.setParameter("customerId", customerId);
            int result = query.executeUpdate();

            
            transaction.commit();
            if (result > 0) {
                System.out.printf("Value updated successfully for Customer ID: %d", customerId);
            } else {
                System.out.println("No user found with the provided ID.");
            }
        } catch (Exception e) {
            //if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
	
	//Overloaded method to update after verification
		/**
		 * @param tableName -> Table name to fetch data from
		 * @param columnName -> Column to look for the value
		 * @param verificationColumn -> A column to look for a value which will be used to cross verify the data
		 * @param customerId ->	primary key
		 * @param newValue -> Value to replace
		 * @param oldValue -> Value to cross verify
		 */
	public void updateData(String entityName, String columnName, String verificationColumn, int customerId, String newValue, String oldValue) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            String hql = String.format("Select e.%s from %s e where e.customerId = :customerId",verificationColumn,entityName);//"SELECT " + verificationColumn + " FROM "+ tableName +" WHERE customerId = :customerId";
            Query query = session.createQuery(hql);
            query.setParameter("customerId", customerId);
           
            String verificationValue = (String) query.uniqueResult();

            if (!oldValue.trim().equals(verificationValue.trim())) {
                System.out.println("Sorry!!! The your entered details don't match the registered details.\nValue update failed.");
                return;
            }

            hql = String.format("UPDATE %s e SET e.%s = :newValue WHERE customerId = :customerId", entityName, columnName );//"UPDATE "+ entityName +" SET " + columnName + " = :newValue WHERE customerId = :customerId";
            query = session.createQuery(hql);
            query.setParameter("newValue", newValue);
            query.setParameter("customerId", customerId);
            int result = query.executeUpdate();
            transaction.commit();

            if (result > 0) {
                System.out.printf("Value updated successfully for Customer ID: %d", customerId);
            } else {
                System.out.println("No user found with the provided ID.");
            }
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }
	 public void close() {
	        session.close();
	    }
	
	
	
/*
	/**
	 * @param rs -> result set which contains data from master_data Table
	 * @return Customer type Object
	 * @throws SQLException
	 */
	/*private Customer mapResultSetToMasterData(ResultSet rs) throws SQLException {
        Customer customerObj = new Customer();
        customerObj.setCustomerId(rs.getInt("CUSTOMER_ID"));
        customerObj.setName(rs.getString("NAME"));
        customerObj.setEmail(rs.getString("EMAIL"));
        customerObj.setMobileNumber(rs.getString("MOBILE_NUMBER"));
        customerObj.setAddress(rs.getString("ADDRESS"));
        customerObj.setDob(rs.getString("DOB"));
        customerObj.setGender(rs.getString("GENDER"));
        

        customerObj.setProfileCreationDate(DATE_TIME_FORMATTER.format(rs.getTimestamp("PROFILE_CREATION_DATE")));
        customerObj.setProfileLastUpdatedDate(DATE_TIME_FORMATTER.format(rs.getTimestamp("PROFILE_LAST_UPDATED_DATE")));
        
        customerObj.setProfilePictureUrl(rs.getString("PROFILE_PICTURE_URL"));
        customerObj.setIsProfileInfoSet((rs.getInt("IS_PROFILE_INFO_SET")==1)?true:false);
        customerObj.setUpiId(rs.getString("UPI_ID"));
        customerObj.setAccNo(rs.getString("ACC_NO"));
        customerObj.setIfscCode(rs.getString("IFSC_CODE"));
        customerObj.setAccountType(rs.getInt("ACCOUNT_TYPE"));
        return customerObj;
    }
	
	
	
	public void updateName(int customerId, String newName )
	{
		Connection con = DatabaseConnection.getConnection();
        String query = "UPDATE master_data SET name = ? WHERE customer_id = ?";
        try {
        	PreparedStatement statement = con.prepareStatement(query);
        	statement.setString(1, newName);
        	statement.setInt(2, customerId);
        	
        	int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Customer Name updated successfully for Customer ID: %d",customerId);
            } else {
                System.out.println("No user found with the provided ID.");
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateEmail(int customerId, String newEmail, String oldMobileNumber)
	{
	
		Connection con = DatabaseConnection.getConnection();
        String query = "UPDATE master_data SET email = ? WHERE customer_id = ?";
        String query2 = "SELECT mobile_number from master_data where customer_id=?";
        try {
        	PreparedStatement statement2 = con.prepareStatement(query2);
        	statement2.setInt(1, customerId);
        	ResultSet rs = statement2.executeQuery();
        	   	
        	if(rs.next())
        	{
        		String mobNum = rs.getString("mobile_number");
	        	if(!(mobNum.trim()).equals((oldMobileNumber).trim()))
	        	{
        		System.out.println("Sorry!!! Mobile number didn't match.\nEmail update failed.");
                return;
	        	}
        	}
        	PreparedStatement statement = con.prepareStatement(query);
        	statement.setString(1, newEmail);
        	statement.setInt(2, customerId);
        	
        	int rowsAffected = statement.executeUpdate();
        	        	
            if (rowsAffected > 0) {
                System.out.printf("Email updated successfully for Customer ID: %d",customerId);
            } else {
                System.out.println("Email Update Failed!!!.");
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateMobileNumber(int customerId, String newMobile, String oldEmail )
	{
		Connection con = DatabaseConnection.getConnection();
        String query = "UPDATE master_data SET mobile_number = ? WHERE customer_id = ?";
        String query2 = "SELECT email from master_data where customer_id=?";

        try {
        	PreparedStatement statement2 = con.prepareStatement(query2);
        	statement2.setInt(1, customerId);
        	ResultSet rs = statement2.executeQuery();
        	
        	if(rs.next())
        	{	
        		String email = rs.getString("email");
	        	if(!(email.trim()).equals((oldEmail).trim()))
	        	{
                System.out.println("Sorry!!! Email didn't match.\nMobile Number update failed.");
                return;
	        	}
        	}
        	PreparedStatement statement = con.prepareStatement(query);
        	statement.setString(1, newMobile);
        	statement.setInt(2, customerId);
        	
        	int rowsAffected = statement.executeUpdate();
       	
        	
            if (rowsAffected >0) {
                System.out.printf("Mobile Number updated successfully for Customer ID: %d",customerId);
            } else {
                System.out.println("Mobile Number update failed.");
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	public void updateProfilePicture(int customerId, String newProfilePictureUrl )
	{
		Connection con = DatabaseConnection.getConnection();
        String query = "UPDATE master_data SET profile_picture_url = ? WHERE customer_id = ?";
        try {
        	PreparedStatement statement = con.prepareStatement(query);
        	statement.setString(1, newProfilePictureUrl);
        	statement.setInt(2, customerId);
        	
        	int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.printf("Profile Picture updated successfully for Customer ID: %d",customerId);
            } else {
                System.out.println("Profile Picture update Failed");
            }
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	*/
	

}
