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
import java.sql.Date;
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


public class DatabaseInsertServices {
	
	public int insertConvertedResults(int fileId, int originalId){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Converted Results\""
				+ " (fileid, originalid)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Converted Results\".convertedid;";
		
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, fileId);
	        pstmt.setInt(2, originalId);
	        ResultSet results = pstmt.executeQuery();
	        while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
				
        return newid;
	}
	
	public int insertMembers(String firstname, String lastname){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Members\""
				+ " (fname, lname)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Members\".memberid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, firstname);
			pstmt.setString(2, lastname);
			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertModel(String modelname, String description, String creationdate, int modelType, String sourcelanguage){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Model\""
				+ " (modelname, description, creationdate, modeltype, sourcelanguage)"
				+ " VALUES(?, ?, ?, ?, ?)"
				+ " RETURNING \"Model\".modelid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);

			pstmt.setString(1, modelname);
			pstmt.setString(2, description);
			pstmt.setString(3, creationdate);
			pstmt.setInt(4, modelType);
			pstmt.setString(5, sourcelanguage);

			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertModelChild(int parentId, int childId, int amount){
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Model Children\""
				+ " (parentid, childid, amount)"
				+ " VALUES(?, ?, ?);";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, parentId);
			pstmt.setInt(2, childId);
			pstmt.setInt(3, amount);

			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return 0;
	}
	
	public int insertModelFiles(String name, String type, String location, String created, String description, int author, int modelid){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Model Files\""
				+ " (\"name\", \"type\", \"location\", created, description, author, modelid)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)"
				+ " RETURNING \"Model Files\".fileid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setString(3, location);
			pstmt.setString(4, created);
			pstmt.setString(5, description);
			pstmt.setInt(6, author);
			pstmt.setInt(7, modelid);
			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertOriginalResults(int fileId, int sourceId){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Original Results\""
				+ " (fileid, sourceid)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Original Results\".originalid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, fileId);
	        pstmt.setInt(2, sourceId);
	        ResultSet results = pstmt.executeQuery();
	        while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertProject(String projectName, String projectDescription, Date creationDate){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Project\""
				+ " (projectname, projectdescription, creationdate)"
				+ " VALUES(?, ?, ?)"
				+ " RETURNING \"Project\".projectid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, projectName);
			pstmt.setString(2, projectDescription);
			pstmt.setDate(3, creationDate);
			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertProjectDocument(String name, String type, String location, String created, String description, int author, int projectId){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Project Documents\""
				+ " (\"name\", \"type\", \"location\", created, description, author, projectid)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)"
				+ " RETURNING \"Project Documents\".documentID;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setString(1, name);
			pstmt.setString(2, type);
			pstmt.setString(3, location);
			pstmt.setString(4, created);
			pstmt.setString(5, description);
			pstmt.setInt(6, author);
			pstmt.setInt(7, projectId);
			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
	
	public int insertProjectMembers(int memberId, int projectId){
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Project_Members\""
				+ " (memberid, projectid)"
				+ " VALUES(?, ?);";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, memberId);
	        pstmt.setInt(2, projectId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return 0;
	}
	
	public int insertProjectModel(int projectId, int modelId){
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Project_Model\""
				+ " (project_id, model_id)"
				+ " VALUES(?, ?);";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, projectId);
	        pstmt.setInt(2, modelId);
			pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return 0;
	}
	
	public int insertSourceFile(int fileId){
		int newid = 0;
		Connection connection = ConnectionFactory.getConnection(); 
		String sql = "INSERT INTO \"FourthYearProject\".\"Source Files\" "
				+ " (fileid) "
				+ " VALUES(?) "
				+ " RETURNING \"Source Files\".sourceid;";
		try {
			PreparedStatement pstmt = connection.prepareStatement(sql);
			pstmt.setInt(1, fileId);
			ResultSet results = pstmt.executeQuery();
			while (results.next()){	
				newid = results.getInt(1);
			}
		} catch (SQLException e) {
			System.out.println("SQLException in getProject()");
			e.printStackTrace();
		} finally {
			DbUtil.close(connection);
		}
        return newid;
	}
}
