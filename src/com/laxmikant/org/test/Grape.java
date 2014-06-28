package com.laxmikant.org.test;

/**
 * @author Laxmikant
 *
 */

import static com.laxmikant.org.test.Db.*;

import java.util.InputMismatchException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Grape {
	
	static Connection con = null;	//connection instance
	static Scanner input;	//scanner for user input
	
	public static void main(String[] args) {
		int i;	//i for user input 
		
		try {
			//loading the DRIVER
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, USER, PASS);	//calling connection				
			input = new Scanner(System.in);
			System.out.println();
			System.out.println("<=================> WELCOME TO FARMER EMPLOYEE DATABASE <=================>");
			System.out.println();
			System.out.println("Choose the option below");
			System.out.println("1.Insert the DATA\n2.Show DATA\n3.Delete DATA\n4.Search Name\n5.Quite");
			i = input.nextInt();
			
			switch (i) {
			case 1:
				insertData(con);	//calling insert static method
				break;
			case 2:
				showData(con);	//calling showData method
				break;
			case 3:
				deleteData(con);	//calling deleteSata method
				break;
			case 4:
				searchData(con);	//calling searchData method
				break;
			case 5:System.out.println("Quite...!!!");
				break;
			default:
				break;
			}
			//Check User choice
			if(i != 5)
				main(args);			
			
		} catch (ClassNotFoundException classNotFoundException) {
			System.err.println("Class not found" + classNotFoundException);
		}catch (SQLException sqlexception) {
			System.err.println("There is problem in SQL" + sqlexception);
		}catch (InputMismatchException inputMismatchException) {
			System.err.println("Exception for Mismatch Entry Please Tray again " + inputMismatchException);
		}
		
		finally{
			try {
				if(con != null)
					con.close();
			} catch (SQLException sqlexception) {
				System.err.println("SQL Exception" + sqlexception);
			}
		}//end of finally
	}//end of main method
	
	//method for inserting data
	public static void insertData(Connection con){
		String Query = "INSERT INTO laxmikant.test(name,age,mobile) VALUES(?,?,?)";
		try {
			PreparedStatement pt = con.prepareStatement(Query);	//preparestatement for executing query 
			//choose the option for user entry
			System.out.println("Please Enter the Name: ");
			String name = input.next();
			System.out.println("Please Enter the age: ");
			String age = input.next();
			System.out.println("Please Enter the mobile number: ");
			String mobile = input.next();
				
			pt.setString(1, name);
			pt.setString(2, age);
			pt.setString(3, mobile);
			pt.executeUpdate();
			System.out.println("Congratulation One row inserted.....!!!");
			System.out.println();			
		} catch (SQLException sqlexception) {
			System.err.println("There is problem in SQL" + sqlexception);
		}
	}// end of insertData
	
	//method for showing data 
	public static void showData(Connection con){
		String Query = "SELECT * FROM laxmikant.test";
		Statement st;
		try {
			
			st = con.createStatement();
			ResultSet rt = st.executeQuery(Query);
			boolean recordFound = false;
			System.out.println("Name            Age      Mobile");
			System.out.println("================================");
			while (rt.next()) {
				String name = rt.getString("name");
				String age = rt.getString("age");
				String mobile = rt.getString("mobile");
				System.out.printf("%-10s%8s%15s",name,age,mobile);
				System.out.println();
				recordFound = true;
			}
			if(!recordFound){
				System.out.println("Oh No Database is Empty...!!!!");
				System.out.println();
			}
		} catch (SQLException sqlexception) {
			System.err.println("There is problem in SQL" + sqlexception);
		}		
	}//end of showData method
	
	//method for deleting the data
	public static void deleteData(Connection con){
		System.out.println("Enter the mobile number to delete the data");
		String mobile = input.next();		
		String cquery = "SELECT * FROM laxmikant.test WHERE mobile='"+mobile+"'";
		String Query = "DELETE FROM laxmikant.test WHERE mobile=?";
		PreparedStatement pt;	//creating instance object
		Statement st;	//creating instance object
		try {
			st = con.createStatement();
			ResultSet rs = st.executeQuery(cquery);
			if(rs.next()){
				pt = con.prepareStatement(Query);
				pt.setString(1, mobile);
				pt.executeUpdate();
				System.out.println(mobile+" Number is Successfuly Deleted..!!!!");
				System.out.println();
			}else{
				System.out.printf("Sorry  %s is Not Found!!! Please Try Again\n ",mobile);
			}	
			
		} catch (SQLException sqlexception) {
			System.err.println("There is problem in SQL" + sqlexception);
		}
	}// end of deleteData method
	
	//method for searching the data using name based
	public static void searchData(Connection con){
		System.out.println("Please Enter the name to search..");
		String name = input.next();	//get the user input
		String Namequery = "SELECT * FROM laxmikant.test WHERE name = '"+name+"'";	//creating query for searching the name
		Statement st;	//creating instance object
		try {
			st = con.createStatement();
			ResultSet rs ;// pt.executeQuery();
			rs = st.executeQuery(Namequery);
			System.out.println("Name        Age      Mobile");
			System.out.println("============================");
			boolean recordf = false;
			while(rs.next()){
				String name1 = rs.getString("Name");
				String age = rs.getString("Age");
				String mobile = rs.getString("Mobile");
				System.out.printf("%-10s%4s%17s",name1,age,mobile);
				System.out.println();	//next line
				System.out.println();
				recordf = true ;
			}
			//if record not found
			if(!recordf){
				System.out.printf("Sorry, Name \"%s\" is Not found...Please Try Again...!!! \n",name.toUpperCase());
				System.out.println();
			}
			
		} catch (SQLException sqlexception) {
			System.err.println("There is problem in SQL" + sqlexception);
		}
		
	}//end of the searchData
	
}//end of the class Grape