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


public class DatabaseInsertServices {
	
	
	public String insertConvertedResults(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Converted Results\""
				+ " (fileid, originalid)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Converted Results\".convertedid;";
		return "";
	}
	
	public String insertMembers(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Members\""
				+ " (fname, lname)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Members\".memberid;";
		return "";
	}
	
	public String insertModel(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Model\""
				+ " (modelname, description, creationdate, modeltype, sourcelanguage)"
				+ " VALUES(?, ?, ?, ?, ?)"
				+ " RETURNING \"Model\".modelid;";
		return "";
	}
	
	public String insertModelChild(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Model Children\""
				+ " (parentid, childid, amount)"
				+ " VALUES(?, ?, ?);";
		return "";
	}
	
	public String insertModelFiles(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Model Files\""
				+ " (\"name\", \"type\", \"location\", created, description, author, modelid)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)"
				+ " RETURNING \"Model Files\".fileid;";
		return "";
	}
	
	public String insertOriginalResults(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Original Results\""
				+ " (fileid, sourceid)"
				+ " VALUES(?, ?)"
				+ " RETURNING \"Original Results\".originalid;";
		return "";
	}
	
	public String insertProject(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Project\""
				+ " (projectname, projectdescription, creationdate)"
				+ " VALUES(?, ?, ?)"
				+ " RETURNING \"Project\".projectid;";
		return "";
	}
	
	public String insertProjectDocument(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Project Documents\""
				+ " (\"name\", \"type\", \"location\", created, description, author, projectid)"
				+ " VALUES(?, ?, ?, ?, ?, ?, ?)"
				+ " RETURNING \"Project Documents\".documentID;";
		return "";
	}
	
	public String insertProjectMembers(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Project_Members\""
				+ " (memberid, projectid)"
				+ " VALUES(?, ?);";
		return "";
	}
	
	public String insertProjectModel(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Project_Model\""
				+ " (project_id, model_id)"
				+ " VALUES(?, ?);";
		return "";
	}
	
	public String insertSourceFile(){
		String sql = "INSERT INTO \"FourthYearProject\".\"Source Files\" "
				+ "(fileid) "
				+ "VALUES(?) "
				+ "RETURNING \"Source Files\".sourceid;";
		return "";
	}
	
	

}
