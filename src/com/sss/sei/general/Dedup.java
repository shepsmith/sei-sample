package com.sss.sei.general;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;


public class Dedup {

	public static int[] randomIntegers = new int[] { 1, 2, 34, 34, 25, 1, 45, 3, 26, 85, 4,
    		34, 86, 25, 43, 2, 1, 10000, 11, 16, 19, 1, 18, 4, 9, 3, 20, 17, 8,
    		15, 6, 2, 5, 10, 14, 12, 13, 7, 8, 9, 1, 2, 15, 12, 18, 10, 14, 20, 17,
    		16, 3, 6, 19, 13, 5, 11, 4, 7, 19, 16, 5, 9, 12, 3, 20, 7, 15, 17, 10, 6,
    		1, 8, 18, 4, 14, 13, 2, 11 };


	public static void main(String[] args) {
		Dedup d = new Dedup(); // get instance of Dedup
		
		int j=1;
		Integer[] ints = d.getUniqueInts();
		System.out.println("unique ints preserve order ");
		for (Integer i : ints) {
			System.out.println(j + ". " + i);
			j++;
		}
		// possible use case might be to get random html pages by id from database...
		try {
			String example = d.getPage(ints.length / 2); // get random from middle of array
			System.out.println("Example page - " + example);
		} catch (Exception e) {
			System.out.println("Exception ");
		}
		
		
		j=1;
		String[] strInts = d.getUniqueIntsAsStrings();
		System.out.println("unique ints as Strings ");
		for (String strInt : strInts) {
			System.out.println(j + ". " + strInt);
			j++;
		}
		// possible use case...format guesses and random answers
		HashMap<String,String> map = new HashMap<>();
		String[] answers = d.getUniqueIntsAsStrings();
		for(int i=0;i<answers.length;i++) {
			map.put("guess1", answers[i]);
		}


		j=1;
		Integer[] ints2 = d.getUniqueIntsInOrder();
		System.out.println("unique ints preserve order ");
		for (Integer i : ints2) {
			System.out.println(j + ". " + i);
			j++;
		}
		// possible use case might be doing inventory report to see which item/sku's are out of stock
		
	}

	/**
	 * This implementation would be useful for doing something truly random like maybe selecting numbered
	 * images or pages from a database table for display.
	 * 
	 * @return Integer[]
	 */
	public Integer[] getUniqueInts() {
		ArrayList<Integer> a = new ArrayList<>();
		for (int i : randomIntegers)
			if (!(a.contains(Integer.valueOf(i))))
				a.add(Integer.valueOf(i));
		Integer[] ints = (Integer[])a.toArray(new Integer[a.size()]);
		return ints;
	}

	/**
	 * This method returns strings and will be less efficient that integers since String
	 * is immutable and each iteration creates a new String for the list.  This might be useful
	 * if you had like a block of text needing to have random values substituted. Or, maybe
	 * creating a Map of guesses like: map.put"guess1" answers.get(i)
	 * 
	 *  
	 * 
	 * @return String[] 
	 */
	public String[] getUniqueIntsAsStrings() {
		ArrayList<String> strList = new ArrayList<>();
		for (int i = 0; i < randomIntegers.length; ++i) {
			String strInt = String.valueOf(randomIntegers[i]);
			if (!(strList.contains(strInt)))
				strList.add(strInt);
		}
		return ((String[])strList.toArray(new String[strList.size()]));
	}

	public Integer[] getUniqueIntsInOrder(){
		TreeSet<Integer> ts = new TreeSet<>();
		for (int i : randomIntegers)
			ts.add(Integer.valueOf(i));
		return ((Integer[])ts.toArray(new Integer[ts.size()]));
	}

	
	
	//---------------------- utility methods for use cases
	
	/**
	 * Sample method to get page from database
	 * 
	 * @param pageNum
	 * @return html page text
	 * @throws Exception 
	 */
	private String getPage(Integer pageNum) { //throws Exception {
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement stmt = null;
		try {
			conn = getOracleConnection();
			stmt = conn.prepareStatement("select name from pages where id = ?");
			stmt.setInt(1, pageNum);  // just get requested page from db
			rs = stmt.executeQuery();
			if(rs.first()) {
				return rs.getString("name");
			} else {
				return "invalid page";
			}
		} catch (Exception e) {
			//throw e;
		}
		finally {
			if(conn!=null) { 
				try {
					conn.close();
				} catch (Exception e2) {
					
				}
			}
		}
		return "db error occurred";
	}
	
	
	// Timeout (in seconds) for validation:
	private static final int VALIDATION_TIMEOUT = 5;
	
	/**
	 * Get a connection to Oracle datasource.
	 * 
	 * @return a connection
	 */
	private static Connection getOracleConnection() throws Exception {
		Connection conn = null;
		boolean valid = false;
		try {
			valid = conn != null ? conn.isValid(VALIDATION_TIMEOUT) : false;
		}
		catch (Exception e) {
			valid = false;
		}
		if (conn == null || !valid) {
			//conn = DriverManager.getConnection("jdbc:oracle:oci8:@", "uid", "pwd");
		}
		return conn;
	}
	
	
}	

