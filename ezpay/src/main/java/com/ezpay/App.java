package com.ezpay;

import com.ezpay.controller.*;
import java.util.*;

public class App 
{
    public static void main( String[] args )
    {
    	MasterDataController mdControllerObj = new MasterDataController();
    	Scanner sc=new Scanner(System.in);
    	int customerId;
    	int choice=0;
    	System.out.print("Enter the customer ID to update Details: ");
    	customerId=Integer.parseInt(sc.nextLine());
    	char wantToContinue='N';
    	do {
    	System.out.println("Enter a choice to continue:\n1. Update Name\n2. Update Email\n3. Update Mobile Number\n4. Update Profile Picture");
    	choice=Integer.parseInt(sc.nextLine());
    	
    	switch (choice) {
		case 1:
   	        System.out.print( "Enter new Name: " );
   	        String newName;
	        newName=sc.nextLine();
	        mdControllerObj.updateData("Customer","name",customerId,newName);
			break;
		case 2:
   	        System.out.print( "Enter new Email: " );
   	        String newEmail=sc.nextLine();
   	        System.out.print("Enter your registered Mobile number: ");
   	        String oldMobileNumber=sc.nextLine();
   	        mdControllerObj.updateData("Customer","email","mobileNumber",customerId,newEmail,oldMobileNumber);
   	        
			break;
		case 3:
			System.out.print( "Enter new Mobile Number: " );
   	        String newMobileNumber=sc.nextLine();
   	        System.out.print("Enter your registered Email address: ");
   	        String oldEmail=sc.nextLine();
   	        mdControllerObj.updateData("Customer","mobileNumber","email",customerId,newMobileNumber,oldEmail);
			
			break;
		
		case 4:
			 System.out.print( "Enter new Profile Picture Url: " );
	   	     String newProfilePictureUrl;
		     newProfilePictureUrl=sc.nextLine();
		        mdControllerObj.updateData("Customer","profile_picture_url",customerId,newProfilePictureUrl);
			
			break;
		default:
			System.out.println("Choose a correct option");
			break;
		}
    	
    	System.out.print("\nWant to update more informations: ");
    	wantToContinue=sc.nextLine().toUpperCase().charAt(0);
    	}while(wantToContinue == 'Y');
    	System.out.print("!!!Changes Saved");
        sc.close();
    }
}
