package services;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import util.ConnectionFactory;
import util.DbUtil;

public class DatabaseService {


	public String testConnection() throws IOException {
		Connection connection = ConnectionFactory.getConnection();
		 String returnThis = "";
		 ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "SELECT * from \"FourthYearProject\".\"Members\"";	
				java.sql.Statement stmt = connection.createStatement();
	            
				results = stmt.executeQuery(sql);
	            while (results.next()){	
	            	returnThis += results.getString("Fname") + " " + results.getString("Lname") + "\n";
	            }
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
		return returnThis;
	}
	
	public String getAuthorAccount(String lname, String fname) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "SELECT * FROM \"FourthYearProject\".\"Members\" where  lname = ? and fname= ?";
	
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, lname);
	            pstmt.setString(2, fname);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	returnThis += results.getString("memberid") + " " +  results.getString("fname") + " " + results.getString("lname") + "\n";
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
	        return returnThis;
	}
	
	public String getAuthorProjects(String lname, String fname) throws IOException{
		Connection connection = ConnectionFactory.getConnection();
		String returnThis = "";
		ResultSet results = null;
	        try {
	            connection = ConnectionFactory.getConnection();
	            
	            String sql = "select * from \"FourthYearProject\".\"Project\" p inner join "
	            		+ "	(select pm.projectid from \"FourthYearProject\".\"Members\" m inner join "
	            		+ "		\"FourthYearProject\".\"Project_Members\" pm "
	            		+ "		on pm.memberid = m.memberid "
	            		+ "		where m.lname = ? and m.fname = ? "
	            		+ "	) mp "
	            		+ "	on p.projectid = mp.projectid";
	
				PreparedStatement pstmt = connection.prepareStatement(sql);
				pstmt.setString(1, lname);
	            pstmt.setString(2, fname);
	            
	            results = pstmt.executeQuery();
	            while (results.next()){	
	            	returnThis += "Project ID: " + results.getString("projectid")
	            				+ " Project Name: " +  results.getString("projectname")
	            				+ " Project Description: " + results.getString("projectdescription") 
	            				+ " Creation Date: " + results.getString("creationdate") + " \n";
	            }    
	        } catch (SQLException e) {
	            System.out.println("SQLException in get() method");
	            e.printStackTrace();
	        } finally {
	            DbUtil.close(connection);
	        }
	        return returnThis;
	}
	
}